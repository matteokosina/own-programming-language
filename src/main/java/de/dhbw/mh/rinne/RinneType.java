package de.dhbw.mh.rinne;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public enum RinneType {

    GANZZAHL("Ganzzahl", true, 2), FLIEßZAHL("Fließzahl", true, 2), SCHNUR("Schnur", false, 1),
    WAHRHEITSWERT("Wahrheitswert", false, 1);

    private final String keyword;
    private final boolean numeric;
    private final int sizeOnOperandStack;

    private static final Map<String, RinneType> KEYWORD_MAP;

    static {
        Map<String, RinneType> map = new HashMap<>();
        for (RinneType type : values()) {
            map.put(type.keyword, type);
        }
        KEYWORD_MAP = Collections.unmodifiableMap(map);
    }

    private RinneType(String keyword, boolean numeric, int sizeOnOperandStack) {
        this.keyword = Objects.requireNonNull(keyword);
        this.numeric = numeric;
        this.sizeOnOperandStack = sizeOnOperandStack;
    }

    /**
     * Returns the RinneType matching the given lexeme.
     *
     * @param lexeme
     *            the lexeme of the type (case-sensitive)
     *
     * @return the corresponding RinneType
     *
     * @throws NullPointerException
     *             if lexeme is null
     * @throws IllegalArgumentException
     *             if no matching type is found
     */
    public static RinneType from(String lexeme) {
        Objects.requireNonNull(lexeme, "lexeme must not be null");
        RinneType type = KEYWORD_MAP.get(lexeme);
        if (type == null) {
            throw new IllegalArgumentException("Unknown RinneType: " + lexeme);
        }
        return type;
    }

    @Override
    public String toString() {
        return keyword;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public boolean isBoolean() {
        return this == WAHRHEITSWERT;
    }

    /**
     * Returns the type which covers the value range of both input types, if any.
     *
     * @param lhs
     *            left-hand side type
     * @param rhs
     *            right-hand side type
     *
     * @return optional covering type, or empty if none exists
     */
    public static Optional<RinneType> covering(RinneType lhs, RinneType rhs) {
        if (rhs == lhs) {
            return Optional.of(rhs);
        }
        if (lhs == GANZZAHL && rhs == FLIEßZAHL) {
            return Optional.of(FLIEßZAHL);
        }
        if (lhs == FLIEßZAHL && rhs == GANZZAHL) {
            return Optional.of(FLIEßZAHL);
        }
        return Optional.empty();
    }

    public int sizeOnOperandStack() {
        return sizeOnOperandStack;
    }

}
