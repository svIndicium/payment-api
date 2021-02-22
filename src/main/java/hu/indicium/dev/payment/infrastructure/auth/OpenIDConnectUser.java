package hu.indicium.dev.payment.infrastructure.auth;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class OpenIDConnectUser implements OpenIDUser {

    private String name;

    @Override
    public String getName() {
        return name;
    }
}
