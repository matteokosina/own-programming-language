package de.dhbw.mh.rinne.codegen;

import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;

public class SimpleVariableAllocator extends BaseVariableAllocator {

    private int nextIndex = 0;

    @Override
    public Void visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        var symbol = scopes.lookupVariable(node.getName());
        if (symbol.isPresent()) {
            symbol.get().setVariableIndex(nextIndex);
            // TODO: We need type inference for this to work correctly.
            // nextIndex += node.getType().sizeOnOperandStack();
            nextIndex += 1;
        }
        return null;
    }

}
