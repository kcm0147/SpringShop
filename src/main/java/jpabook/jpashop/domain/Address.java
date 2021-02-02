package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;


@Embeddable // 내장타입으로 사용한다는 것을 선언해준다, 값 타입은 변경이 불가능하게 선언해야하기 때문에 Getter만을 둔다
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
