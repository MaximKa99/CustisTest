package com.custis.sorter.exception;

public class CantReadFileException extends RuntimeException{
    public CantReadFileException() {
    }

    public CantReadFileException(String message) {
        super(message);
    }

    public CantReadFileException(Throwable cause) {
        super(cause);
    }
}
