package de.dhbw.mh.rinne.ast;

public class AstVisitor<T> {

    public T visit(AstNode node) {
        return node.accept(this);
    }

    public T visitChildren(AstNode node) {
        for (AstNode child : node.getChildren()) {
            child.accept(this);
        }
        return null;
    }

    public T visitAssignment(AstAssignmentNode node) {
        return visitChildren(node);
    }

    public T visitDruckeStmt(AstDruckeStmtNode node) {
        return visitChildren(node);
    }

    public T visitExpression(AstExpressionNode node) {
        return visitChildren(node);
    }

    public T visitExpressionStmt(AstExpressionStmtNode node) {
        return visitChildren(node);
    }

    public T visitFunctionCall(AstFunctionCallNode node) {
        return visitChildren(node);
    }

    public T visitFunctionDefinition(AstFunctionDefinitionNode node) {
        return visitChildren(node);
    }

    public T visitParameterList(AstParameterListNode node) {
        return visitChildren(node);
    }

    public T visitOhjeStmt(AstOhjeStmtNode node) {
        return visitChildren(node);
    }

    public T visitParameter(AstParameterNode node) {
        return visitChildren(node);
    }

    public T visitPostCheckLoop(AstPostCheckLoopNode node) {
        return visitChildren(node);
    }

    public T visitPreCheckLoop(AstPreCheckLoopNode node) {
        return visitChildren(node);
    }

    public T visitProgram(AstProgramNode node) {
        return visitChildren(node);
    }

    public T visitReturnStmt(AstReturnStmtNode node) {
        return visitChildren(node);
    }

    public T visitIfElseStmt(AstIfElseStmtNode node) {
        return visitChildren(node);
    }

    public T visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        return visitChildren(node);
    }

    public T visitVariableReference(AstVariableReferenceNode node) {
        return visitChildren(node);
    }

    public T visitScopedStatements(AstScopedStmtsNode node) {
        return visitChildren(node);
    }

    public T visitCast(AstCastNode node) {
        return visitChildren(node);
    }

}
