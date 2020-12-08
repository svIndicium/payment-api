package hu.indicium.dev.payment.infrastructure.auth;

import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import hu.indicium.dev.payment.infrastructure.setting.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class Auth0ServiceImpl implements AuthService {

    private final Auth0ApiProvider auth0ApiProvider;


    private final SettingService settingService;

    @Override
    public Auth0User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authUserId = (String) authentication.getPrincipal();
        Request<User> userRequest = auth0ApiProvider.getManagementAPI().users().get(authUserId, new UserFilter());
        User user = executeRequest(userRequest);
        return new Auth0User(user);
    }

    private <T> T executeRequest(Request<T> request) {
        try {
            return request.execute();
        } catch (Auth0Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
