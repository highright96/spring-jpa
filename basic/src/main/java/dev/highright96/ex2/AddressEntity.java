package dev.highright96.ex2;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    public AddressEntity(String city, String street, String zipcode){
        this.address = new Address(city, street, zipcode);
    }
}
