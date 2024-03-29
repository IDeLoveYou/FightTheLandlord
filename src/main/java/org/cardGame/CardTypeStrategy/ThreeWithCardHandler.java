package org.cardGame.CardTypeStrategy;

import org.cardGame.CardList.CardList;
import org.cardGame.CardType.CardType;

import static org.cardGame.PokerCard.CardFace.*;

/**
 * @author IDeLoveYou
 * @since 2024/2/7 23:54
 **/
public class ThreeWithCardHandler extends CardTypeStrategyHandler {
    @Override
    public CardType getCardType(CardList cards) {
        //三带二是两种牌型以及某种牌型需要三张
        if (cards.getThreeCard().isEmpty()) {
            return processNextHandler(cards);
        }

        //三带一
        if (cards.size() == 4) {
            return CardType.THREE_WITH_SINGLE_CARD;
        }

        //三带二
        if (cards.size() == 5) {
            return CardType.THREE_WITH_PAIR_CARD;
        }

        return processNextHandler(cards);
    }

    @Override
    public CardList getBetterCardList(CardList cards, CardType cardType) {
        CardList threeCard = cards.getThreeCard();

        //已经是最大的牌，返回高一级别的牌型
        if (threeCard.containsFace(TWO)) {
            return cardType.getBetterCardType().getMinCardList();
        }

        //改变带牌为最小
        return switch (cardType) {
            case THREE_WITH_SINGLE_CARD -> threeCard.getAllBetterCard().addSingleWithCardByCount(1);
            case THREE_WITH_PAIR_CARD -> threeCard.getAllBetterCard().addPairWithCardByCount(1);
            default -> null;
        };
    }
}
