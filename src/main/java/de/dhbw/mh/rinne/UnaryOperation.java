package de.dhbw.mh.rinne;

import java.util.Arrays;

public enum UnaryOperation {

    NOT("!", true), POSITIVE("+", false), NEGATIVE("-", false);

    private final String symbol;
    private final boolean negation;

    private UnaryOperation(String symbol, boolean negation) {
        this.symbol = symbol;
        this.negation = negation;
    }

    public static UnaryOperation fromSymbol(String symbol) {
        return Arrays.stream(values()).filter(op -> op.symbol.equals(symbol)).findFirst().orElseThrow();
    }

    public boolean isNegation() {
        return negation;
    }

    public boolean isArithmetic() {
        return !negation;
    }

}
