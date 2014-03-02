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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private Adress adress;

    private List<User> children;

    private User father;

    public User() {
	super();
    }

    @Column(name = "AGE")
    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "father")
    public List<User> getChildren() {
	return children;
    }

    public void setChildren(List<User> children) {
	this.children = children;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adress_id")
    public Adress getAdress() {
	return adress;
    }

    public void setAdress(Adress adress) {
	this.adress = adress;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    @Column(name = "LASTNAME")
    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    @ManyToOne
    @JoinColumn(name = "FATHER_ID")
    public User getFather() {
	return father;
    }

    public void setFather(User father) {
	this.father = father;
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

}
