package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstCastNode extends AstExpressionNode {

    public AstCastNode(CodeLocation codeLocation, AstExpressionNode expression) {
        super(codeLocation);
        this.children.add(expression);
    }

    public AstCastNode(AstExpressionNode expression) {
        this(expression.codeLocation, expression);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitCast(this);
    }

}
