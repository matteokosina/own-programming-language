package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.antlr.RinneParser.StatementContext;

import java.util.List;

public class AstIfElseStmtNode extends AstStmtNode {

    private AstStmtNode statement;
    private String condition;
    private List<AstStmtNode> elseBlock;

    public AstIfElseStmtNode(CodeLocation codeLocation, AstStmtNode statement, List<AstStmtNode> elseBlock, String condition) {

        super(codeLocation);
        this.statement = statement;
        this.elseBlock = elseBlock;
        this.condition = condition;
        
        this.children.add(statement);
        this.children.addAll(elseBlock);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitIfElseStmt(this);
    }
}