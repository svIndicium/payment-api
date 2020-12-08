package hu.indicium.dev.payment.infrastructure.setting.requests;

//import javax.validation.constraints.NotBlank;

public class UpdateSettingRequest {
//    @NotBlank(message = "Waarde mag niet leeg zijn")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
