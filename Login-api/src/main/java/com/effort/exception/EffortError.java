package com.effort.exception;

import org.springframework.http.HttpStatus;

public class EffortError extends RuntimeException { 

    private static final long serialVersionUID = 7766511856485216833L;

    private int code;
    private String desc;
    private HttpStatus httpStatus;

    public EffortError(int code, HttpStatus httpStatus) {
        this(code, "An error occurred with code: " + code, httpStatus); 
    }

    public EffortError(int code, String desc, HttpStatus httpStatus) {
        super(desc != null ? desc : "An error occurred with code: " + code); 
        this.code = code;
        this.desc = desc;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return "EffortError [code=" + code + ", desc=" + desc + ", httpStatus=" + httpStatus + "]";
    }

    @Override
    public String getMessage() {
        return "EffortError [code=" + code + ", desc=" + desc + ", httpStatus=" + httpStatus + "]";
    }
}