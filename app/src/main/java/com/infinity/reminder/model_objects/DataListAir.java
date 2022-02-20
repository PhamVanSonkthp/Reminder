package com.infinity.reminder.model_objects;

public class DataListAir {
    int id;
    int user_id;
    int co;
    int gas;
    int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataListAir(int id, int user_id, int co, int gas, int status) {
        this.id = id;
        this.user_id = user_id;
        this.co = co;
        this.gas = gas;
        this.status = status;
    }
}
