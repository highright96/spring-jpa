package dev.highright96.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    public String city;
    public String street;
    public String zipcode;

    // 상속 받은 클래스들만 생성할 수 있게 제한
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
