package de.dhbw.mh.rinne.codegen;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.mh.rinne.semantic.AstSemanticVisitor;

public abstract class BaseBytecodeGenerator extends AstSemanticVisitor<String> {

    private final Map<String, Integer> labelCounts = new HashMap<>();

    public String generateUniqueLabel(String baseLabel) {
        int count = labelCounts.getOrDefault(baseLabel, 0);
        labelCounts.put(baseLabel, count + 1);
        return baseLabel + "_" + count;
    }

    public int loadVariableIndex(String variableName) {
        var symbol = scopes.lookupVariable(variableName);
        if (symbol.isPresent()) {
            return symbol.get().getVariableIndex();
        }
        return Integer.MIN_VALUE;
    }

}
