package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

import java.util.HashMap;
import java.util.List;

public class AstFunctionDefinitionNode extends AstStmtNode {
    private final String name;
    private final AstParameterListNode parameters;
    private final List<AstStmtNode> body;

    public AstFunctionDefinitionNode(CodeLocation codeLocation, String name, AstParameterListNode parameters,
            List<AstStmtNode> body) {
        super(codeLocation);
        this.name = name;
        this.parameters = parameters;
        this.body = body;
        this.children.addAll(body);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }

}
