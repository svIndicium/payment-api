package hu.indicium.dev.payment.infrastructure.auth;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakProvider {
    @Value("${spring.security.oauth2.client.registration.indicium.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.indicium.client-secret}")
    private String clientSecret;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private Keycloak keycloak;

    public Keycloak getKeycloak() {
        if (this.keycloak == null || this.keycloak.isClosed()) {
            createKeycloak();
        }
        return this.keycloak;
    }

    private void createKeycloak() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
