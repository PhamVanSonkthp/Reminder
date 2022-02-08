package com.infinity.reminder.model_objects;

public class UserData{

    private String fullname;
    private String username;
    private String email;
    private int age;
    private String note;
    private int role;
    private String health_insurance;
    private String phone;
    private String address;
    private String building_id;
    private String device_code;
    private String channel;
    private String ir_limit;
    private String bpm_limit;
    private int id;
    private String job;
    private String email_verified_at;
    private String created_at;
    private String updated_at;

    public UserData() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHealth_insurance() {
        return health_insurance;
    }

    public void setHealth_insurance(String health_insurance) {
        this.health_insurance = health_insurance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIr_limit() {
        return ir_limit;
    }

    public void setIr_limit(String ir_limit) {
        this.ir_limit = ir_limit;
    }

    public String getBpm_limit() {
        return bpm_limit;
    }

    public void setBpm_limit(String bpm_limit) {
        this.bpm_limit = bpm_limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UserData(String fullname, String username, String email, int age, String note, int role, String health_insurance, String phone, String address, String building_id, String device_code, String channel, String ir_limit, String bpm_limit, int id, String job, String email_verified_at, String created_at, String updated_at) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.age = age;
        this.note = note;
        this.role = role;
        this.health_insurance = health_insurance;
        this.phone = phone;
        this.address = address;
        this.building_id = building_id;
        this.device_code = device_code;
        this.channel = channel;
        this.ir_limit = ir_limit;
        this.bpm_limit = bpm_limit;
        this.id = id;
        this.job = job;
        this.email_verified_at = email_verified_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}