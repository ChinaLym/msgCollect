package com.msgc.constant.enums;

import com.msgc.entity.Table;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName:  TabletatusEnum
 * @Description:
 *      收集表的状态（生命周期）
 * @Author:         LYM
 * @CreateDate:     2019/2/26 16:59
*/
public enum TableStatusEnum {
	EDIT("编辑中", "EDIT"),				//编辑中,还未发布
	PUBLISHED("已发布", "PUBLISHED"),	//已发布，但未开始
	COLLECTING("收集中", "COLLECTING"),	//数据收集正在进行中
	END("已截止", "END"),				//已停止
	DELETE("已删除", "DELETE");			//已删除

	private String name;
	private String value;

	TableStatusEnum(String name, String value){
		this.name = name;
		this.value = value;
	}

	/**
	 * 根据数据库字段，映射成中文
	 * 使用 BeanUtils 拷贝（浅拷贝）
	 */
	public static Table processTableState(Table table){
		Table newTable = new Table();
		BeanUtils.copyProperties(table, newTable);
		if(EDIT.equal(table.getState()))
			newTable.setState(EDIT.name);
		else if(PUBLISHED.equal(table.getState()))
			newTable.setState(PUBLISHED.name);
		else if(COLLECTING.equal(table.getState()))
			newTable.setState(COLLECTING.name);
		else if(END.equal(table.getState()))
			newTable.setState(END.name);
		else if(DELETE.equal(table.getState()))
			newTable.setState(DELETE.name);
		return newTable;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public boolean equal(Object obj){
		return this.value.equals(obj);
	}
}
