package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstParameterNode extends AstNode {
    private final String name;
    private final String type;

    public AstParameterNode(CodeLocation codeLocation, String name, String type) {
        super(codeLocation);
        this.name = name;
        this.type = type;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitParameter(this);
    }

}
