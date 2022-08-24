package com.microservice.redis.dao;

public class LogRequest {
    private String id;
    private String uri;
    private String method;
    private String requestDate;
    private String requestData;
    private String responseDate;
    private String responseData;
    private long processTime;
    private String requestId;

    public LogRequest(){
        this.id = "";
        this.uri = "";
        this.method = "";
        this.requestDate = "";
        this.requestData = "";
        this.responseDate = "";
        this.responseData = "";
        this.processTime = 0;
        this.requestId = "";
    }

    public LogRequest(String id, String uri, String method, String requestDate, String requestData, String responseDate, String responseData, long processTime, String requestId) {
        this.id = id;
        this.uri = uri;
        this.method = method;
        this.requestDate = requestDate;
        this.requestData = requestData;
        this.responseDate = responseDate;
        this.responseData = responseData;
        this.processTime = processTime;
        this.requestId = requestId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
