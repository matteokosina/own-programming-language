package de.dhbw.mh.rinne.ast;

import java.util.Objects;
import java.util.stream.Collectors;

public class AstPrinter extends AstVisitor<String> {

    private static final String INDENTATION_PER_LEVEL = " ";

    private int level = 0;

    private String indentationFor(int level) {
        return level <= 1 ? "" : INDENTATION_PER_LEVEL + indentationFor(level - 1);
    }

    public void enterNode() {
        ++level;
    }

    public void exitNode() {
        --level;
    }

    @Override
    String visitChildren(AstNode node) {
        // TODO: Some child nodes may be null due to incomplete AST construction in AstBuilder.
        // Once all node types are handled and children are always non-null, this check should be removed.
        return node.getChildren().stream().filter(Objects::nonNull).map(child -> child.accept(this))
                .collect(Collectors.joining());
    }

    @Override
    String visitProgram(AstProgramNode node) {
        enterNode();
        String temp = "Program(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarDecl(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarRef(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    // Team 1
    String visitAssignment(AstAssignmentNode node) {
        enterNode();
        String temp = indentationFor(level) + "Assig(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    // Team 2
    @Override
    String visitFunctionCall(AstFunctionCallNode node) {
        enterNode();
        String temp = indentationFor(level) + "FuncCall(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    @Override
    String visitExpressionStmt(AstExpressionStmtNode node) {
        return visitChildren(node);
    }

    // Team 3

    // Team 4

    // Team 5

    // Team 6
    String visitPreCheckLoop(AstPreCheckLoopNode node) {
        enterNode();
        String temp = indentationFor(level) + "While(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 7
    String visitDruckeStmt(AstDruckeStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "DruckeStmt(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    // Team 8
    @Override
    String visitReturnStmt(AstReturnStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "Return(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    @Override
    String visitScopedStatements(AstScopedStmtsNode node) {
        enterNode();
        String temp = indentationFor(level) + "Stmts(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    String visitCast(AstCastNode node) {
        enterNode();
        String temp = indentationFor(level) + "Cast(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

}
