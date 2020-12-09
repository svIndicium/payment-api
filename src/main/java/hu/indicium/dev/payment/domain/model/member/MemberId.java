package hu.indicium.dev.payment.domain.model.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private String authId;

    public MemberId(String authId) {
        this.authId = authId;
    }

    public static MemberId fromAuthId(String authId) {
        return new MemberId(authId);
    }
}