package com.ys.citronix.sharedkernel.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<ResponseDto> {

    private final ResponseDto data;
    private final String message;
    private final HttpStatus status;

    public ApiResponse(ResponseDto data, String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

}