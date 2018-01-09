package com.ytf.apple.entity;

import javax.persistence.*;

@Entity //注解指明这是一个和数据库表映射的实体类
public class City {
	@Id // 注解指明这个属性映射为数据库的主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 注解默认使用主键生成方式为自增。
	
	@Column(name="city_id") // 注解映射数据库字段名
	private long id;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
