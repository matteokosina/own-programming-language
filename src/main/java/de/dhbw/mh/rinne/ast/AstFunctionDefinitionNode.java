package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstFunctionDefinitionNode extends AstStmtNode {

    public AstFunctionDefinitionNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }

}
