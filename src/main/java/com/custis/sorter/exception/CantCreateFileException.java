package com.custis.sorter.exception;

public class CantCreateFileException extends RuntimeException{
    public CantCreateFileException() {
    }

    public CantCreateFileException(String message) {
        super(message);
    }

    public CantCreateFileException(Throwable cause) {
        super(cause);
    }
}
