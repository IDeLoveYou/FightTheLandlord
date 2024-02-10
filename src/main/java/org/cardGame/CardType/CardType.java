package org.cardGame.CardType;

import lombok.Getter;
import org.cardGame.CardList.CardList;

import static org.cardGame.PokerCard.CardFace.*;

@Getter
public enum CardType {
    ERROR_CARD("错误牌型", -1, null),
    SINGLE_CARD("单牌", 0, new CardList(THREE)),
    PAIR_CARD("对子", 0, new CardList(THREE, THREE)),
    TRIPLE_CARD("三张", 0, new CardList(THREE, THREE, THREE)),
    THREE_WITH_SINGLE_CARD("三带一", 0, new CardList(THREE, THREE, THREE, FOUR)),
    THREE_WITH_PAIR_CARD("三带二", 0, new CardList(THREE, THREE, THREE, FOUR, FOUR)),
    CONTINUOUS_SINGLE_CARD("顺子", 0, new CardList(THREE, FOUR, FIVE, SIX, SEVEN)),
    CONTINUOUS_PAIR_CARD("连对", 0, new CardList(THREE, THREE, FOUR, FOUR, FIVE, FIVE)),
    PLANE_CARD("飞机", 0, new CardList(THREE, THREE, THREE)),
    PLANE_WITH_SINGLE_WING_CARD("飞机带单牌", 0, new CardList(THREE, THREE, THREE, FOUR)),
    PLANE_WITH_PAIR_WINGS_CARD("飞机带双牌", 0, new CardList(THREE, THREE, THREE, FOUR, FOUR)),
    BOMB_WITH_TWO_SINGLE_CARD("四带两单牌", 0, new CardList(THREE, THREE, THREE, THREE, FOUR, FIVE)),
    BOMB_WITH_TWO_PAIR_CARD("四带两对牌", 0, new CardList(THREE, THREE, THREE, THREE, FOUR, FOUR, FIVE, FIVE)),
    BOMB_CARD("炸弹", 1, new CardList(THREE, THREE, THREE, THREE)),
    KING_BOMB_CARD("王炸", 2, new CardList(JGHOST, DGHOST)),
    ;

    private final String value;
    private final int priority;
    private final CardList minCardList;

    CardType(String value, int priority, CardList minCardList) {
        this.value = value;
        this.priority = priority;
        this.minCardList = minCardList;
    }

    private CardType getCardTypeByPriority(int priority) {
        for (CardType cardType : CardType.values()) {
            if (cardType.getPriority() == priority) {
                return cardType;
            }
        }
        return null;
    }

    public CardType getBetterCardType() {
        return this.priority == CardType.values()[CardType.values().length - 1].getPriority() ? this : getCardTypeByPriority(this.priority + 1);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
