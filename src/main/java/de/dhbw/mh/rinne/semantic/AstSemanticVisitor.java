package de.dhbw.mh.rinne.semantic;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import de.dhbw.mh.rinne.RinneErrorListener;
import de.dhbw.mh.rinne.ast.AstAssignmentNode;
import de.dhbw.mh.rinne.ast.AstDruckeStmtNode;
import de.dhbw.mh.rinne.ast.AstExpressionNode;
import de.dhbw.mh.rinne.ast.AstExpressionStmtNode;
import de.dhbw.mh.rinne.ast.AstFunctionCallNode;
import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstIfElseStmtNode;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstOhjeStmtNode;
import de.dhbw.mh.rinne.ast.AstParameterNode;
import de.dhbw.mh.rinne.ast.AstPostCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstPreCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstReturnStmtNode;
import de.dhbw.mh.rinne.ast.AstScopedStmtsNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;
import de.dhbw.mh.rinne.ast.AstVisitor;

public abstract class AstSemanticVisitor<T> extends AstVisitor<T> {

    private final List<RinneErrorListener> errorListeners;

    protected SymbolTable scopes = new SymbolTable();

    protected AstSemanticVisitor() {
        this.errorListeners = new LinkedList<>();
    }

    public void addErrorListener(RinneErrorListener errorListener) {
        errorListeners.add(errorListener);
    }

    protected void reportSemanticError(AstNode node, String message) {
        errorListeners.forEach(listener -> listener.receive(node, node.locationAsString() + ": " + message));
    }

    protected T withScope(Scope scope, Supplier<T> block) {
        scopes.enterScope(scope);
        try {
            return block.get();
        } finally {
            beforeScopeExit();
            scopes.exitScope();
        }
    }

    public T visit(AstNode node) {
        return node.accept(this);
    }

    public T visitChildren(AstNode node) {
        // TODO: Some child nodes may be null due to incomplete AST construction in AstBuilder.
        // Once all node types are handled and children are always non-null, this check should be removed.
        for (AstNode child : node.getChildren()) {
            if (child == null) {
                continue;
            }
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
        return withScope(node.getScope(), () -> {
            List<T> results = new LinkedList<>();
            for (AstNode child : node.getChildren()) {
                if (child == null) {
                    continue;
                }
                results.add(child.accept(this));
            }
            return visitPost(node, results);
        });
    }

    public T visitPost(AstProgramNode node, List<T> children) {
        return null;
    }

    public T visitReturnStmt(AstReturnStmtNode node) {
        return visitChildren(node);
    }

    public T visitIfElseStmt(AstIfElseStmtNode node) {
        return visitChildren(node);
    }

    public T visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        T init = null;
        if (node.getInitializer() != null) {
            init = node.getInitializer().accept(this);
        }
        return visitPost(node, init);
    }

    public T visitVariableReference(AstVariableReferenceNode node) {
        return visitChildren(node);
    }

    public T visitScopedStatements(AstScopedStmtsNode node) {
        scopes.enterScope(node.getScope());
        T temp = visitChildren(node);
        scopes.exitScope();
        return temp;
    }

    public void beforeScopeExit() {
    }

    public T visitPost(AstVariableDeclarationStmtNode node, T init) {
        return init;
    }

}
