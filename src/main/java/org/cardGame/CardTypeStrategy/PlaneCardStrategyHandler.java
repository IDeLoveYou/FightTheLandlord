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
        //基本验证
        if (cards.size() < 6 || cards.containsFace(TWO) || cards.containsFace(JGHOST) || cards.containsFace(DGHOST)) {
            return processNextHandler(cards);
        }

        //筛选出卡牌数量为3为飞机头
        CardList planeHeads = cards.subCardListByFaceCount(3);

        //不符合飞机头连续的条件
        if (!planeHeads.isDistinctContinuousCardFace()) {
            return super.nextHandler.getCardType(planeHeads);
        }

        //判断是否只是飞机
        if (cards.size() == planeHeads.size() * 3) {
            return CardType.PLANE_CARD;
        }

        //飞机带单牌策略（尾巴需要带同等数量的单牌）&&（尾巴卡牌种类是否都为单）
        if (cards.size() == planeHeads.size() + planeHeads.size() / 3 && cards.getCardFaceCountMap().values().stream().filter(entry -> entry != 3L).allMatch(entry -> entry == 1)) {
            return CardType.PLANE_WITH_SINGLE_WING_CARD;
        }

        //飞机带双牌策略（尾巴需要带同等数量的双牌）&&（尾巴卡牌种类是否都为双）
        if (cards.size() == planeHeads.size() + planeHeads.size() / 3 * 2 && cards.getCardFaceCountMap().values().stream().filter(entry -> entry != 3L).allMatch(entry -> entry == 2)) {
            return CardType.PLANE_WITH_PAIR_WINGS_CARD;
        }

        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        CardList pokerCards = cards.subCardListByFaceCount(3);

        //已经是最大的炸，返回高一级别的牌型
        if (pokerCards.containsFace(ACE)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        //改变带牌为最小
        return switch (cardType) {
            case PLANE_CARD -> pokerCards.getBetterPriorityFace();
            case PLANE_WITH_SINGLE_WING_CARD -> pokerCards.getBetterPriorityFace().addMinSingleCard(pokerCards.size() / 3);
            case PLANE_WITH_PAIR_WINGS_CARD -> pokerCards.getBetterPriorityFace().addMinPairCard(pokerCards.size() / 3);
            default -> null;
        };
    }
}
