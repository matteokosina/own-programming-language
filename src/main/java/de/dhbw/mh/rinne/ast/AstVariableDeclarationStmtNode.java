package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstVariableDeclarationStmtNode extends AstDeclNode {

    private AstExpressionNode initializer;

    public AstVariableDeclarationStmtNode(CodeLocation codeLocation, String name, RinneType type,
            AstExpressionNode initializer) {
        super(codeLocation, name, type);
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

    public void castInitializer(RinneType type) {
        initializer = new AstCastNode(initializer);
    }

}
