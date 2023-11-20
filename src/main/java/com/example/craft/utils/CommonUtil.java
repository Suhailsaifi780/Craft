package com.example.craft.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonUtil {

    public static boolean checkNotNullNotEmptyAndNotSameString(String oldValue, String newValue) {
        log.info("Inside checkNotNullNotEmptyAndNotSame for newValue : {} and oldValue : {}", newValue, oldValue);

        return (oldValue == null && newValue != null) || (newValue != null && !newValue.isEmpty() && !oldValue.equalsIgnoreCase(newValue));
    }

    public static boolean checkNotNullNotEmptyAndNotSameInteger(Integer oldValue, Integer newValue) {
        log.info("Inside checkNotNullNotEmptyAndNotSame for newValue : {} and oldValue : {}", newValue, oldValue);

        return (oldValue == null && newValue != null) || (newValue != null && !oldValue.equals(newValue));
    }
}
