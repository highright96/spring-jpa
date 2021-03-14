package dev.highright96.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    public String city;
    public String street;
    public String zipcode;

    protected Address(){
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
