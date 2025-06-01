package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstVariableReferenceNode extends AstExpressionNode {

    private final String name;

    public AstVariableReferenceNode(CodeLocation codeLocation, String name) {
        super(codeLocation);
        this.name = name;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitVariableReference(this);
    }

    public String getName() {
        return name;
    }

}
