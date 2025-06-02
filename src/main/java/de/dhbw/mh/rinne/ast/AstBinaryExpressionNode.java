package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.CodeLocation;

public class AstBinaryExpressionNode extends AstExpressionNode {

    private final BinaryOperation operator;
    private final AstExpressionNode lhs, rhs;

    public AstBinaryExpressionNode(CodeLocation codeLocation, BinaryOperation operator, AstExpressionNode lhs,
            AstExpressionNode rhs) {
        super(codeLocation);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
        this.children.add(lhs);
        this.children.add(rhs);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitBinaryExpression(this);
    }

}
