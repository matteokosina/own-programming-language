package de.dhbw.mh.rinne.semantic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import de.dhbw.mh.rinne.ast.AstDeclNode;
import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;

public class SymbolTable {

    private static final String PREFIX_VARIABLE_ID = "var:";
    private static final String PREFIX_FUNCTION_ID = "fn:";

    private final Deque<Scope> scopes = new ArrayDeque<>();

    public void defineVariable(String name, AstVariableDeclarationStmtNode node) {
        define(PREFIX_VARIABLE_ID + name, name, node);
    }

    public void defineFunction(String name, AstFunctionDefinitionNode node) {
        define(PREFIX_FUNCTION_ID + name, name, node);
    }

    public Optional<SymbolTableEntry> lookupVariable(String name) {
        return deepLookup(PREFIX_VARIABLE_ID + name);
    }

    public Optional<SymbolTableEntry> lookupFunction(String name) {
        return deepLookup(PREFIX_FUNCTION_ID + name);
    }

    public Scope pushNewScope() {
        Scope scope = new Scope();
        scopes.push(scope);
        return scope;
    }

    public void enterScope(Scope scope) {
        scopes.push(scope);
    }

    public void exitScope() {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("No scope to exit");
        }
        scopes.pop();
    }

    public Scope innermostScope() {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("No scope available");
        }
        return scopes.peek();
    }

    private Optional<SymbolTableEntry> deepLookup(String id) {
        for (Scope scope : scopes) {
            Optional<SymbolTableEntry> entry = scope.get(id);
            if (entry.isPresent()) {
                return entry;
            }
        }
        return Optional.empty();
    }

    private Optional<SymbolTableEntry> shallowLookup(String id) {
        return scopes.peek().get(id);
    }

    private void define(String id, String name, AstDeclNode node) {
        var temp = shallowLookup(id);
        if (shallowLookup(id).isPresent()) {
            throw new SemanticException(node,
                    "'" + name + "' already declared at " + temp.get().getDeclNode().locationAsString());
        }
        scopes.peek().define(id, node);
    }

}
