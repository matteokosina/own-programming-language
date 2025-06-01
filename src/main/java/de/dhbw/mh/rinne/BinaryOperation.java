package de.dhbw.mh.rinne;

import java.util.EnumSet;
import java.util.Set;

public enum BinaryOperation {

    ADD(Kind.ARITHMETIC, Kind.CONCATENATION), SUBTRACT(Kind.ARITHMETIC), MULTIPLY(Kind.ARITHMETIC),
    DIVIDE(Kind.ARITHMETIC), EQUAL(Kind.EQUALITY), NOT_EQUAL(Kind.EQUALITY), LESS_THAN(Kind.COMPARISON),
    GREATER_THAN(Kind.COMPARISON), LESS_EQUAL(Kind.COMPARISON), GREATER_EQUAL(Kind.COMPARISON), ASSIGN(Kind.ASSIGNMENT);

    private final Set<Kind> kinds;

    private BinaryOperation(Kind... kinds) {
        this.kinds = EnumSet.noneOf(Kind.class);
        for (Kind kind : kinds) {
            this.kinds.add(kind);
        }
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

    private enum Kind {
        ARITHMETIC, CONCATENATION, EQUALITY, COMPARISON, ASSIGNMENT
    }

}
