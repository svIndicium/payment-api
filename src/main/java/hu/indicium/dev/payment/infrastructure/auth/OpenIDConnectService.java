package hu.indicium.dev.payment.infrastructure.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OpenIDConnectService implements AuthService {

    private final KeycloakProvider keycloakProvider;

    @Value("${keycloak.realm}")
    private String realm;

    public OpenIDConnectService(KeycloakProvider keycloakProvider) {
        this.keycloakProvider = keycloakProvider;
    }

    @Override
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return new TokenUser(authentication);
    }
}
