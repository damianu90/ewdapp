package com.duzieblo.ewdapp.exception;

/**
 *
 * @author Damian Uziębło
 */
public class XmlRpcFault extends Exception{
    private final String errorCode;
    private final String errorMessage;

    public XmlRpcFault(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    
}
