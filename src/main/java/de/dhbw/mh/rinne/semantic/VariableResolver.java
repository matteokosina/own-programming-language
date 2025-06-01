package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class VariableResolver extends AstSemanticVisitor<Void> {

    public Void visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        String variableName = node.getName();
        try {
            scopes.defineVariable(variableName, node);
        } catch (SemanticException ex) {
            reportSemanticError(node, ex.getMessage());
        }
        return visitChildren(node);
    }

    public Void visitVariableReference(AstVariableReferenceNode node) {
        String variableName = node.getName();
        var varDef = scopes.lookupVariable(variableName);
        if (varDef.isEmpty()) {
            reportSemanticError(node, "cannot find symbol '" + variableName + "'");
        } else {
            varDef.get().setUsed();
        }
        return visitChildren(node);
    }

}
