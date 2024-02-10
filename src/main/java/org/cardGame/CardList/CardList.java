package org.cardGame.CardList;

import lombok.Getter;
import org.cardGame.PokerCard.CardFace;
import org.cardGame.PokerCard.CardSuit;
import org.cardGame.PokerCard.PokerCard;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public CardList sort() {
        super.sort(Comparator.comparingInt(PokerCard::getFacePriority));
        return this;
    }

    //添加单张卡牌（不带花色）
    public void add(CardFace cardFace) {
        this.add(new PokerCard(cardFace));
    }

    //添加单张卡牌（带花色）
    public void add(CardFace cardFace, CardSuit cardSuit) {
        this.add(new PokerCard(cardFace, cardSuit));
    }

    //获取卡牌中各牌面的牌面
    public List<CardFace> getCardFaceList() {
        return this.stream()
                .map(PokerCard::getFace)
                .toList();
    }

    //获得卡牌中各牌型的数量map
    public Map<CardFace, Long> getCardFaceCountMap() {
        return getCardFaceList().stream()
                .collect(Collectors.groupingBy(cardFace -> cardFace, Collectors.counting()));
    }

    //获得卡牌中各牌型的数量map
    public Map<CardFace, Long> getAvailableCardFaceCountMap() {
        // 直接在流操作中计算剩余数量并按优先级降序排序
        return Arrays.stream(CardFace.values())
                .collect(Collectors.toMap(
                        cardFace -> cardFace,
                        cardFace -> cardFace.getMaxCount() - getCardFaceCountMap().getOrDefault(cardFace, 0L),
                        (oldValue, newValue) -> oldValue, // 处理键冲突（这里直接返回旧值，因为键应该是唯一的）
                        LinkedHashMap::new));
    }

    //是否包含某牌面
    public Boolean containsFace(CardFace cardFace) {
        return getCardFaceList().contains(cardFace);
    }

    //获取卡牌中各牌面的数量
    public Integer getCardFaceKindsCount() {
        return getCardFaceCountMap().size();
    }

    //判断优先级是否连续
    public Boolean isPriorityContinuous(List<CardFace> cardFaceList) {
        return cardFaceList.stream()
                .skip(1) // 跳过第一个元素，因为没有前一个元素与之比较
                .allMatch(cardFace -> cardFace.getPriority() - cardFaceList.get(cardFaceList.indexOf(cardFace) - 1).getPriority() == 1);
    }

    //判断是否是连续牌
    public Boolean isContinuousCardFace() {
        return isPriorityContinuous(getCardFaceList());
    }

    //去重后判断是否是连续牌
    public Boolean isDistinctContinuousCardFace() {
        return isPriorityContinuous(getCardFaceList().stream().distinct().toList());
    }

    //通过相同卡牌的数量获取这些卡牌
    public CardList subCardListByFaceCount(int count) {
        return this.stream()
                .collect(Collectors.groupingBy(PokerCard::getFace, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .flatMap(entry -> this.stream()
                        .filter(card -> card.getFace() == entry.getKey())
                        .limit(count))  // 限制只取count个相同的cardFace的Card对象
                .collect(Collectors.toCollection(CardList::new));  // 将结果转换为CardList类型
    }

    //将所有牌面设置为更大的牌面
    public CardList getBetterPriorityFace() {
        return this.stream()
                .map(PokerCard::new)
                .peek(PokerCard::setBetterFace)
                .collect(Collectors.toCollection(CardList::new));
    }

    //添加最小的单牌
    public CardList addMinSingleCard(int count) {
        getAvailableCardFaceCountMap().entrySet().stream()
                .filter(entry -> entry.getValue() >= 1)
                .limit(count)
                .forEach(entry -> this.add(new PokerCard(entry.getKey())));
        return this;
    }

    //添加最小的双牌
    public CardList addMinPairCard(int count) {
        getAvailableCardFaceCountMap().entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .limit(count)
                .forEach(entry -> {
                    this.add(new PokerCard(entry.getKey()));
                    this.add(new PokerCard(entry.getKey()));
                });
        return this;
    }
}
