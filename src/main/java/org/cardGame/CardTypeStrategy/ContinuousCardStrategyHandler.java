package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;
import static org.cardGame.PokerCard.CardFace.*;


/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:54
 **/
public class ContinuousCardStrategyHandler extends CardTypeStrategyHandler {
    @Override
    public CardType getCardType(CardList cards) {
        //基本卡牌数量验证
        if (cards.size() < 5 || cards.size() > 24 || cards.containsFace(TWO) || cards.containsFace(JGHOST) || cards.containsFace(DGHOST)) {
            return processNextHandler(cards);
        }

        //每种牌的数量都是1，且是连牌是顺子
        if (cards.getCardFaceCountMap().values().stream().allMatch(i -> i == 1) && cards.isContinuousCardFace()) {
            return CardType.CONTINUOUS_SINGLE_CARD;
        }

        //每种牌的数量都是2，且是连牌是连对
        if (cards.getCardFaceCountMap().values().stream().allMatch(i -> i == 2) && cards.isDistinctContinuousCardFace()) {
            return CardType.CONTINUOUS_PAIR_CARD;
        }

        //全都不是交给下个责任链路
        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        //已经是最大的顺子，返回高一级别的牌型
        if (cards.containsFace(ACE)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        return cards.getBetterPriorityFace();
    }
}
