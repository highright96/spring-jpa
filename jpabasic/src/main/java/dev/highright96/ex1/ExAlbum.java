package dev.highright96.ex1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class ExAlbum extends ExItem {
    private String artist;
}
