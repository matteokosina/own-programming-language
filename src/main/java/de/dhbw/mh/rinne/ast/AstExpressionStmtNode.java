package de.dhbw.mh.rinne.ast;

public class AstExpressionStmtNode extends AstStmtNode {

    public AstExpressionStmtNode(AstExpressionNode expr) {
        super(expr.getCodeLocation());
        this.children.add(expr);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitExpressionStmt(this);
    }

}
