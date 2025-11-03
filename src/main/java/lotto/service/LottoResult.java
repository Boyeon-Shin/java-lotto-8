package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> result;

    public LottoResult() {
        this.result = initializeResult();
    }

    private Map<Rank, Integer> initializeResult() {
        Map<Rank, Integer> map = new EnumMap<>(Rank.class);
        map.put(Rank.FIRST, 0);
        map.put(Rank.SECOND, 0);
        map.put(Rank.THIRD, 0);
        map.put(Rank.FOURTH, 0);
        map.put(Rank.FIFTH, 0);
        return map;
    }

    public void calculateResult(List<Lotto> lottos, List<Integer> winningNumbers, int bonusNumber) {
        for (Lotto lotto : lottos) {
            Rank rank = determineRank(lotto, winningNumbers, bonusNumber);
            updateResult(rank);
        }
    }

    private Rank determineRank(Lotto lotto, List<Integer> winningNumbers, int bonusNumber) {
        int matchCount = lotto.countMatch(winningNumbers);
        boolean matchBonus = lotto.contains(bonusNumber);
        return Rank.valueOf(matchCount, matchBonus);
    }

    private void updateResult(Rank rank) {
        if (rank == Rank.NONE) {
            return;
        }
        result.put(rank, result.get(rank) + 1);
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrizeMoney = calculateTotalPrizeMoney();
        return (double) totalPrizeMoney / purchaseAmount * 100;
    }

    private long calculateTotalPrizeMoney() {
        return result.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }

    public Map<Rank, Integer> getResult() {
        return result;
    }
}

