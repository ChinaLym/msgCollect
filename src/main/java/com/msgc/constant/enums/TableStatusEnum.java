package com.msgc.constant.enums;

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
	 * 该方法被调用全在 TableController 中，由于 JDK 自带的Array是浅拷贝，因此如果调用该方法，有可能会修改缓存中的值，因此不再枚举中即不更改
	 */
	public static String getNameBy(String value){
		if(EDIT.equal(value))
			return EDIT.name;
		if(PUBLISHED.equal(value))
			return PUBLISHED.name;
		if(COLLECTING.equal(value))
			return COLLECTING.name;
		if(END.equal(value))
			return END.name;
		if(DELETE.equal(value))
			return DELETE.name;
		return value;
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
