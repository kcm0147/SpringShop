package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;


@Embeddable // 내장타입으로 사용한다는 것을 선언해준다
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
