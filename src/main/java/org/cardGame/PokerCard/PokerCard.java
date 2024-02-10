package org.cardGame.PokerCard;

import lombok.Builder;
import lombok.Data;
import java.util.Objects;

/**
 * @author IDeLoveYou
 * @since 2024/1/31 16:04
 **/
@Data
@Builder
public class PokerCard {
    private CardFace face; // 牌面
    private CardSuit suit; // 花色

    public PokerCard(PokerCard pokerCard) {
        this.face = pokerCard.face;
        this.suit = pokerCard.suit;
    }

    // 获取优先级
    public int getFacePriority() {
        return face.getPriority();
    }

    public void setBetterFace() {
        this.face = face.getBetterCardFace();
    }

    public String toString() {
        return this.face.getValue() + this.suit.getValue();
    }

    public PokerCard(CardFace face) {
        this.face = face;
        this.suit = CardSuit.ANY;
    }

    public PokerCard(CardFace face, CardSuit suit) {
        this.face = face;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokerCard pokerCard = (PokerCard) o;
        return face == pokerCard.face && suit == pokerCard.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(face, suit);
    }

}
