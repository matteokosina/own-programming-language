package de.dhbw.mh.rinne.codegen;

import java.util.List;

import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class BytecodeGenerator extends BaseBytecodeGenerator {

    public void enterNode() {
    }

    public void exitNode() {
    }

    @Override
    public String visitPost(AstProgramNode node, List<String> children) {
        enterNode();
        String bytecode = String.join("", children);
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String bytecode = "";
        if (node.getInitializer() != null) {
            bytecode += node.getInitializer().accept(this);
            bytecode += "istore " + loadVariableIndex(node.getName()) + "\n";
        }
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String bytecode = "iload " + loadVariableIndex(node.getName()) + "\n";
        exitNode();
        return bytecode;
    }

}
