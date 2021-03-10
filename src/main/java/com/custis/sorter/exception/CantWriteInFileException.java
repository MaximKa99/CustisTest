package com.custis.sorter.exception;

public class CantWriteInFileException extends RuntimeException{
    public CantWriteInFileException(Throwable cause) {
        super(cause);
    }

    public CantWriteInFileException() {
    }

    public CantWriteInFileException(String message) {
        super(message);
    }
}
