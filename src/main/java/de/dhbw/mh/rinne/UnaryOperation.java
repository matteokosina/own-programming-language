package de.dhbw.mh.rinne;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum UnaryOperation {

    NOT("!", true), POSITIVE("+", false), NEGATIVE("-", false);

    private static final Map<String, UnaryOperation> SYMBOL_MAP;

    static {
        Map<String, UnaryOperation> map = new HashMap<>();
        for (var op : values()) {
            map.put(op.symbol, op);
        }
        SYMBOL_MAP = Collections.unmodifiableMap(map);
    }

    private final String symbol;
    private final boolean negation;

    private UnaryOperation(String symbol, boolean negation) {
        this.symbol = symbol;
        this.negation = negation;
    }

    public static UnaryOperation fromSymbol(String symbol) {
        Objects.requireNonNull(symbol, "lexeme must not be null");
        var type = SYMBOL_MAP.get(symbol);
        if (type == null) {
            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }
        return type;
    }

    public boolean isNegation() {
        return negation;
    }

    public boolean isArithmetic() {
        return !negation;
    }

}
