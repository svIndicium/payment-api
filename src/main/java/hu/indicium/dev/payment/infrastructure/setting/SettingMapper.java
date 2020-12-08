package hu.indicium.dev.payment.infrastructure.setting;

import hu.indicium.dev.payment.infrastructure.setting.dto.SettingDTO;

public class SettingMapper {
    public static SettingDTO map(Setting setting) {
        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setKey(setting.getKey());
        settingDTO.setValue(setting.getValue());
        settingDTO.setDescription(setting.getDescription());
        settingDTO.setTitle(setting.getTitle());
        settingDTO.setPermission(setting.getPermission());
        settingDTO.setUpdatedAt(setting.getUpdatedAt());
        settingDTO.setUpdatedBy(setting.getUpdatedBy());
        return settingDTO;
    }
}
