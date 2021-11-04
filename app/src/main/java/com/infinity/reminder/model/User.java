package com.infinity.reminder.model;

public class User {
    private String id;
    private String password;
    private String permission;
    private String name;
    private String phone;
    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public User(String id, String password, String permission, String name, String phone, String age) {
        this.id = id;
        this.password = password;
        this.permission = permission;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }
}
