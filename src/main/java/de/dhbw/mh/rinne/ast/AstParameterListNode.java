package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

import java.util.List;

public class AstParameterListNode extends AstNode {
    private final List<AstParameterNode> parameters;

    public AstParameterListNode(CodeLocation codeLocation, List<AstParameterNode> parameters) {
        super(codeLocation);
        this.parameters = parameters;
        this.children.addAll(parameters);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitParameterList(this);
    }

}
