package com.msgc.entity.dto;

import com.msgc.entity.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecentTable {
    private Table table;
    private boolean filled;
    Date operateTime;

    public RecentTable(){}

    public RecentTable(Table table){
        this.table = table;
        filled = false;
        operateTime = table.getCreateTime();
    }

    public RecentTable(Table table, Date operateTime){
        this.table = table;
        this.filled = true;
        this.operateTime = operateTime;
    }

}
