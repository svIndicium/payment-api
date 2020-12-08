package hu.indicium.dev.payment.infrastructure.setting;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Setting {
    @Id
    @Column(name = "key", nullable = false, updatable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "permission", updatable = false)
    private String permission;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public Setting() {
        // public no-args constructor for hibernate
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
