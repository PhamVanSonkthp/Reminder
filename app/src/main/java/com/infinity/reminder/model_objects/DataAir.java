package com.infinity.reminder.model_objects;

import java.util.List;

public class DataAir {

    private DataAirValue data;

    public DataAirValue getData() {
        return data;
    }

    public void setData(DataAirValue data) {
        this.data = data;
    }

    public DataAir(DataAirValue data) {
        this.data = data;
    }

    public class DataAirValue{
        int pageIndex;
        int pageSize;
        int totalItems;
        int totalPages;
        List<DataListAir> data;

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

        public List<DataListAir> getData() {
            return data;
        }

        public void setData(List<DataListAir> data) {
            this.data = data;
        }

        public DataAirValue(int pageIndex, int pageSize, int totalItems, int totalPages, List<DataListAir> data) {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
            this.totalItems = totalItems;
            this.totalPages = totalPages;
            this.data = data;
        }
    }

    public class DataListAir{
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
}
