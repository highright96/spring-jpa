package dev.highright96.basic.ex2;

import javax.persistence.Entity;

@Entity
public class BasicMovie extends BasicItem{
    private String director;
    private String actor;
}
