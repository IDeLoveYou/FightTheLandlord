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

        //炸弹
        if (cards.size() == 4 && cards.getCardFaceKindsCount() == 1) {
            return CardType.BOMB_CARD;
        }

        //炸弹带两单
        if (cards.size() == 6 && cards.getCardFaceCountMap().containsValue(4L) && cards.getCardFaceCountMap().containsValue(1L)) {
            return CardType.BOMB_WITH_TWO_SINGLE_CARD;
        }

        //炸弹带两双
        if (cards.size() == 8 && cards.getCardFaceCountMap().containsValue(4L) && cards.getCardFaceCountMap().containsValue(2L)) {
            return CardType.BOMB_WITH_TWO_PAIR_CARD;
        }

        //全都不是交给下个责任链路
        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        CardList pokerCards = cards.subCardListByFaceCount(4);

        //已经是最大的炸，返回高一级别的牌型
        if (pokerCards.containsFace(TWO)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        //改变带牌为最小
        return switch (cardType) {
            case BOMB_CARD -> pokerCards.getBetterPriorityFace();
            case BOMB_WITH_TWO_SINGLE_CARD -> pokerCards.getBetterPriorityFace().addMinSingleCard(2);
            case BOMB_WITH_TWO_PAIR_CARD -> pokerCards.getBetterPriorityFace().addMinPairCard(2);
            default -> null;
        };
    }
}
