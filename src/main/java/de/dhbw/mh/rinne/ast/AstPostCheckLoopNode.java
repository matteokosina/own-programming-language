package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstPostCheckLoopNode extends AstStmtNode {

    public AstPostCheckLoopNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitPostCheckLoop(this);
    }

}
