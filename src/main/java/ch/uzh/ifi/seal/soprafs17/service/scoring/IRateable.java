package ch.uzh.ifi.seal.soprafs17.service.scoring;


import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;

public interface IRateable {

    public boolean supports(BuildingSite buildingSite);

    public void score(BuildingSite buildingSite);

    public void scoreNow(BuildingSite buildingSite);

    public void scoreEndOfRound(BuildingSite buildingSite);

    public void scoreEndOfGame(BuildingSite buildingSite);

}
