package me.hurel.entity;

import java.util.List;

public class User {

    private String firstName;

    private String lastName;

    private int age;

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    private Adress adress;

    private List<User> children;

    public List<User> getChildren() {
	return children;
    }

    public void setChildren(List<User> children) {
	this.children = children;
    }

    public Adress getAdress() {
	return adress;
    }

    public void setAdress(Adress adress) {
	this.adress = adress;
    }

    public User() {
	super();
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

}
