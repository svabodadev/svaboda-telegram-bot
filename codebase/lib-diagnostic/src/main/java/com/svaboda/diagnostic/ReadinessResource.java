package com.svaboda.diagnostic;

import com.svaboda.utils.Endpoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReadinessResource {

    private static final String ALIVE_RESPONSE = "OK";

    @GetMapping(Endpoints.READY)
    String ready() {
        return ALIVE_RESPONSE;
    }
}
