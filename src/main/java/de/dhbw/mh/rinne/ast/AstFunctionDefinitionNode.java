package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstFunctionDefinitionNode extends AstDeclNode {

    public AstFunctionDefinitionNode(CodeLocation codeLocation, String name) {
        super(codeLocation, name, null);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }

}
