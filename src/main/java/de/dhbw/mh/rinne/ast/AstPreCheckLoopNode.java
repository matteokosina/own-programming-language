package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstPreCheckLoopNode extends AstStmtNode {

    public AstPreCheckLoopNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitPreCheckLoop(this);
    }

}
