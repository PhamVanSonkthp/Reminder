package com.infinity.reminder.model_objects;

import java.util.List;

public class WifiValue {
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
