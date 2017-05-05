package ch.uzh.ifi.seal.soprafs17.constant;


public enum MarketCardType {
    PAVED_PATH ("PAVED_PATH"),
    SARCOPHAGUS ("SARCOPHAGUS"),
    ENTRANCE ("ENTRANCE"),
    PYRAMID_DECORATION ("PYRAMID_DECORATION"),
    TEMPLE_DECORATION ("TEMPLE_DECORATION"),
    BURIAL_CHAMBER_DECORATION ("BURIAL_CHAMBER_DECORATION"),
    OBELISK_DECORATION ("OBELISK_DECORATION"),
    STATUE ("STATUE"),
    CHISEL ("CHISEL"),
    LEVER ("LEVER"),
    HAMMER ("HAMMER"),
    SAIL ("SAIL");

    private final String name;

    MarketCardType(String name){
        this.name = name;
    }

    public boolean equalsName(String otherName){
        return name.equals(otherName);
    }

    @Override
    public String toString(){
        return this.name;
    }
}