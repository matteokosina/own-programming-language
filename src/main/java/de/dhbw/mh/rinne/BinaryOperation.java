package de.dhbw.mh.rinne;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public enum BinaryOperation {

    ADD("+", Kind.ARITHMETIC, Kind.CONCATENATION), SUBTRACT("-", Kind.ARITHMETIC), MULTIPLY("*", Kind.ARITHMETIC),
    DIVIDE("/", Kind.ARITHMETIC), EQUAL("==", Kind.EQUALITY), NOT_EQUAL("!=", Kind.EQUALITY),
    LESS_THAN("<", Kind.COMPARISON), GREATER_THAN(">", Kind.COMPARISON), LESS_EQUAL("<=", Kind.COMPARISON),
    GREATER_EQUAL(">=", Kind.COMPARISON), ASSIGN(":=", Kind.ASSIGNMENT), LAND("&&", Kind.LOGICAL),
    LOR("||", Kind.LOGICAL);

    private static final Map<String, BinaryOperation> SYMBOL_MAP;

    static {
        Map<String, BinaryOperation> map = new HashMap<>();
        for (var binOp : values()) {
            map.put(binOp.symbol, binOp);
        }
        SYMBOL_MAP = Collections.unmodifiableMap(map);
    }

    private final String symbol;
    private final Set<Kind> kinds;

    private BinaryOperation(String symbol, Kind... kinds) {
        this.symbol = symbol;
        this.kinds = EnumSet.noneOf(Kind.class);
        for (Kind kind : kinds) {
            this.kinds.add(kind);
        }
    }

    public static BinaryOperation fromSymbol(String symbol) {
        Objects.requireNonNull(symbol, "lexeme must not be null");
        var type = SYMBOL_MAP.get(symbol);
        if (type == null) {
            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }
        return type;
    }

    public boolean isArithmetic() {
        return kinds.contains(Kind.ARITHMETIC);
    }

    public boolean isConcatenation() {
        return kinds.contains(Kind.CONCATENATION);
    }

    public boolean checksEquality() {
        return kinds.contains(Kind.EQUALITY);
    }

    public boolean isComparison() {
        return kinds.contains(Kind.COMPARISON);
    }

    public boolean isAssignment() {
        return kinds.contains(Kind.ASSIGNMENT);
    }

    public boolean isLogical() {
        return kinds.contains(Kind.LOGICAL);
    }

    private enum Kind {
        ARITHMETIC, CONCATENATION, EQUALITY, COMPARISON, ASSIGNMENT, LOGICAL
    }

}
