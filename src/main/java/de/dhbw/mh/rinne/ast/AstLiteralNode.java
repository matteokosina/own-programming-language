package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstLiteralNode extends AstExpressionNode {

    private final String lexeme;

    public AstLiteralNode(CodeLocation codeLocation, String lexeme, RinneType type) {
        super(codeLocation);
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }

}
