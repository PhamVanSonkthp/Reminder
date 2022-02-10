package com.infinity.reminder.model_objects;

public class DataListWifi {
    int id;
    String name;
    String pass;
    int status;
    int id_device;
    String create_at;
    String update_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_device() {
        return id_device;
    }

    public void setId_device(int id_device) {
        this.id_device = id_device;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public DataListWifi(int id, String name, String pass, int status, int id_device, String create_at, String update_at) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.status = status;
        this.id_device = id_device;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}
