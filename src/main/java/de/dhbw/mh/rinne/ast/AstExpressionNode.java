package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public abstract class AstExpressionNode extends AstNode {

    public AstExpressionNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

}
