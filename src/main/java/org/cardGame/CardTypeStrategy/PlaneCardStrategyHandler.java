package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;

import static org.cardGame.PokerCard.CardFace.*;


/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:54
 **/
public class PlaneCardStrategyHandler extends CardTypeStrategyHandler {
    @Override
    public CardType getCardType(CardList cards) {
        //获取飞机牌
        CardList planeCard = cards.getPlaneCard();

        //基本验证
        if (cards.size() < 6 || cards.containsFace(TWO) || cards.containsFace(JGHOST) || cards.containsFace(DGHOST) || planeCard.isEmpty()) {
            return processNextHandler(cards);
        }

        //判断是否只是飞机
        if (cards.size() == planeCard.size()) {
            return CardType.PLANE_CARD;
        }

        //飞机带单牌策略（尾巴需要带同等数量的单牌）&&（尾巴卡牌种类是否都为单）
        if (cards.size() == planeCard.size() + planeCard.size() / 3 && cards.getPlaneWithCard().isSingleOfCards()) {
            return CardType.PLANE_WITH_SINGLE_WING_CARD;
        }

        //飞机带双牌策略（尾巴需要带同等数量的双牌）&&（尾巴卡牌种类是否都为双）
        if (cards.size() == planeCard.size() + planeCard.size() / 3 * 2 && cards.getPlaneWithCard().isPairOfCards()) {
            return CardType.PLANE_WITH_PAIR_WINGS_CARD;
        }

        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        CardList planeCard = cards.getPlaneCard();

        //已经是最大的炸，返回高一级别的牌型
        if (planeCard.containsFace(ACE)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        //改变带牌为最小
        return switch (cardType) {
            case PLANE_CARD -> planeCard.getAllBetterCard();
            case PLANE_WITH_SINGLE_WING_CARD ->
                    planeCard.getAllBetterCard().addSingleWithCardByCount(planeCard.size() / 3);
            case PLANE_WITH_PAIR_WINGS_CARD ->
                    planeCard.getAllBetterCard().addPairWithCardByCount(planeCard.size() / 3);
            default -> null;
        };
    }
}
