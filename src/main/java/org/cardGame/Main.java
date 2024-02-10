package org.cardGame;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;
import org.cardGame.CardType.PokerTypeContext;

import static org.cardGame.PokerCard.CardFace.*;

public class Main {
    public static void main(String[] args) {
        //请不要输入不存在的牌，例三张鬼牌、五张三等
        CardList pokerCards = new CardList(THREE, THREE, THREE, TWO);

        System.out.println("输入：" + pokerCards);

        PokerTypeContext pokerTypeContext = new PokerTypeContext();

        CardType cardType = pokerTypeContext.parsePokerType(pokerCards);
        System.out.println("牌型是：" + cardType);

        CardList betterCards = pokerTypeContext.getBetterCards(pokerCards, cardType);
        System.out.println("更好的牌组合是：" + betterCards);
    }
}