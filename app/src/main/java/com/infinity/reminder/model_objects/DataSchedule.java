package com.infinity.reminder.model_objects;

import java.util.List;

public class DataSchedule {
    private DataScheduleValue data;

    public DataSchedule(DataScheduleValue data) {
        this.data = data;
    }

    public DataScheduleValue getData() {
        return data;
    }

    public void setData(DataScheduleValue data) {
        this.data = data;
    }

    public class DataScheduleValue{
        int pageIndex;
        int pageSize;
        int totalItems;
        int totalPages;
        List<DataListSchedule> data;

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

        public List<DataListSchedule> getData() {
            return data;
        }

        public void setData(List<DataListSchedule> data) {
            this.data = data;
        }

        public DataScheduleValue(int pageIndex, int pageSize, int totalItems, int totalPages, List<DataListSchedule> data) {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
            this.totalItems = totalItems;
            this.totalPages = totalPages;
            this.data = data;
        }
    }

    public class DataListSchedule{
        int id;
        int type_alert;
        int status;
        int process;
        String content;
        String time;
        String create_by;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType_alert() {
            return type_alert;
        }

        public void setType_alert(int type_alert) {
            this.type_alert = type_alert;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getProcess() {
            return process;
        }

        public void setProcess(int process) {
            this.process = process;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public DataListSchedule(int id, int type_alert, int status, int process, String content, String time, String create_by) {
            this.id = id;
            this.type_alert = type_alert;
            this.status = status;
            this.process = process;
            this.content = content;
            this.time = time;
            this.create_by = create_by;
        }
    }
}
