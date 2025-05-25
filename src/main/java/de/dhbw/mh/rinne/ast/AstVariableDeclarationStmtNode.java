package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstVariableDeclarationStmtNode extends AstStmtNode {

    private final String name;
    private final String type;
    private final AstExpressionNode initializer;

    public AstVariableDeclarationStmtNode(CodeLocation codeLocation, String name, String type,
            AstExpressionNode initializer) {
        super(codeLocation);
        this.name = name;
        this.type = type;
        this.initializer = initializer;
        this.children.add(initializer);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitVariableDeclarationStmt(this);
    }

    public String getName() {
        return name;
    }

    public AstExpressionNode getInitializer() {
        return initializer;
    }

}
