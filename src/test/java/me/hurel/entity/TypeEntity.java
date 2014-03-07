package me.hurel.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

@Entity
public class TypeEntity {
    private List<String> someList;

    private Map<String, String> someMap;

    public TypeEntity() {
	super();
    }

    public List<String> getSomeList() {
	return someList;
    }

    public void setSomeList(List<String> someList) {
	this.someList = someList;
    }

    public Map<String, String> getSomeMap() {
	return someMap;
    }

    public void setSomeMap(Map<String, String> someMap) {
	this.someMap = someMap;
    }

}
