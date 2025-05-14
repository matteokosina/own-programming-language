package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstReturnStmtNode extends AstStmtNode {

    public AstReturnStmtNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitReturnStmt(this);
    }

}
