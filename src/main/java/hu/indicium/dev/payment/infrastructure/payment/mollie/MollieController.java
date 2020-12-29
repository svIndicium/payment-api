package hu.indicium.dev.payment.infrastructure.payment.mollie;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static hu.indicium.dev.payment.infrastructure.util.BaseUrl.API_V1;

@RestController
@RequestMapping(API_V1)
@AllArgsConstructor
public class MollieController {

    private final MollieService mollieService;

    @PostMapping("/mollie/webhook")
    @ResponseStatus(HttpStatus.OK)
    public void processWebhook(@RequestBody String body) {
        String[] content = body.split("=");
        mollieService.updateTransaction(content[1]);
    }
}
