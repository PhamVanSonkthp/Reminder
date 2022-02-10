package com.infinity.reminder.model_objects;

import java.util.List;

public class Wifi {

    private WifiValue data;

    public WifiValue getData() {
        return data;
    }

    public void setData(WifiValue data) {
        this.data = data;
    }

    public Wifi(WifiValue data) {
        this.data = data;
    }

    public class WifiValue{
        int pageIndex;
        int pageSize;
        int totalItems;
        int totalPages;
        List<DataListWifi> data;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<DataListWifi> getData() {
            return data;
        }

        public void setData(List<DataListWifi> data) {
            this.data = data;
        }

        public WifiValue(int pageIndex, int pageSize, int totalItems, int totalPages, List<DataListWifi> data) {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
            this.totalItems = totalItems;
            this.totalPages = totalPages;
            this.data = data;
        }
    }

    public class DataListWifi{
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
}
