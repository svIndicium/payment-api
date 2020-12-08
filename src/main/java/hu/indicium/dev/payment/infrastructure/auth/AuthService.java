package hu.indicium.dev.payment.infrastructure.auth;

import hu.indicium.dev.payment.domain.model.member.MemberId;

public interface AuthService {
    Auth0User getCurrentUser();
}