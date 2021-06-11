package hu.indicium.dev.payment.infrastructure.setting;

import hu.indicium.dev.payment.infrastructure.setting.dto.SettingDto;
import hu.indicium.dev.payment.infrastructure.setting.requests.UpdateSettingRequest;
import hu.indicium.dev.payment.infrastructure.util.Response;
import hu.indicium.dev.payment.infrastructure.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static hu.indicium.dev.payment.infrastructure.util.BaseUrl.API_V1;

@RestController
@AllArgsConstructor
@RequestMapping(API_V1 + "/settings/payments")
public class SettingController {
    private final SettingService settingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<SettingDto>> getSettings() {
        Collection<Setting> settings = settingService.getAllSettings();
        Collection<SettingDto> settingDtos = settings.stream()
                .map(SettingDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(settingDtos)
                .build();
    }

    @GetMapping(value = "/{settingKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<SettingDto> getSettingByKey(@PathVariable String settingKey) {
        Setting setting = settingService.getSettingByKey(settingKey);
        SettingDto settingDto = new SettingDto(setting);
        return ResponseBuilder.ok()
                .data(settingDto)
                .build();
    }

    @PutMapping(value = "/{settingKey}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<SettingDto> updateSetting(@PathVariable String settingKey, @RequestBody UpdateSettingRequest updateSettingRequest) {
        Setting setting = settingService.updateSetting(settingKey, updateSettingRequest.getValue());
        SettingDto settingDto = new SettingDto(setting);
        return ResponseBuilder.accepted()
                .data(settingDto)
                .build();
    }
}
