package com.james.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;

public class JSONUtils {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static String toString(JSONFormatObject input) {
        // TODO 单例模式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        try {
            return objectMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            logger.error("writeValueAsString error", e);
            return "";
        }
    }

}
