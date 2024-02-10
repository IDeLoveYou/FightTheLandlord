package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;

/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:52
 **/
public abstract class CardTypeStrategyHandler {
    protected CardTypeStrategyHandler nextHandler;

    public abstract CardType getCardType(CardList cards);

    public CardType processNextHandler(CardList cards) {
        return nextHandler != null ? nextHandler.getCardType(cards) : CardType.ERROR_CARD;
    }

    public abstract CardList getBetterCardList(CardList cards, CardType cardType);

    public CardTypeStrategyHandler setNextHandler(CardTypeStrategyHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }
}
