package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstCastNode extends AstExpressionNode {

    public AstCastNode(CodeLocation codeLocation, AstExpressionNode expression, RinneType targetType) {
        super(codeLocation);
        this.children.add(expression);
        this.type = targetType;
    }

    public AstCastNode(AstExpressionNode expression, RinneType targetType) {
        this(expression.codeLocation, expression, targetType);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitCast(this);
    }

}
