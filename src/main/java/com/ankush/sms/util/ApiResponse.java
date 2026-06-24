package com.ankush.sms.util;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse {
    private String message;
    private String error;
    private int status;
    private String path;
    private LocalDateTime timestamp;
}
