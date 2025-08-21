package com.coder.learn.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserActivityLogService {

    public String convertToString(LocalDateTime timestamp) {
        return timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}