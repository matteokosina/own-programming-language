package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ast.AstDeclNode;

public class SymbolTableEntry {

    private final AstDeclNode declNode;
    private boolean isUsed = false;

    public SymbolTableEntry(AstDeclNode declNode) {
        this.declNode = declNode;
    }

    public AstDeclNode getDeclNode() {
        return declNode;
    }

    public void setUsed() {
        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

}
