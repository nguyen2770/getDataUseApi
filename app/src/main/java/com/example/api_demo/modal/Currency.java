package com.example.api_demo.modal;

public class Currency {
    private boolean success;
    private ERror error;

    public Currency(boolean success, ERror error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ERror getError() {
        return error;
    }

    public void setError(ERror error) {
        this.error = error;
    }
}
