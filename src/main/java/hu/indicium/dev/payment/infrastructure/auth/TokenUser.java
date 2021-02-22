package hu.indicium.dev.payment.infrastructure.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class TokenUser implements User {

    private final String name;

    public TokenUser(Authentication authentication) {
        this.name = ((Jwt) authentication.getPrincipal()).getClaim("name");
    }

    @Override
    public String getName() {
        return name;
    }
}
