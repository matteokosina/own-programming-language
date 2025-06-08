package de.dhbw.mh.rinne.codegen;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.ast.AstBinaryExpressionNode;
import de.dhbw.mh.rinne.ast.AstIfElseStmtNode;
import de.dhbw.mh.rinne.ast.AstPostCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstPreCheckLoopNode;
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

    @Override
    public String visitBinaryExpression(AstBinaryExpressionNode node) {
        String lhs = node.lhs().accept(this);
        String rhs = node.rhs().accept(this);
        node.lhs().getType();
        return visitPost(node, node.operation(), node.lhs().getType(), lhs, rhs);
    }

    @Override
    public String visitIfElseStmt(AstIfElseStmtNode node) {
        String cond = node.condition().accept(this);
        String thenBlock = "";
        for (var stmt : node.thenBlock()) {
            thenBlock += stmt.accept(this);
        }
        String elseBlock = "";
        for (var stmt : node.elseBlock()) {
            elseBlock += stmt.accept(this);
        }
        return visitPost(node, cond, thenBlock, elseBlock);
    }

    @Override
    public String visitPreCheckLoop(AstPreCheckLoopNode node) {
        String cond = node.condition().accept(this);
        String body = node.body().accept(this);
        return visitPost(node, cond, body);
    }

    @Override
    public String visitPostCheckLoop(AstPostCheckLoopNode node) {
        String cond = node.condition().accept(this);
        String body = "";
        for (var stmt : node.body()) {
            body += stmt.accept(this);
        }
        return visitPost(node, cond, body);
    }

    public String visitPost(AstBinaryExpressionNode node, BinaryOperation op, RinneType type, String lhs, String rhs) {
        return "";
    }

    public String visitPost(AstIfElseStmtNode node, String condition, String thenBlock, String elseBlock) {
        return "";
    }

    public String visitPost(AstPreCheckLoopNode node, String condition, String body) {
        return "";
    }

    public String visitPost(AstPostCheckLoopNode node, String condition, String body) {
        return "";
    }

}
