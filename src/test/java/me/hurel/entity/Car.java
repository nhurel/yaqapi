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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_CAR")
public class Car {

    private Long id;

    private Date releaseDate;

    private Date sellDate;

    private String model;

    private BigDecimal price;

    private Boolean hybrid;

    private User owner;

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

    public Boolean isHybrid() {
	return hybrid;
    }

    @Column(name = "SELL_DATE")
    public Date getSellDate() {
	return sellDate;
    }

    public void setSellDate(Date sellDate) {
	this.sellDate = sellDate;
    }

    public void setHybrid(Boolean hybrid) {
	this.hybrid = hybrid;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getOwner() {
	return owner;
    }

    public void setOwner(User owner) {
	this.owner = owner;
    }

}
