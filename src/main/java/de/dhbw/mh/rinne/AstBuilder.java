package de.dhbw.mh.rinne;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import de.dhbw.mh.rinne.antlr.RinneBaseVisitor;
import de.dhbw.mh.rinne.antlr.RinneParser;
import de.dhbw.mh.rinne.ast.AstAssignmentNode;
import de.dhbw.mh.rinne.ast.AstBinaryExpressionNode;
import de.dhbw.mh.rinne.ast.AstDruckeStmtNode;
import de.dhbw.mh.rinne.ast.AstExpressionNode;
import de.dhbw.mh.rinne.ast.AstExpressionStmtNode;
import de.dhbw.mh.rinne.ast.AstFunctionCallNode;
import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstIfElseStmtNode;
import de.dhbw.mh.rinne.ast.AstLiteralNode;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstParameterListNode;
import de.dhbw.mh.rinne.ast.AstParameterNode;
import de.dhbw.mh.rinne.ast.AstPostCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstPreCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstReturnStmtNode;
import de.dhbw.mh.rinne.ast.AstScopedStmtsNode;
import de.dhbw.mh.rinne.ast.AstStmtNode;
import de.dhbw.mh.rinne.ast.AstUnaryExpressionNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class AstBuilder extends RinneBaseVisitor<AstNode> {

    private final List<RinneErrorListener> errorListeners;

    public AstBuilder() {
        this.errorListeners = new LinkedList<>();
    }

    public void addErrorListener(RinneErrorListener errorListener) {
        errorListeners.add(errorListener);
    }

    protected void reportError(String message) {
        errorListeners.forEach(listener -> listener.receive(null, message));
    }

    private CodeLocation getCodeLocation(ParserRuleContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        return new CodeLocation(line, column);
    }

    @Override
    public AstNode visitProgram(RinneParser.ProgramContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        List<AstStmtNode> statements = new ArrayList<>();
        for (var stmtCtx : ctx.statement()) {
            statements.add((AstStmtNode) visit(stmtCtx));
        }
        return new AstProgramNode(codeLoc, statements);
    }

    @Override
    public AstNode visitStatement(RinneParser.StatementContext ctx) {
        if (ctx.functionDefinition() != null)
            return visitFunctionDefinition(ctx.functionDefinition());
        if (ctx.variableDeclaration() != null)
            return visit(ctx.variableDeclaration());
        if (ctx.assignment() != null)
            return visitAssignment(ctx.assignment());
        if (ctx.ifStatement() != null)
            return visitIfStatement(ctx.ifStatement());
        if (ctx.whileStatement() != null)
            return visitWhileStatement(ctx.whileStatement());
        if (ctx.doWhileStatement() != null)
            return visitDoWhileStatement(ctx.doWhileStatement());
        if (ctx.funcCall() != null) {
            var functionCallNode = (AstFunctionCallNode) visitFuncCall(ctx.funcCall());
            return new AstExpressionStmtNode(functionCallNode);
        }
        if (ctx.returnStatement() != null)
            return visitReturnStatement(ctx.returnStatement());
        if (ctx.druckeStatement() != null)
            return visitDruckeStatement(ctx.druckeStatement());

        reportError(ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + ": unexpected statement type at '"
                + ctx.getText() + "'");
        return null;
    }

    @Override
    public AstNode visitTypedVariableDeclaration(RinneParser.TypedVariableDeclarationContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();
        var type = RinneType.from(ctx.type().getText());

        if (ctx.initialValue == null || ctx.initialValue.isEmpty()) {
            return new AstVariableDeclarationStmtNode(codeLoc, name, type, null);
        }
        AstExpressionNode init = (AstExpressionNode) visit(ctx.initialValue);
        return new AstVariableDeclarationStmtNode(codeLoc, name, type, init);
    }

    @Override
    public AstNode visitUntypedVariableDeclaration(RinneParser.UntypedVariableDeclarationContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();

        if (ctx.initialValue == null || ctx.initialValue.isEmpty()) {
            return new AstVariableDeclarationStmtNode(codeLoc, name, null, null);
        }
        AstExpressionNode init = (AstExpressionNode) visit(ctx.initialValue);
        return new AstVariableDeclarationStmtNode(codeLoc, name, null, init);
    }

    @Override
    public AstNode visitVariableReference(RinneParser.VariableReferenceContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();

        return new AstVariableReferenceNode(codeLoc, name);
    }

    @Override
    public AstNode visitLongLiteral(RinneParser.LongLiteralContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        return new AstLiteralNode(codeLoc, ctx.literal.getText(), RinneType.GANZZAHL);
    }

    @Override
    public AstNode visitDoubleLiteral(RinneParser.DoubleLiteralContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        return new AstLiteralNode(codeLoc, ctx.literal.getText(), RinneType.FLIEßZAHL);
    }

    @Override
    public AstNode visitStringLiteral(RinneParser.StringLiteralContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        return new AstLiteralNode(codeLoc, ctx.literal.getText(), RinneType.SCHNUR);
    }

    // Team 1
    @Override
    public AstNode visitAssignment(RinneParser.AssignmentContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();
        var value = (AstExpressionNode) visit(ctx.conditionalExpression());
        return new AstAssignmentNode(codeLoc, name, value);
    }

    // Team 2
    @Override
    public AstNode visitFuncCall(RinneParser.FuncCallContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String func = ctx.functionName.getText();
        List<AstExpressionNode> args = new ArrayList<AstExpressionNode>();
        ctx.actualParameters.forEach(e -> args.add((AstExpressionNode) visit(e)));

        return new AstFunctionCallNode(codeLoc, func, args);
    }

    // Team 3

    @Override
    public AstNode visitFunctionDefinition(RinneParser.FunctionDefinitionContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String functionName = ctx.functionName.getText();

        AstParameterListNode parameters = (AstParameterListNode) visit(ctx.formalParameters());

        List<AstStmtNode> body = new ArrayList<>();

        for (var bodyStatement : ctx.statement()) {
            AstStmtNode statement = (AstStmtNode) visit(bodyStatement);
            body.add(statement);
        }

        return new AstFunctionDefinitionNode(codeLoc, functionName, parameters, body);
    }

    @Override
    public AstNode visitFormalParameters(RinneParser.FormalParametersContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        List<AstParameterNode> parameters = new ArrayList<>();

        for (var parameter : ctx.formalParameter()) {
            parameters.add((AstParameterNode) visit(parameter));
        }

        return new AstParameterListNode(codeLoc, parameters);
    }

    @Override
    public AstNode visitFormalParameter(RinneParser.FormalParameterContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String name = ctx.parameterName.getText();
        String type = ctx.type().getText();

        return new AstParameterNode(codeLoc, name, type);
    }

    // Team 4
    @Override
    public AstNode visitIfStatement(RinneParser.IfStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        AstExpressionNode condition = (AstExpressionNode) visit(ctx.condition);
        List<AstStmtNode> thenBlock = new ArrayList<>();
        List<AstStmtNode> elseBlock = new ArrayList<>();

        for (var stmtsCtx : ctx.thenBlock) {
            thenBlock.add((AstStmtNode) visit(stmtsCtx));
        }

        for (var stmtCtx : ctx.elseBlock) {
            elseBlock.add((AstStmtNode) visit(stmtCtx));
        }

        AstIfElseStmtNode node = new AstIfElseStmtNode(codeLoc, condition, thenBlock, elseBlock);
        return node;
    }

    // Team 5
    public AstNode visitDoWhileStatement(RinneParser.DoWhileStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String condition = ctx.conditionalExpression().getText();

        List<AstStmtNode> body = new ArrayList<>();

        for (var bodyStatement : ctx.statement()) {
            AstStmtNode statement = (AstStmtNode) visit(bodyStatement);
            body.add(statement);
        }
        return new AstPostCheckLoopNode(codeLoc, condition, body);
    }

    // Team 6
    public AstNode visitWhileStatement(RinneParser.WhileStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        AstExpressionNode condition = (AstExpressionNode) visit(ctx.conditionalExpression());

        AstScopedStmtsNode scopedStmts = new AstScopedStmtsNode(codeLoc);
        for (var stmtCtx : ctx.statement()) {
            AstStmtNode stmtNode = (AstStmtNode) visit(stmtCtx);
            scopedStmts.add(stmtNode);
        }

        return new AstPreCheckLoopNode(codeLoc, condition, scopedStmts);
    }

    // Team 7
    @Override
    public AstNode visitDruckeStatement(RinneParser.DruckeStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        AstExpressionNode expression = (AstExpressionNode) visit(ctx.conditionalExpression());
        return new AstDruckeStmtNode(codeLoc, expression);
    }

    // Team 8
    @Override
    public AstNode visitReturnStatement(RinneParser.ReturnStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        var expr = (AstExpressionNode) visit(ctx.conditionalExpression());
        return new AstReturnStmtNode(codeLoc, expr);
    }

    @Override
    public AstNode visitConditionalExpression(RinneParser.ConditionalExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        return new AstBinaryExpressionNode(codeLoc, BinaryOperation.LOR, lhs, rhs);
    }

    @Override
    public AstNode visitConditionalAndExpression(RinneParser.ConditionalAndExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        return new AstBinaryExpressionNode(codeLoc, BinaryOperation.LAND, lhs, rhs);
    }

    @Override
    public AstNode visitEqualityExpression(RinneParser.EqualityExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        var operation = BinaryOperation.fromSymbol(ctx.operator.getText());
        assert operation.checksEquality();
        return new AstBinaryExpressionNode(codeLoc, operation, lhs, rhs);
    }

    @Override
    public AstNode visitRelationalExpression(RinneParser.RelationalExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        var operation = BinaryOperation.fromSymbol(ctx.operator.getText());
        assert operation.isComparison();
        return new AstBinaryExpressionNode(codeLoc, operation, lhs, rhs);
    }

    @Override
    public AstNode visitAdditiveExpression(RinneParser.AdditiveExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        var operation = BinaryOperation.fromSymbol(ctx.operator.getText());
        assert operation.isArithmetic();
        return new AstBinaryExpressionNode(codeLoc, operation, lhs, rhs);
    }

    @Override
    public AstNode visitMultiplicativeExpression(RinneParser.MultiplicativeExpressionContext ctx) {
        if (ctx.lhs == null) {
            return visit(ctx.rhs);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var lhs = (AstExpressionNode) visit(ctx.lhs);
        var rhs = (AstExpressionNode) visit(ctx.rhs);
        var operation = BinaryOperation.fromSymbol(ctx.operator.getText());
        assert operation.isArithmetic();
        return new AstBinaryExpressionNode(codeLoc, operation, lhs, rhs);
    }

    @Override
    public AstNode visitUnaryExpression(RinneParser.UnaryExpressionContext ctx) {
        if (ctx.operator == null) {
            return visit(ctx.expr);
        }
        CodeLocation codeLoc = getCodeLocation(ctx);
        var expr = (AstExpressionNode) visit(ctx.expr);
        var operation = UnaryOperation.fromSymbol(ctx.operator.getText());
        assert operation.isArithmetic();
        return new AstUnaryExpressionNode(codeLoc, operation, expr);
    }

    @Override
    public AstNode visitBooleanTrue(RinneParser.BooleanTrueContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        return new AstLiteralNode(codeLoc, ctx.WAHR().getText(), RinneType.WAHRHEITSWERT);
    }

    @Override
    public AstNode visitBooleanFalse(RinneParser.BooleanFalseContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        return new AstLiteralNode(codeLoc, ctx.FALSCH().getText(), RinneType.WAHRHEITSWERT);
    }

    @Override
    public AstNode visitParenthesizedExpression(RinneParser.ParenthesizedExpressionContext ctx) {
        return visit(ctx.conditionalExpression());
    }

    @Override
    public AstNode visitFunctionCall(RinneParser.FunctionCallContext ctx) {
        return visit(ctx.funcCall());
    }

}
