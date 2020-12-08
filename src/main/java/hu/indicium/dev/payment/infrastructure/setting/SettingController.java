package hu.indicium.dev.payment.infrastructure.setting;

import hu.indicium.dev.payment.infrastructure.setting.dto.SettingDTO;
import hu.indicium.dev.payment.infrastructure.setting.requests.UpdateSettingRequest;
import hu.indicium.dev.payment.infrastructure.util.Response;
import hu.indicium.dev.payment.infrastructure.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.indicium.dev.payment.infrastructure.util.BaseUrl.API_V1;

@RestController
@RequestMapping(API_V1 + "/settings/payments")
public class SettingController {
    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<SettingDTO>> getSettings() {
        return ResponseBuilder.ok()
                .data(settingService.getAllSettings())
                .build();
    }

    @PutMapping(value = "/{settingKey}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<SettingDTO> updateSetting(@PathVariable String settingKey, @RequestBody UpdateSettingRequest updateSettingRequest) {
        return ResponseBuilder.accepted()
                .data(settingService.updateSetting(settingKey, updateSettingRequest.getValue()))
                .build();
    }
}
