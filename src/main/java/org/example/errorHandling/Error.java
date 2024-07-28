package org.example.errorHandling;

public class Error {
    private String errorCode;
    private String errorMessage;

    @Override
    public String toString() {
        return "Error{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public Error(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public Error() {

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
