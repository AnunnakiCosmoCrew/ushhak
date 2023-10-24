package com.annunakicosmocrew.ushhak.restservices.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private T data;
    private String error;

    /**
     * Constructor for success responses
     */
    public ApiResponse(T data) {
        this.data = data;
    }

    /**
     * Constructor for error responses
     */
    public ApiResponse(String error) {
        this.error = error;
    }

}

