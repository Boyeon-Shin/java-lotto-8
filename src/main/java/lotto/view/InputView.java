package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    private static final String PURCHASE_AMOUNT_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String WINNING_NUMBERS_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String BONUS_NUMBER_MESSAGE = "보너스 번호를 입력해 주세요.";
    private static final String DELIMITER = ",";
    private static final int LOTTO_PRICE = 1000;

    public int readPurchaseAmount() {
        System.out.println(PURCHASE_AMOUNT_MESSAGE);
        String input = Console.readLine();
        return parsePurchaseAmount(input);
    }

    private int parsePurchaseAmount(String input) {
        int amount = parsePositiveInteger(input);
        validatePurchaseAmount(amount);
        return amount;
    }

    private void validatePurchaseAmount(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    public List<Integer> readWinningNumbers() {
        System.out.println(WINNING_NUMBERS_MESSAGE);
        String input = Console.readLine();
        return parseWinningNumbers(input);
    }

    private List<Integer> parseWinningNumbers(String input) {
        String[] tokens = input.split(DELIMITER);
        return parseNumbers(tokens);
    }

    private List<Integer> parseNumbers(String[] tokens) {
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            numbers.add(parsePositiveInteger(token.trim()));
        }
        return numbers;
    }

    public int readBonusNumber() {
        System.out.println(BONUS_NUMBER_MESSAGE);
        String input = Console.readLine();
        return parsePositiveInteger(input);
    }

    private int parsePositiveInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }
}

