package hu.indicium.dev.payment.infrastructure.setting;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
public class Setting {
    @Id
    @Column(name = "key", nullable = false, updatable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "read_permission", updatable = false)
    private String readPermission;

    @Column(name = "write_permission", updatable = false)
    private String writePermission;

    @Column(name = "write_only", updatable = false)
    private boolean writeOnly;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public void updateSetting(String value, String updatedBy) {
        this.setValue(value);
        this.setUpdatedBy(updatedBy);
    }

    private void setValue(String value) {
        this.assertArgumentNotEmpty(value, "Setting can not be empty.");
        this.value = value;
    }

    private void setUpdatedBy(String updatedBy) {
        this.assertArgumentNotEmpty(updatedBy, "User updating the setting must be provided.");
        this.updatedBy = updatedBy;
    }

    protected void assertArgumentNotEmpty(String aString, String aMessage) {
        if (aString == null || aString.trim().isEmpty()) {
            throw new IllegalArgumentException(aMessage);
        }
    }
}
