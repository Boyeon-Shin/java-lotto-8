package lotto.controller;

import lotto.domain.Lotto;
import lotto.service.LottoMachine;
import lotto.service.LottoResult;
import lotto.view.InputView;
import lotto.view.OutputView;
import java.util.List;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        int purchaseAmount = readPurchaseAmountWithRetry();
        List<Lotto> lottos = purchaseLottos(purchaseAmount);

        List<Integer> winningNumbers = readWinningNumbersWithRetry();
        int bonusNumber = readBonusNumberWithRetry(winningNumbers);

        processResult(lottos, winningNumbers, bonusNumber, purchaseAmount);
    }

    private int readPurchaseAmountWithRetry() {
        while (true) {
            try {
                return inputView.readPurchaseAmount();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Lotto> purchaseLottos(int amount) {
        List<Lotto> lottos = lottoMachine.purchaseLottos(amount);
        outputView.printPurchaseCount(lottos.size());
        outputView.printLottos(lottos);
        return lottos;
    }

    private List<Integer> readWinningNumbersWithRetry() {
        while (true) {
            try {
                List<Integer> numbers = inputView.readWinningNumbers();
                new Lotto(numbers);
                return numbers;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readBonusNumberWithRetry(List<Integer> winningNumbers) {
        while (true) {
            try {
                int bonusNumber = inputView.readBonusNumber();
                validateBonusNumber(winningNumbers, bonusNumber);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateBonusNumber(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private void processResult(List<Lotto> lottos, List<Integer> winningNumbers, int bonusNumber, int purchaseAmount) {
        LottoResult lottoResult = new LottoResult();
        lottoResult.calculateResult(lottos, winningNumbers, bonusNumber);

        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);
        outputView.printWinningStatistics(lottoResult.getResult(), profitRate);
    }
}

