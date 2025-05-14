package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstFunctionCallNode extends AstExpressionNode {

    public AstFunctionCallNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }

}
