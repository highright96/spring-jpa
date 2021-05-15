package dev.highright96.ex2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(length = 5)
    private String city;

    @Column(length = 10)
    private String street;

    @Column(length = 10)
    private String zipcode;

    /*
    값 타입을 따로 두면 아래와 같은 의미있는 비즈니스 메서드를
    따로 빼서 관리할 수 있다.
    매우 객체지향적!!
    */
    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }
}
