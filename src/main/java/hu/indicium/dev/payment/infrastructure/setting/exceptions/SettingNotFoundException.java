package hu.indicium.dev.payment.infrastructure.setting.exceptions;

import javax.persistence.EntityNotFoundException;

public class SettingNotFoundException extends EntityNotFoundException {
    public SettingNotFoundException(String key) {
        super(String.format("Setting with key %s not found!", key));
    }
}
