package de.dhbw.mh.rinne.ast;

import java.util.List;

import de.dhbw.mh.rinne.CodeLocation;

public class AstProgramNode extends AstNode {

    public AstProgramNode(CodeLocation codeLocation, List<AstStmtNode> statements) {
        super(codeLocation);
        this.children.addAll(statements);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitProgram(this);
    }

}
