package lotto;

import lotto.domain.Lotto;
import lotto.service.LottoMachine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest {
    @DisplayName("구입 금액에 따라 로또를 구매한다.")
    @Test
    void 구입_금액에_따라_로또를_구매한다() {
        LottoMachine lottoMachine = new LottoMachine();
        int amount = 3000;

        List<Lotto> lottos = lottoMachine.purchaseLottos(amount);

        assertThat(lottos).hasSize(3);
    }

    @DisplayName("생성된 로또는 6개의 숫자를 가진다.")
    @Test
    void 생성된_로또는_6개의_숫자를_가진다() {
        LottoMachine lottoMachine = new LottoMachine();
        int amount = 1000;

        List<Lotto> lottos = lottoMachine.purchaseLottos(amount);

        assertThat(lottos.get(0).getNumbers()).hasSize(6);
    }
}

