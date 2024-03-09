package com.blog.blogback.common.Exception;

public class NotFoundException extends RuntimeException  {
    private final ErrorType errorType;

    public ErrorType getErrorType(){
        return errorType;
    }

    public NotFoundException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public NotFoundException(String message) {
        super(message);
        this.errorType = null;
    }

    public NotFoundException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    // 오류 코드를 받아들이는 생성자 추가
    public NotFoundException(int errorCode) {
        switch (errorCode) {
            case ErrorType.INDEX_NOT_FOUND:
            case ErrorType.BOARD_NOT_FOUND:
            case ErrorType.NOT_MATCHING_PASSWORD:
                this.errorType = new ErrorType(errorCode);
                break;
            default:
                throw new IllegalArgumentException("Invalid error code: " + errorCode);
        }
    }

}