package hu.indicium.dev.payment.infrastructure.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Auth0 settings")
@TestPropertySource(properties = {
        "auth0.apiAudience=https://test.auth0.com",
        "auth0.issuer=https://lit.test.com",
        "auth0.clientId=abcabc",
        "auth0.clientSecret=abcdef"
})
class AuthSettingsTest {

    @Autowired
    private AuthSettings authSettings;

    @Test
    @DisplayName("Get audience")
    void getApiAudience() {
        assertThat(authSettings.getApiAudience()).isEqualTo("https://test.auth0.com");
    }

    @Test
    @DisplayName("Get issuer")
    void getIssuer() {
        assertThat(authSettings.getIssuer()).isEqualTo("https://lit.test.com");
    }

    @Test
    @DisplayName("Get client id")
    void getClientId() {
        assertThat(authSettings.getClientId()).isEqualTo("abcabc");
    }

    @Test
    @DisplayName("Get client secret")
    void getClientSecret() {
        assertThat(authSettings.getClientSecret()).isEqualTo("abcdef");
    }
}