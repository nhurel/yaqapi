package me.hurel.entity;

public class User {

    private String firstName;

    private String lastName;

    private Adress adress;

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
