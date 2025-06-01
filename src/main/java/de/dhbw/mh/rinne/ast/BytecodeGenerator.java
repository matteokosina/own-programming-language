package de.dhbw.mh.rinne.ast;

import java.util.Objects;
import java.util.stream.Collectors;

//TODO: Move this class to a more appropriate package once the visit methods in AstVisitor are made public.
public class BytecodeGenerator extends AstVisitor<String> {

    public void enterNode() {
    }

    public void exitNode() {
    }

    @Override
    public String visitProgram(AstProgramNode node) {
        // TODO: Some child nodes may be null due to incomplete AST construction in AstBuilder.
        // Once all node types are handled and children are always non-null, this check should be removed.
        enterNode();
        String bytecode = node.getChildren().stream().filter(Objects::nonNull).map(child -> child.accept(this))
                .collect(Collectors.joining());
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
