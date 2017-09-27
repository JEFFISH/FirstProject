package entity;

import java.io.Serializable;

public class Student implements Serializable {
	private String name;
	private int age;
	private String sex;
	private int id;
	private Banji banji;

	
	public Banji getBanji() {
		return banji;
	}

	public void setBanji(Banji banji) {
		this.banji = banji;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
