package com.xteam.ggq.web.controller.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private static ApiResponse DEFAULT_SUCCESS_RESPONSE = new ApiResponse();
    private static ApiResponse DEFAULT_FAIL_RESPONSE = new ApiResponse(-1, null, "服务器异常！");

    /** 返回码 */
    private int status;

    /** 返回消息 */
    private String message;

    /** 返回具体的数据 */
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * 返回成功
     */
    public static ApiResponse returnSuccess() {
        return DEFAULT_SUCCESS_RESPONSE;
    }

    public static <T> ApiResponse<T> returnSuccess(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> returnSuccess(String message) {
        return new ApiResponse<>(0, null, message);
    }

    public static <T> ApiResponse<T> returnSuccess(T data, String message) {
        return new ApiResponse<>(0, data, message);
    }

    /**
     * 返回失败
     */
    public static ApiResponse returnFail() {
        return DEFAULT_FAIL_RESPONSE;
    }

    public static <T> ApiResponse<T> returnFail(int status, String message) {
        assert status != 0;
        return new ApiResponse<>(status, null, message);
    }

    public static <T> ApiResponse<T> returnFail(int status, T data, String message) {
        assert status != 0;
        return new ApiResponse<>(status, data, message);
    }

}
