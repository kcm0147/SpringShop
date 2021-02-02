package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // single Table 전략이기때문에 Book을 구분 할 수 있는 구분자를 선언한
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
