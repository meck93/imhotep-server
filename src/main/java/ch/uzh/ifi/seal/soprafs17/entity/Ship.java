package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Ship implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    //@ManyToOne(targetEntity = Round.class)
    //private Round round;
}
