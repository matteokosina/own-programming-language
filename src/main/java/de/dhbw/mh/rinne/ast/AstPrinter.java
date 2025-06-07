package de.dhbw.mh.rinne.ast;

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
    public String visitChildren(AstNode node) {
        return node.getChildren().stream().map(child -> child.accept(this)).collect(Collectors.joining());
    }

    @Override
    public String visitProgram(AstProgramNode node) {
        enterNode();
        String temp = "Program(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarDecl(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarRef(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 1
    public String visitAssignment(AstAssignmentNode node) {
        enterNode();
        String temp = indentationFor(level) + "Assig(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 2
    @Override
    public String visitFunctionCall(AstFunctionCallNode node) {
        enterNode();
        String temp = indentationFor(level) + "FuncCall(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitExpressionStmt(AstExpressionStmtNode node) {
        return visitChildren(node);
    }

    // Team 3
    @Override
    public String visitFunctionDefinition(AstFunctionDefinitionNode node) {
        enterNode();
        String temp = indentationFor(level) + "FunctionDefinition(" + node.locationAsString() + ")\n"
                + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 4
    @Override
    public String visitIfElseStmt(AstIfElseStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "IfElseStmt(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 5
    public String visitPostCheckLoop(AstPostCheckLoopNode node) {
        enterNode();
        String temp = indentationFor(level) + "DoWhile(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 6
    public String visitPreCheckLoop(AstPreCheckLoopNode node) {
        enterNode();
        String temp = indentationFor(level) + "While(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 7
    public String visitDruckeStmt(AstDruckeStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "DruckeStmt(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 8
    @Override
    public String visitReturnStmt(AstReturnStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "Return(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitScopedStatements(AstScopedStmtsNode node) {
        enterNode();
        String temp = indentationFor(level) + "Stmts(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitCast(AstCastNode node) {
        enterNode();
        String temp = indentationFor(level) + "Cast(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitBinaryExpression(AstBinaryExpressionNode node) {
        enterNode();
        String temp = indentationFor(level) + "BinOp(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitLiteral(AstLiteralNode node) {
        enterNode();
        String temp = indentationFor(level) + "Literal(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    public String visitParameterList(AstParameterListNode node) {
        return visitChildren(node);
    }

    @Override
    public String visitParameter(AstParameterNode node) {
        enterNode();
        String temp = indentationFor(level) + "Param(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

}
