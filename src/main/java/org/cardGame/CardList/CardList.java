package org.cardGame.CardList;

import lombok.Getter;
import org.cardGame.CardListUtils.CardListUtils;
import org.cardGame.PokerCard.CardFace;
import org.cardGame.PokerCard.CardSuit;
import org.cardGame.PokerCard.PokerCard;

import java.util.*;
import java.util.stream.IntStream;

import static org.cardGame.CardListUtils.CardListUtils.addMinStackCard;


/**
 * @author IDeLoveYou
 * @since 2024/2/8 0:06
 **/
@Getter
public class CardList extends ArrayList<PokerCard> {

    //快捷创建卡牌，便于测试输入
    public CardList(CardFace... cardFaces) {
        IntStream.range(0, cardFaces.length).forEach(i -> this.add(cardFaces[i]));
    }

    //默认使用优先级排序
    public void sort() {
        super.sort(Comparator.comparingInt(PokerCard::getFacePriority));
    }

    //添加单张卡牌（不带花色）
    public void add(CardFace cardFace) {
        this.add(new PokerCard(cardFace));
    }

    //添加单张卡牌（带花色）
    public void add(CardFace cardFace, CardSuit cardSuit) {
        this.add(new PokerCard(cardFace, cardSuit));
    }

    //是否包含某牌面
    public Boolean containsFace(CardFace cardFace) {
        return CardListUtils.getCardFaceList(this).contains(cardFace);
    }

    //以下用于拆分出复杂牌型
    public CardList getBombCard() {
        return CardListUtils.subCardListByStackFaceCount(this, 4);
    }

    public CardList getBombWithCard() {
        CardList withCard = (CardList) this.clone();
        withCard.removeAll(getBombCard());
        return withCard;
    }

    public CardList getPlaneCard() {
        CardList cardList = CardListUtils.subCardListByStackFaceCount(this, 3);
        return CardListUtils.isContinuousCardFace(cardList) ? cardList : new CardList();
    }

    public CardList getPlaneWithCard() {
        CardList withCard = (CardList) this.clone();
        withCard.removeAll(getPlaneCard());
        return withCard;
    }

    public CardList getThreeCard() {
        return CardListUtils.subCardListByStackFaceCount(this, 3);
    }

    public Boolean isSingleOfCards() {
        return CardListUtils.isAllFaceCountMatch(this, 1);
    }

    public Boolean isPairOfCards() {
        return CardListUtils.isAllFaceCountMatch(this, 2);
    }

    public Boolean isTripleOfCards() {
        return CardListUtils.isAllFaceCountMatch(this, 3);
    }

    //以下用于判断是否为连续牌
    public Boolean isContinuousCardByStackCount(int stackCount) {
        return CardListUtils.isAllFaceCountMatch(this, stackCount) && CardListUtils.isContinuousCardFace(this);
    }

    //以下函数用于自动出牌算法
    public CardList getAllBetterCard() {
        return CardListUtils.setBetterPriorityFace(this);
    }

    public CardList addSingleWithCardByCount(int count) {
        return addMinStackCard(this, count, 1);
    }

    public CardList addPairWithCardByCount(int count) {
        return addMinStackCard(this, count, 2);
    }

}
