package me.hurel.entity;

public class Adress {

    private String street;

    private String number;

    private City city;

    public Adress() {
	super();
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public City getCity() {
	return city;
    }

    public void setCity(City city) {
	this.city = city;
    }

}
