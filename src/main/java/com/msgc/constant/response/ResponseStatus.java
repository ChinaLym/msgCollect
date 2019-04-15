package com.msgc.constant.response;

public enum ResponseStatus {

    /**
     *  请求成功状态码
     */
    OK("2000","success"),

    /**
     * 请求失败
     */
    FAIL_4000("4000","FILE"),

    /**
     *  未认证
     */
    FAIL_4001("4001","未认证"),

    /**
     * 无权限
     */
    FAIL_4003("4003","无权限");


    private final String value;

    private final String reason;

    ResponseStatus(String value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
