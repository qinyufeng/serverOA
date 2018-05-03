package com.jgonet.jdbc;

public class DataException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataException() {
        super();
    }

    public DataException(String msg) {
        super(msg);
    }

    public DataException(String msg, Throwable e) {
        super(msg, e);
    }

    public DataException(Throwable e) {
        super(e);
    }
}
