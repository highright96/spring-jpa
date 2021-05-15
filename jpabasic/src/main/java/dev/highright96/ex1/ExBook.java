package dev.highright96.ex1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("B")
public class ExBook extends ExItem {
    private String author;
    private String isbn;
}
