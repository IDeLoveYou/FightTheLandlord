package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;

import static org.cardGame.PokerCard.CardFace.*;

/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:54
 **/
public class BombCardStrategyHandler extends CardTypeStrategyHandler {
    @Override
    public CardType getCardType(CardList cards) {
        //王炸
        if (cards.size() == 2 && cards.containsFace(DGHOST) && cards.containsFace(JGHOST)) {
            return CardType.KING_BOMB_CARD;
        }

        CardList bombCard = cards.getBombCard();

        //没有炸弹牌
        if (bombCard.isEmpty()) {
            return processNextHandler(cards);
        }

        //炸弹
        if (cards.size() == 4) {
            return CardType.BOMB_CARD;
        }

        //炸弹带两单
        if (cards.size() == 6 && cards.getBombWithCard().isSingleOfCards()) {
            return CardType.BOMB_WITH_TWO_SINGLE_CARD;
        }

        //炸弹带两双
        if (cards.size() == 8 && cards.getBombWithCard().isPairOfCards()) {
            return CardType.BOMB_WITH_TWO_PAIR_CARD;
        }

        //全都不是交给下个责任链路
        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        CardList bombHead = cards.getBombCard();

        //已经是最大的炸，返回高一级别的牌型
        if (bombHead.containsFace(TWO)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        //改变带牌为最小
        return switch (cardType) {
            case BOMB_CARD -> bombHead.getAllBetterCard();
            case BOMB_WITH_TWO_SINGLE_CARD -> bombHead.getAllBetterCard().addSingleWithCardByCount(2);
            case BOMB_WITH_TWO_PAIR_CARD -> bombHead.getAllBetterCard().addPairWithCardByCount(2);
            default -> null;
        };
    }
}
