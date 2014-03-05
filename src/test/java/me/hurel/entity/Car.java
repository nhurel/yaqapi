/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.entity;

import static javax.persistence.GenerationType.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CAR")
public class Car {

    private Long id;

    private Date releaseDate;

    private String model;

    private BigDecimal price;

    private List<String> someList;

    private Map<String, String> someMap;

    private Boolean hybrid;

    public Car() {
	super();
    }

    @Column(name = "RELEASE_DATE")
    public Date getReleaseDate() {
	return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
	this.releaseDate = releaseDate;
    }

    @Column(name = "MODEL")
    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    @Column(name = "PRICE")
    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Map<String, String> getSomeMap() {
	return someMap;
    }

    public void setSomeMap(Map<String, String> someMap) {
	this.someMap = someMap;
    }

    public List<String> getSomeList() {
	return someList;
    }

    public void setSomeList(List<String> someList) {
	this.someList = someList;
    }

    public Boolean isHybrid() {
	return hybrid;
    }

    public void setHybrid(Boolean hybrid) {
	this.hybrid = hybrid;
    }

}
