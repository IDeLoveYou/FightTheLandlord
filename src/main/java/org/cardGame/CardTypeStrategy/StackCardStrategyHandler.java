package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;

import static org.cardGame.CardType.CardType.*;
import static org.cardGame.PokerCard.CardFace.*;

/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:54
 **/
public class StackCardStrategyHandler extends CardTypeStrategyHandler {
    @Override
    public CardType getCardType(CardList cards) {

        //单张
        if (cards.size() == 1 && cards.isSingleOfCards()) {
            return SINGLE_CARD;
        }

        //对子
        if (cards.size() == 2 && cards.isPairOfCards()) {
            return CardType.PAIR_CARD;
        }

        //三条
        if (cards.size() == 3 && cards.isTripleOfCards()) {
            return CardType.TRIPLE_CARD;
        }

        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        //已经是最大的牌，返回高一级别的牌型
        if ((cardType == SINGLE_CARD && cards.containsFace(DGHOST)) || cards.containsFace(TWO)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        return cards.getAllBetterCard();
    }
}
