package org.cardGame.PokerCard;

import lombok.Getter;
import org.cardGame.CardType.CardType;

@Getter
public enum CardFace {
    THREE("3", 0, 4),
    FOUR("4", 1, 4),
    FIVE("5", 2, 4),
    SIX("6", 3, 4),
    SEVEN("7", 4, 4),
    EIGHT("8", 5, 4),
    NINE("9", 6, 4),
    TEN("10", 7, 4),
    JACK("J", 8, 4),
    QUEEN("Q", 9, 4),
    KING("k", 10, 4),
    ACE("A", 11, 4),
    TWO("2", 12, 4),
    JGHOST("小鬼", 13, 1),
    DGHOST("大鬼", 14, 1),
    ;

    private final String value;
    private final int priority;
    private final int maxCount;

    CardFace(String value, int priority, int maxCount) {
        this.value = value;
        this.priority = priority;
        this.maxCount = maxCount;
    }

    private CardFace getCardFaceByPriority(int priority) {
        for (CardFace cardFace : CardFace.values()) {
            if (cardFace.getPriority() == priority) {
                return cardFace;
            }
        }
        return null;
    }


    public CardFace getBetterCardFace() {
        return this.priority == CardFace.values()[CardFace.values().length - 1].getPriority() ? this : getCardFaceByPriority(this.priority + 1);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
