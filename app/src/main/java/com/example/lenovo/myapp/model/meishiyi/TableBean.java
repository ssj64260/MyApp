package com.example.lenovo.myapp.model.meishiyi;

import java.io.Serializable;

/**
 * 美食易时间段
 */

public class TableBean implements Serializable {

    private String diningStatus;
    private String tableId;
    private String tableName;
    private String tableType;
    private String seats;
    private String desks;
    private String sequence;

    public String getDiningStatus() {
        return diningStatus;
    }

    public void setDiningStatus(String diningStatus) {
        this.diningStatus = diningStatus;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDesks() {
        return desks;
    }

    public void setDesks(String desks) {
        this.desks = desks;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
