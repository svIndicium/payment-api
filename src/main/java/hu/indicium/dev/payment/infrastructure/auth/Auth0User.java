package hu.indicium.dev.payment.infrastructure.auth;

import com.auth0.json.mgmt.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Auth0User {

    private String userId;

    private String mailAddress;

    private boolean mailAddressVerified;

    private String username;

    private String phoneNumber;

    private Date createdAt;

    private Date updatedAt;

    private Map<String, Object> appMetadata;

    private Map<String, Object> userMetadata;

    private String picture;

    private String name;

    private String nickname;

    private int loginsCount;

    private String givenName;

    private String familyName;

    public Auth0User(User user) {
        this.userId = user.getId();
        this.mailAddress = user.getEmail();
        this.mailAddressVerified = user.isEmailVerified();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.appMetadata = user.getAppMetadata();
        this.userMetadata = user.getUserMetadata();
        this.picture = user.getPicture();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.loginsCount = user.getLoginsCount();
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
    }
}