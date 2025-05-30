package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstDruckeStmtNode extends AstStmtNode {

    private final AstExpressionNode value;

    public AstDruckeStmtNode(CodeLocation codeLocation, AstExpressionNode value) {
        super(codeLocation);
        this.value = value;
        this.children.add(value);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitDruckeStmt(this);
    }

}
