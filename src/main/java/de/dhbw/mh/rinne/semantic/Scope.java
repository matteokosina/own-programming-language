package de.dhbw.mh.rinne.semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import de.dhbw.mh.rinne.ast.AstDeclNode;

public class Scope {

    private final Map<String, SymbolTableEntry> symbols = new HashMap<>();

    /**
     * Checks whether a symbol with the given internal ID is defined in this scope.
     *
     * @param id
     *            the internal symbol ID (e.g., "var:x" or "fn:main")
     *
     * @return true if the symbol exists in this scope; false otherwise
     *
     * @throws NullPointerException
     *             if {@code id} is null
     */
    public boolean contains(String id) {
        return symbols.containsKey(Objects.requireNonNull(id));
    }

    /**
     * Retrieves the symbol entry associated with the given internal ID, if present.
     *
     * @param id
     *            the internal symbol ID (e.g., "var:x" or "fn:main")
     *
     * @return an Optional containing the symbol entry, or empty if not found
     *
     * @throws NullPointerException
     *             if {@code id} is null
     */
    public Optional<SymbolTableEntry> get(String id) {
        return Optional.ofNullable(symbols.get(Objects.requireNonNull(id)));
    }

    /**
     * Defines a new symbol in this scope. If a symbol with the same internal ID already exists in this scope, an
     * exception is thrown to prevent redeclaration.
     *
     * @param id
     *            the internal symbol ID (e.g., "var:x" or "fn:main")
     * @param node
     *            the AST declaration node associated with the symbol
     *
     * @throws IllegalStateException
     *             if the symbol ID is already defined in this scope
     * @throws NullPointerException
     *             if either argument is null
     */
    public void define(String id, AstDeclNode node) {
        Objects.requireNonNull(id, "Symbol ID must not be null");
        Objects.requireNonNull(node, "Declaration node must not be null");
        if (symbols.containsKey(id)) {
            throw new IllegalStateException("Symbol '" + id + "' already defined in this scope.");
        }
        symbols.put(id, new SymbolTableEntry(node));
    }

    /**
     * Performs the given action for each symbol entry in this scope.
     *
     * @param consumer
     *            the action to perform on each symbol
     *
     * @throws NullPointerException
     *             if the consumer is null
     */
    public void forEach(Consumer<SymbolTableEntry> consumer) {
        Objects.requireNonNull(consumer);
        symbols.values().forEach(consumer);
    }

}
