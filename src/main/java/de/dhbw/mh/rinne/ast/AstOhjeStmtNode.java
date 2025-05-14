package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstOhjeStmtNode extends AstStmtNode {

    public AstOhjeStmtNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitOhjeStmt(this);
    }

}
