package de.dhbw.mh.rinne.ast;

import java.util.List;

import de.dhbw.mh.rinne.CodeLocation;

public class AstIfElseStmtNode extends AstStmtNode {

    final AstExpressionNode condition;
    final List<AstStmtNode> thenBlock;
    final List<AstStmtNode> elseBlock;

    public AstIfElseStmtNode(CodeLocation codeLocation, AstExpressionNode condition, List<AstStmtNode> thenBlock,
            List<AstStmtNode> elseBlock) {

        super(codeLocation);
        this.condition = condition;
        this.elseBlock = elseBlock;
        this.thenBlock = thenBlock;
        this.children.add(condition);
        this.children.addAll(thenBlock);
        this.children.addAll(elseBlock);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitIfElseStmt(this);
    }

    public List<AstStmtNode> thenBlock() {
        return thenBlock;
    }

    public List<AstStmtNode> elseBlock() {
        return elseBlock;
    }

    public AstExpressionNode condition() {
        return condition;
    }

}
