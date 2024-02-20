package org.cardGame.CardType;

import org.cardGame.CardList.CardList;
import org.cardGame.CardListUtils.CardListUtils;
import org.cardGame.CardTypeStrategy.*;


/**
 * @author IDeLoveYou
 * @since 2024/2/9 2:07
 **/
public class PokerTypeContext {
    private CardTypeStrategyHandler chain;

    public PokerTypeContext() {
        buildChain();
    }

    private void buildChain() {
        chain = new BombCardStrategyHandler();
        chain.setNextHandler(new ContinuousCardStrategyHandler())
                .setNextHandler(new PlaneCardStrategyHandler())
                .setNextHandler(new StackCardStrategyHandler())
                .setNextHandler(new ThreeWithCardHandler());
    }

    public CardType parsePokerType(CardList cardList) {
        //解析牌型前先排序可以节省很多判断的步骤
        cardList.sort();
        return CardListUtils.isCorrectCardList(cardList) ? chain.getCardType(cardList) : CardType.ERROR_CARD;
    }

    public CardList getBetterCards(CardList cardList, CardType cardType) {
        return switch (cardType) {
            case BOMB_CARD, BOMB_WITH_TWO_SINGLE_CARD, BOMB_WITH_TWO_PAIR_CARD ->
                    new BombCardStrategyHandler().getBetterCardList(cardList, cardType);
            case CONTINUOUS_SINGLE_CARD, CONTINUOUS_PAIR_CARD ->
                    new ContinuousCardStrategyHandler().getBetterCardList(cardList, cardType);
            case PLANE_CARD, PLANE_WITH_SINGLE_WING_CARD, PLANE_WITH_PAIR_WINGS_CARD ->
                    new PlaneCardStrategyHandler().getBetterCardList(cardList, cardType);
            case SINGLE_CARD, PAIR_CARD, TRIPLE_CARD ->
                    new StackCardStrategyHandler().getBetterCardList(cardList, cardType);
            case THREE_WITH_SINGLE_CARD, THREE_WITH_PAIR_CARD ->
                    new ThreeWithCardHandler().getBetterCardList(cardList, cardType);
            default -> null;
        };
    }
}
