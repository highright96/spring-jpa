package dev.highright96.basic.ex2;

import javax.persistence.Entity;

@Entity
public class BasicBook extends BasicItem {
    private String author;
    private String isbn;
}
