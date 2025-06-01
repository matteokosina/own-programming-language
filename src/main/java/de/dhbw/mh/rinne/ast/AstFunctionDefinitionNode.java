package de.dhbw.mh.rinne.ast;

import java.util.List;

import de.dhbw.mh.rinne.CodeLocation;

public class AstFunctionDefinitionNode extends AstDeclNode {
    private final AstParameterListNode parameters;
    private final List<AstStmtNode> body;

    public AstFunctionDefinitionNode(CodeLocation codeLocation, String name, AstParameterListNode parameters,
            List<AstStmtNode> body) {
        super(codeLocation, name, null);
        this.parameters = parameters;
        this.body = body;
        this.children.addAll(body);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }

}
