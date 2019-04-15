package com.msgc.entity.dto;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 传来的 excel解析策略
 */
public class StrategyParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableNameBy;
    private String sheetIndex;
    private String fieldLine;
    private String fieldStart;
    private String fieldNum;
    private String withDefaultValue;

    public String getTableNameBy() {
        return tableNameBy;
    }

    public void setTableNameBy(String tableNameBy) {
        this.tableNameBy = tableNameBy;
    }

    public String getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(String sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getFieldLine() {
        return fieldLine;
    }

    public void setFieldLine(String fieldLine) {
        this.fieldLine = fieldLine;
    }

    public String getFieldStart() {
        return fieldStart;
    }

    public void setFieldStart(String fieldStart) {
        this.fieldStart = fieldStart;
    }

    public String getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(String fieldNum) {
        this.fieldNum = fieldNum;
    }

    public String getWithDefaultValue() {
        return withDefaultValue;
    }

    public void setWithDefaultValue(String withDefaultValue) {
        this.withDefaultValue = withDefaultValue;
    }

    public StrategyParam() {
    }

    public StrategyParam(HttpServletRequest request) {
        tableNameBy = request.getParameter("tableNameBy");
        sheetIndex = request.getParameter("sheetIndex");
        fieldLine = request.getParameter("fieldLine");
        fieldStart = request.getParameter("fieldStart");
        fieldNum = request.getParameter("fieldNum");
        withDefaultValue = request.getParameter("withDefaultValue");
    }

    @Override
    public String toString() {
        return "FileAndStrategyDTO{" +
                ", tableNameBy='" + tableNameBy + '\'' +
                ", sheetIndex='" + sheetIndex + '\'' +
                ", fieldLine='" + fieldLine + '\'' +
                ", fieldStart='" + fieldStart + '\'' +
                ", fieldEnd='" + fieldNum + '\'' +
                ", withDefaultValue='" + withDefaultValue + '\'' +
                '}';
    }
}
