package com.wordpress.omanandj.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 26/07/13
 * Time: 11:08 PM
 */
public class User implements Serializable {
    public static final String OBJECT_KEY = "USER";

    private Long  id;
    private String  name;
    private String  password;
    private Integer age;
    private String  organization;

    public User(Long id, String name, String password, Integer age, String organization) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", organization='" + organization + '\'' +
                ", age=" + age +
                '}';
    }
}
