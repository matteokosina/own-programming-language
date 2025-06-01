package de.dhbw.mh.rinne;

public enum UnaryOperation {

    NOT(true);

    private final boolean negation;

    private UnaryOperation(boolean negation) {
        this.negation = negation;
    }

    public boolean isNegation() {
        return negation;
    }

}
