package hu.indicium.dev.payment.infrastructure.web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenIDConnectJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

    private static final Collection<String> WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES = Arrays.asList("scope", "scp");

    private String principalClaimName;

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        if (this.principalClaimName == null) {
            return new JwtAuthenticationToken(jwt, authorities);
        }
        String name = jwt.getClaim(this.principalClaimName);
        return new JwtAuthenticationToken(jwt, authorities, name);
    }

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.getScopes(jwt)
                .stream()
                .map(authority -> SCOPE_AUTHORITY_PREFIX + authority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<String> getScopes(Jwt jwt) {
        Collection<String> authorities = new ArrayList<>();
        for (String attributeName : WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES) {
            Object scopes = jwt.getClaims().get(attributeName);
            if (scopes instanceof String) {
                if (StringUtils.hasText((String) scopes)) {
                    authorities.addAll(Arrays.asList(((String) scopes).split(" ")));
                }
            } else if (scopes instanceof Collection) {
                authorities.addAll((Collection<String>) scopes);
            }
        }
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        for (String applicationName : resourceAccess.keySet()) {
            Map<String, Object> application = (Map<String, Object>) resourceAccess.get(applicationName);
            Object roles = application.get("roles");
            if (roles instanceof Collection) {
                for (String role : ((Collection<String>) roles)) {
                    authorities.add(applicationName + "/" + role);
                }
            }
        }
        return authorities;
    }

    public void setPrincipalClaimName(String principalClaimName) {
        Assert.hasText(principalClaimName, "principalClaimName cannot be empty");
        this.principalClaimName = principalClaimName;
    }
}