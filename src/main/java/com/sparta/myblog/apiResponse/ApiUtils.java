package com.sparta.myblog.apiResponse;

public class ApiUtils {
    public static <T>ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static ApiResult<?> error(String message, int status){
        return new ApiResult<>(false, false, new ApiError(message, status));
    }

}