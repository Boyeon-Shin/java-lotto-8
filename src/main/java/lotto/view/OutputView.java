package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String PURCHASE_COUNT_FORMAT = "%d개를 구매했습니다.";
    private static final String LOTTO_FORMAT = "%s";
    private static final String WINNING_STATISTICS_TITLE = "당첨 통계\n---";
    private static final String WINNING_RESULT_FORMAT = "%d개 일치 (%s원) - %d개";
    private static final String WINNING_RESULT_WITH_BONUS_FORMAT = "%d개 일치, 보너스 볼 일치 (%s원) - %d개";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.";

    public void printPurchaseCount(int count) {
        System.out.println(String.format(PURCHASE_COUNT_FORMAT, count));
    }

    public void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }
        System.out.println();
    }

    private void printLotto(Lotto lotto) {
        List<Integer> numbers = lotto.getNumbers();
        List<Integer> sortedNumbers = sortNumbers(numbers);
        System.out.println(String.format(LOTTO_FORMAT, sortedNumbers));
    }

    private List<Integer> sortNumbers(List<Integer> numbers) {
        return numbers.stream()
                .sorted()
                .toList();
    }

    public void printWinningStatistics(Map<Rank, Integer> result, double profitRate) {
        System.out.println(WINNING_STATISTICS_TITLE);
        printRankResults(result);
        printProfitRate(profitRate);
    }

    private void printRankResults(Map<Rank, Integer> result) {
        printRankResult(Rank.FIFTH, result.get(Rank.FIFTH));
        printRankResult(Rank.FOURTH, result.get(Rank.FOURTH));
        printRankResult(Rank.THIRD, result.get(Rank.THIRD));
        printRankResult(Rank.SECOND, result.get(Rank.SECOND));
        printRankResult(Rank.FIRST, result.get(Rank.FIRST));
    }

    private void printRankResult(Rank rank, int count) {
        if (rank.isMatchBonus()) {
            printBonusRankResult(rank, count);
            return;
        }
        printNormalRankResult(rank, count);
    }

    private void printNormalRankResult(Rank rank, int count) {
        System.out.println(String.format(WINNING_RESULT_FORMAT,
                rank.getMatchCount(),
                formatMoney(rank.getPrizeMoney()),
                count));
    }

    private void printBonusRankResult(Rank rank, int count) {
        System.out.println(String.format(WINNING_RESULT_WITH_BONUS_FORMAT,
                rank.getMatchCount(),
                formatMoney(rank.getPrizeMoney()),
                count));
    }

    private String formatMoney(int money) {
        return String.format("%,d", money);
    }

    private void printProfitRate(double profitRate) {
        System.out.println(String.format(PROFIT_RATE_FORMAT, profitRate));
    }
}

