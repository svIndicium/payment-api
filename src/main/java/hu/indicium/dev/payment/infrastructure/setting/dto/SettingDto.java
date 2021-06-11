package hu.indicium.dev.payment.infrastructure.setting.dto;

import hu.indicium.dev.payment.infrastructure.setting.Setting;
import lombok.Data;

import java.util.Date;

@Data
public class SettingDto {
    private String key;

    private String value;

    private String readPermission;

    private String writePermission;

    private Date updatedAt;

    private String updatedBy;

    private boolean writeOnly;

    public SettingDto(Setting setting) {
        this.key = setting.getKey();
        if (!setting.isWriteOnly()) {
            this.value = setting.getValue();
        }
        this.readPermission = setting.getReadPermission();
        this.writePermission = setting.getWritePermission();
        this.updatedAt = setting.getUpdatedAt();
        this.updatedBy = setting.getUpdatedBy();
        this.writeOnly = setting.isWriteOnly();
    }
}
