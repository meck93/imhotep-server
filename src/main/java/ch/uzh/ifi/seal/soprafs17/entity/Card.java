package ch.uzh.ifi.seal.soprafs17.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Cristian on 25.03.2017.
 */

@Entity
public abstract class Card {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private Long roundCardId;

    private Long marketCardId;


}
