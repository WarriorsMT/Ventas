package com.ciclo3.ventas.entities;

public class RespuestaEntity {
    private String message;
    private Object result;
    private boolean ok;

    public RespuestaEntity(String message, Object result, Boolean ok) {
        this.message = message;
        this.result = result;
        this.ok = ok;
    }

    public RespuestaEntity() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
