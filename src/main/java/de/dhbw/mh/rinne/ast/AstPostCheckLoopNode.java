package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

import java.util.List;

public class AstPostCheckLoopNode extends AstStmtNode {
    private final String condition;
    private final List<AstStmtNode> body;

    public AstPostCheckLoopNode(CodeLocation codeLocation, String condition, List<AstStmtNode> body) {
        super(codeLocation);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitPostCheckLoop(this);
    }

}
