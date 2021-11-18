package com.infinity.reminder.model;

import java.util.List;

public class DataMax30100 {

    private DataMax30100Value data;

    public DataMax30100Value getData() {
        return data;
    }

    public void setData(DataMax30100Value data) {
        this.data = data;
    }

    public DataMax30100(DataMax30100Value data) {
        this.data = data;
    }

    public class DataMax30100Value{
        int pageIndex;
        int pageSize;
        int totalItems;
        int totalPages;
        List<DataListMax30100> data;

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

        public List<DataListMax30100> getData() {
            return data;
        }

        public void setData(List<DataListMax30100> data) {
            this.data = data;
        }

        public DataMax30100Value(int pageIndex, int pageSize, int totalItems, int totalPages, List<DataListMax30100> data) {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
            this.totalItems = totalItems;
            this.totalPages = totalPages;
            this.data = data;
        }
    }

    public class DataListMax30100{
        int id;
        int user_id;
        int bmp;
        int spo2;
        int ir;
        String create_at;
        String update_at;

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

        public int getBmp() {
            return bmp;
        }

        public void setBmp(int bmp) {
            this.bmp = bmp;
        }

        public int getSpo2() {
            return spo2;
        }

        public void setSpo2(int spo2) {
            this.spo2 = spo2;
        }

        public int getIr() {
            return ir;
        }

        public void setIr(int ir) {
            this.ir = ir;
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

        public DataListMax30100(int id, int user_id, int bmp, int spo2, int ir, String create_at, String update_at) {
            this.id = id;
            this.user_id = user_id;
            this.bmp = bmp;
            this.spo2 = spo2;
            this.ir = ir;
            this.create_at = create_at;
            this.update_at = update_at;
        }
    }
}
