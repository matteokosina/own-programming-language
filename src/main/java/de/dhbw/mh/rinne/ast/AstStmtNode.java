package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public abstract class AstStmtNode extends AstNode {

    public AstStmtNode(CodeLocation codeLocation) {
        super(codeLocation);
    }
}
