package org.cardGame.CardListUtils;

import org.cardGame.CardList.CardList;
import org.cardGame.PokerCard.CardFace;
import org.cardGame.PokerCard.PokerCard;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author IDeLoveYou
 * @since 2024/2/20 12:18
 **/
public class CardListUtils {
    //是否是正确的牌型组合
    public static boolean isCorrectCardList(CardList cardList) {
        Map<CardFace, Long> cardFaceCountMap = getCardFaceStackCountMap(cardList);
        //输入卡牌数量应该遵守规则，小于最大卡牌数
        return cardFaceCountMap.keySet().stream().allMatch(cardFace -> cardFaceCountMap.get(cardFace) <= cardFace.getMaxCount());
    }

    //获取卡牌中各牌面的牌面
    public static List<CardFace> getCardFaceList(CardList cardList) {
        return cardList.stream()
                .map(PokerCard::getFace)
                .toList();
    }

    //获得卡牌中各牌型的数量map
    public static Map<CardFace, Long> getCardFaceStackCountMap(CardList cardList) {
        return getCardFaceList(cardList).stream()
                .collect(Collectors.groupingBy(cardFace -> cardFace, Collectors.counting()));
    }

    //获得剩余可用卡牌中各牌型的数量map
    public static Map<CardFace, Long> getAvailableCardFaceCountMap(CardList cardList) {
        // 直接在流操作中计算剩余数量并按优先级降序排序
        return Arrays.stream(CardFace.values())
                .collect(Collectors.toMap(
                        cardFace -> cardFace,
                        cardFace -> cardFace.getMaxCount() - getCardFaceStackCountMap(cardList).getOrDefault(cardFace, 0L),
                        (oldValue, newValue) -> oldValue, // 处理键冲突（这里直接返回旧值，因为键应该是唯一的）
                        LinkedHashMap::new));
    }

    //去重后判断是否是连续牌
    public static Boolean isContinuousCardFace(CardList cardList) {
        //去重
        List<CardFace> cardFaceList = getCardFaceList(cardList).stream().distinct().toList();
        return cardFaceList.stream()
                .skip(1) // 跳过第一个元素，因为没有前一个元素与之比较
                .allMatch(cardFace -> cardFace.getPriority() - cardFaceList.get(cardFaceList.indexOf(cardFace) - 1).getPriority() == 1);
    }

    //通过相同卡牌的数量获取这些卡牌
    public static CardList subCardListByStackFaceCount(CardList cardList, int count) {
        return cardList.stream()
                .collect(Collectors.groupingBy(PokerCard::getFace, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .flatMap(entry -> cardList.stream()
                        .filter(card -> card.getFace() == entry.getKey())
                        .limit(count))  // 限制只取count个相同的cardFace的Card对象
                .sorted(Comparator.comparingInt(PokerCard::getFacePriority))
                .collect(Collectors.toCollection(CardList::new));  // 将结果转换为CardList类型
    }

    //将所有牌面设置为更大的牌面
    public static CardList setBetterPriorityFace(CardList cardList) {
        return cardList.stream()
                .map(PokerCard::new)
                .peek(PokerCard::setBetterFace)
                .collect(Collectors.toCollection(CardList::new));
    }

    //添加最小的若干数量叠牌
    public static CardList addMinStackCard(CardList cardList, int count, int stackCount) {
        getAvailableCardFaceCountMap(cardList).entrySet().stream()
                .filter(entry -> entry.getValue() >= 1)
                .limit(count)
                .forEach(entry -> {
                    for (int i = 0; i < stackCount; i++) {
                        cardList.add(new PokerCard(entry.getKey()));
                    }
                });
        return cardList;
    }

    //是否各种卡牌的数量都为faceCount
    public static Boolean isAllFaceCountMatch(CardList cardList, int faceCount) {
        return getCardFaceStackCountMap(cardList).values().stream().allMatch(i -> i == faceCount);
    }
}
