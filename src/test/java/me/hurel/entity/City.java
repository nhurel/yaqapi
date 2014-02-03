package me.hurel.entity;

public class City {

    private String name;

    private Country country;

    public City() {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Country getCountry() {
	return country;
    }

    public void setCountry(Country country) {
	this.country = country;
    }

}
