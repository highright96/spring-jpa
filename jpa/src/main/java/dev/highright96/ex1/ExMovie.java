package dev.highright96.ex1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOVIE")
public class ExMovie extends ExItem {
    private String director;
    private String actor;
}
