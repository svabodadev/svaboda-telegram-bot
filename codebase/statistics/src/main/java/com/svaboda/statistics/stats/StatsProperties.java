package com.svaboda.statistics.stats;

import com.svaboda.utils.Endpoints;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.svaboda.utils.ArgsValidation.notEmpty;
import static com.svaboda.utils.ArgsValidation.positive;

@ConfigurationProperties(prefix = "env")
@ConstructorBinding
@Value
class StatsProperties {
    private final static String URL_SEPARATOR = ",";
    int intervalSec;
    int httpTimeoutSec;
    List<String> servicesUrls;


    StatsProperties(int intervalSec, int httpTimeoutSec, String servicesBaseUrls) {
        this.intervalSec = positive(intervalSec);
        this.httpTimeoutSec = positive(httpTimeoutSec);
        this.servicesUrls = Arrays.stream(notEmpty(servicesBaseUrls).split(URL_SEPARATOR))
                .map(baseUrl -> "https://" + baseUrl + Endpoints.STATS)
                .collect(Collectors.toList());
    }

    Duration intervalSec() {
        return Duration.ofSeconds(intervalSec);
    }

    Duration httpTimeoutSec() {
        return Duration.ofSeconds(httpTimeoutSec);
    }

}
