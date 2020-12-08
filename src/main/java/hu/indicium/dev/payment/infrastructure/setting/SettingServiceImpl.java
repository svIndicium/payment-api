package hu.indicium.dev.payment.infrastructure.setting;

import hu.indicium.dev.payment.infrastructure.setting.dto.SettingDTO;
import hu.indicium.dev.payment.infrastructure.setting.exceptions.SettingNotFoundException;
import hu.indicium.dev.payment.infrastructure.setting.exceptions.SettingNotSetException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

//    private final AuthService authService;

    public SettingServiceImpl(SettingRepository settingRepository
//                             , @Lazy AuthService authService
    ) {
        this.settingRepository = settingRepository;
//        this.authService = authService;
    }

    @Override
    public String getValueByKey(String key) {
        return getSettingByKey(key).getValue();
    }

    @Override
    public SettingDTO getSettingByKey(String key) {
        Setting setting = getSetting(key);
        if (setting.getValue().isBlank()) {
            throw new SettingNotSetException(key);
        }
        return SettingMapper.map(setting);
    }

    @Override
//    @PostAuthorize("hasPermission('write:' + returnObject.permission)")
    public SettingDTO updateSetting(String key, String value) {
//        Auth0User auth0User = authService.getCurrentUser();
        Setting setting = getSetting(key);
        setting.setValue(value);
//        setting.setUpdatedBy(auth0User.getName());
        setting = settingRepository.save(setting);
        return SettingMapper.map(setting);
    }

    @Override
//    @PreAuthorize("hasPermission('read:settings')")
//    @PostFilter("hasPermission('read:' + filterObject.permission)")
    public List<SettingDTO> getAllSettings() {
        return settingRepository.findAll()
                .stream()
                .map(SettingMapper::map)
                .collect(Collectors.toList());
    }

    private Setting getSetting(String key) {
        return settingRepository.findByKey(key)
                .orElseThrow(() -> new SettingNotFoundException(key));
    }
}
