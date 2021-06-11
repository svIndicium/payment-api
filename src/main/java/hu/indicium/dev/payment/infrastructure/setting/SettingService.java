package hu.indicium.dev.payment.infrastructure.setting;

import java.util.List;

public interface SettingService {
    String getValueByKey(String key);

    Setting getSettingByKey(String key);

    Setting updateSetting(String key, String value);

    List<Setting> getAllSettings();
}
