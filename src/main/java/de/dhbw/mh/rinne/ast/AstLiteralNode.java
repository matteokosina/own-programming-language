package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstLiteralNode extends AstExpressionNode {

    private final String lexeme;
    private Value value;

    public AstLiteralNode(CodeLocation codeLocation, String lexeme, RinneType type) {
        super(codeLocation);
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public static interface Value {
    }

    public static final class Wahrheitswert implements Value {
        public final boolean state;

        public Wahrheitswert(boolean state) {
            this.state = state;
        }
    }

    public static final class Ganzzahl implements Value {
        public final long number;

        public Ganzzahl(long number) {
            this.number = number;
        }
    }

    public static final class Fließzahl implements Value {
        public final double number;

        public Fließzahl(double number) {
            this.number = number;
        }
    }

    public static final class Schnur implements Value {
        public final String text;

        public Schnur(String text) {
            this.text = text;
        }
    }

}
