package lotto;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.service.LottoResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    @DisplayName("당첨 결과를 정상적으로 계산한다.")
    @Test
    void 당첨_결과를_정상적으로_계산한다() {
        LottoResult lottoResult = new LottoResult();
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                new Lotto(List.of(1, 2, 3, 4, 8, 9))
        );
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        lottoResult.calculateResult(lottos, winningNumbers, bonusNumber);
        Map<Rank, Integer> result = lottoResult.getResult();

        assertThat(result.get(Rank.FIRST)).isEqualTo(1);
        assertThat(result.get(Rank.SECOND)).isEqualTo(1);
        assertThat(result.get(Rank.FOURTH)).isEqualTo(1);
    }

    @DisplayName("수익률을 정확하게 계산한다.")
    @Test
    void 수익률을_정확하게_계산한다() {
        LottoResult lottoResult = new LottoResult();
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 7, 8, 9))
        );
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;
        int purchaseAmount = 1000;

        lottoResult.calculateResult(lottos, winningNumbers, bonusNumber);
        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(500.0);
    }

    @DisplayName("당첨되지 않았을 때 수익률은 0이다.")
    @Test
    void 당첨되지_않았을_때_수익률은_0이다() {
        LottoResult lottoResult = new LottoResult();
        List<Lotto> lottos = List.of(
                new Lotto(List.of(7, 8, 9, 10, 11, 12))
        );
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 13;
        int purchaseAmount = 1000;

        lottoResult.calculateResult(lottos, winningNumbers, bonusNumber);
        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(0.0);
    }
}

