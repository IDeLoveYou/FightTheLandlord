package org.cardGame.PokerCard;

import lombok.Getter;

@Getter
public enum CardSuit {
    SPADE("♠", 4),
    CLUB("♣", 4),
    HEART("♥", 4),
    DIAMOND("♦", 4),
    ANY("某种花色", 4),
    ;
    private final String value;
    private final int maxCount;

    CardSuit(String value, int maxCount) {
        this.value = value;
        this.maxCount = maxCount;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
