package com.msgc.constant.response;

/**
 * 返回的JSON数据结构标准
 *
 * @param <T>
 */
public class ResponseWrapper<T> {

    private String status = ResponseStatus.OK.getValue();

    private String message = "success";

    private String detail;

    private T data;

    public ResponseWrapper(){}

    public static ResponseWrapper fail(String detail){
        ResponseWrapper resp = new ResponseWrapper();
        resp.message = "fail";
        resp.status = ResponseStatus.FAIL_4000.getValue();
        resp.detail = detail;
        return resp;
    }

    public static ResponseWrapper success(String detail){
        ResponseWrapper resp = new ResponseWrapper();
        resp.message = "success";
        resp.status = ResponseStatus.FAIL_4000.getValue();
        resp.detail = detail;
        return resp;
    }

    public ResponseWrapper(ResponseStatus status, String message) {
        this.status = status.getValue();
        this.message = message;
    }

    public ResponseWrapper(ResponseStatus status, T data) {
        this.status = status.getValue();
        this.data = data;
    }

    public ResponseWrapper(ResponseStatus status, String message, T data) {
        this.status = status.getValue();
        this.message = message;
        this.data = data;
    }

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status.getValue();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
