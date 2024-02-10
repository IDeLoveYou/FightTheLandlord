package org.cardGame.CardType;

import org.cardGame.CardList.CardList;
import org.cardGame.CardTypeStrategy.*;
import org.cardGame.PokerCard.CardFace;

import java.util.Map;

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

    //是否是正确的牌型组合
    private boolean isCorrectCardList(CardList cardList) {
        Map<CardFace, Long> cardFaceCountMap = cardList.getCardFaceCountMap();
        //输入卡牌数量应该遵守规则，小于最大卡牌数
        return cardFaceCountMap.keySet().stream().anyMatch(cardFace -> cardFaceCountMap.get(cardFace) <= cardFace.getMaxCount());
    }

    public CardType parsePokerType(CardList cardList) {
        return isCorrectCardList(cardList) ? chain.getCardType(cardList.sort()) : CardType.ERROR_CARD;
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
