package com.svaboda.telegram.support;

import org.springframework.util.StringUtils;

public interface ArgsValidation {

    static String notEmpty(String arg) {
        if (StringUtils.isEmpty(arg) || arg.isBlank()) throw new IllegalArgumentException("Param is empty");
        return arg;
    }

    static <T> T notNull(T arg) {
        if (arg == null) throw new IllegalArgumentException("Param is null");
        return arg;
    }

}
