package com.ljd.rmi;

public class Response {
    private int resultCode;
    private String resultMsg;
    private Object data;
    public static final Response SUCCESS = new Response();

    public Response() {
        this((Object)null);
    }

    public Response(Object data) {
        this.resultCode = 0;
        this.resultMsg = "success";
        this.data = data;
    }

    public Response(int resultCode, String resutMessage) {
        this.resultCode = resultCode;
        this.resultMsg = resutMessage;
    }

    public Response(int resultCode, String resutMsg, Object data) {
        this.resultCode = resultCode;
        this.resultMsg = resutMsg;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String toString() {
        return "Response [resultCode=" + this.resultCode + ", resultMsg=" + this.resultMsg + ", data=" + this.data + "]";
    }
}

