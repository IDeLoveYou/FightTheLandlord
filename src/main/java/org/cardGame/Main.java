package org.cardGame;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;
import org.cardGame.CardType.PokerTypeContext;

import static org.cardGame.PokerCard.CardFace.*;

public class Main {
    private static void test(CardList pokerCards) {
        System.out.println("输入：" + pokerCards);

        PokerTypeContext pokerTypeContext = new PokerTypeContext();

        CardType cardType = pokerTypeContext.parsePokerType(pokerCards);
        System.out.println("牌型是：" + cardType);

        CardList betterCards = pokerTypeContext.getBetterCards(pokerCards, cardType);
        System.out.println("更好的牌组合是：" + betterCards + "\n");
    }

    public static void main(String[] args) {
        CardList pokerCards1 = new CardList(THREE, THREE, THREE, THREE, THREE, FOUR, FOUR, FOUR, FIVE, SIX);
        test(pokerCards1);

        CardList pokerCards2 = new CardList(THREE, THREE, THREE, THREE, FOUR, FOUR, FOUR, FIVE, SIX);
        test(pokerCards2);

        CardList pokerCards3 = new CardList(THREE, THREE, THREE, THREE, SIX, SEVEN);
        test(pokerCards3);

        CardList pokerCards4 = new CardList(THREE, THREE, THREE, FOUR, FOUR, FOUR, FIVE, SIX);
        test(pokerCards4);

        CardList pokerCards5 = new CardList(THREE, THREE, FOUR, FOUR, FIVE, FIVE, SIX, SIX);
        test(pokerCards5);
    }
}