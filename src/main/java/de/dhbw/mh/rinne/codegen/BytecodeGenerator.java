package de.dhbw.mh.rinne.codegen;

import java.util.stream.Collectors;

import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;
import de.dhbw.mh.rinne.ast.AstVisitor;

public class BytecodeGenerator extends AstVisitor<String> {

    public void enterNode() {
    }

    public void exitNode() {
    }

    @Override
    public String visitProgram(AstProgramNode node) {
        enterNode();
        String bytecode = node.getChildren().stream().map(child -> child.accept(this)).collect(Collectors.joining());
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String bytecode = "";
        if (node.getInitializer() != null) {
            bytecode += node.getInitializer().accept(this);
            bytecode += "istore <" + node.getName() + ">\n";
        }
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String bytecode = "iload <" + node.getName() + ">\n";
        exitNode();
        return bytecode;
    }

}
