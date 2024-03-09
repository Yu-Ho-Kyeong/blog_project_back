package com.blog.blogback.common.Exception;

public class ErrorType {
    public final static int INDEX_NOT_FOUND = 101;
    public final static int BOARD_NOT_FOUND = 201;
    public final static int NOT_MATCHING_PASSWORD = 301;

    private final int errorCode;

    public ErrorType(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
