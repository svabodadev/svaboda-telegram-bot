package com.svaboda.monitor.monitoring;

import com.svaboda.utils.Endpoints;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.svaboda.utils.ArgsValidation.notEmpty;
import static com.svaboda.utils.ArgsValidation.positive;

@ConfigurationProperties(prefix = "env")
@ConstructorBinding
@Value
class MonitorProperties {
    private final static String URL_SEPARATOR = ",";
    int intervalSec;
    List<String> servicesUrls;

    MonitorProperties(int intervalSec, String servicesBaseUrls) {
        this.intervalSec = positive(intervalSec);
        this.servicesUrls = Arrays.stream(notEmpty(servicesBaseUrls).split(URL_SEPARATOR))
                .map(baseUrl -> "https://" + baseUrl + Endpoints.READY)
                .collect(Collectors.toList());
    }

}
