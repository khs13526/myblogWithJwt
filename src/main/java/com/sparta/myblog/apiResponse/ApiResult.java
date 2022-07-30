package com.sparta.myblog.apiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
    private final boolean success;
    private final T data;
    private final ApiError error;

    public ApiResult(boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
