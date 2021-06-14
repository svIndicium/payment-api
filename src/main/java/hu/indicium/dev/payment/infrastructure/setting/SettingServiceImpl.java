package hu.indicium.dev.payment.infrastructure.setting;

import hu.indicium.dev.payment.infrastructure.auth.AuthService;
import hu.indicium.dev.payment.infrastructure.auth.User;
import hu.indicium.dev.payment.infrastructure.setting.exceptions.SettingNotFoundException;
import hu.indicium.dev.payment.infrastructure.setting.exceptions.SettingNotSetException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    private final AuthService authService;

    public SettingServiceImpl(SettingRepository settingRepository, @Lazy AuthService authService) {
        this.settingRepository = settingRepository;
        this.authService = authService;
    }

    @Override
    public String getValueByKey(String key) {
        return getSetting(key).getValue();
    }

    @Override
    @PostAuthorize("hasPermission(returnObject.readPermission)")
    public Setting getSettingByKey(String key) {
        Setting setting = getSetting(key);
        if (setting.getValue().isBlank()) {
            throw new SettingNotSetException(key);
        }
        return setting;
    }

    @Override
    @Transactional
    @PostAuthorize("hasPermission(returnObject.writePermission)")
    public Setting updateSetting(String key, String value) {
        User currentUser = authService.getCurrentUser();
        Setting setting = getSetting(key);
        setting.updateSetting(value, currentUser.getName());
        setting = settingRepository.save(setting);
        return setting;
    }

    @Override
    @PostFilter("hasPermission(filterObject.readPermission)")
    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    private Setting getSetting(String key) {
        return settingRepository.findByKey(key)
                .orElseThrow(() -> new SettingNotFoundException(key));
    }
}
