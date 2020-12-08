package hu.indicium.dev.payment.infrastructure.payment.mollie;

import be.woutschoovaerts.mollie.Client;
import be.woutschoovaerts.mollie.ClientBuilder;
import hu.indicium.dev.payment.infrastructure.setting.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MollieClient {

    private final SettingService settingService;

    @Bean
    public Client client() {
        return new ClientBuilder()
                .withApiKey(settingService.getValueByKey("MOLLIE_API_KEY"))
                .build();
    }
}
