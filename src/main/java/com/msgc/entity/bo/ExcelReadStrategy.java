package com.msgc.entity.bo;

import com.msgc.entity.dto.StrategyParam;

import java.util.Objects;

public class ExcelReadStrategy {
    private String id;
    private Integer userId;
    private String name;
    private Boolean tableNameStrategy;
    private Integer sheetIndex;
    private Integer field_position;
    private Integer start;
    private Integer fieldNum;
    private Boolean withDefaultValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTableNameStrategy() {
        return tableNameStrategy;
    }

    public void setTableNameStrategy(Boolean tableNameStrategy) {
        this.tableNameStrategy = tableNameStrategy;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Integer getField_position() {
        return field_position;
    }

    public void setField_position(Integer field_position) {
        this.field_position = field_position;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(Integer fieldNum) {
        this.fieldNum = fieldNum;
    }

    public Boolean getWithDefaultValue() {
        return withDefaultValue;
    }

    public void setWithDefaultValue(Boolean withDefaultValue) {
        this.withDefaultValue = withDefaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelReadStrategy that = (ExcelReadStrategy) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(tableNameStrategy, that.tableNameStrategy) &&
                Objects.equals(sheetIndex, that.sheetIndex) &&
                Objects.equals(field_position, that.field_position) &&
                Objects.equals(start, that.start) &&
                Objects.equals(fieldNum, that.fieldNum) &&
                Objects.equals(withDefaultValue, that.withDefaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, tableNameStrategy, sheetIndex, field_position, start, fieldNum, withDefaultValue);
    }

    public ExcelReadStrategy() { }

    public ExcelReadStrategy(StrategyParam fileAndStrategy) {
        this.tableNameStrategy = Boolean.valueOf(fileAndStrategy.getTableNameBy());
        this.sheetIndex = Integer.valueOf(fileAndStrategy.getSheetIndex());
        this.field_position = Integer.parseInt(fileAndStrategy.getFieldLine());
        this.start = Integer.parseInt(fileAndStrategy.getFieldStart());
        this.fieldNum = Integer.parseInt(fileAndStrategy.getFieldNum());
        this.withDefaultValue = "on".equals(fileAndStrategy.getWithDefaultValue());
    }
}

