package de.dhbw.mh.rinne;

import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.semantic.SemanticException;

/**
 * An error listener that fails fast by throwing a SemanticException as soon as an error is reported.
 */
public final class ThrowingErrorListener implements RinneErrorListener {

    @Override
    public void receive(AstNode node, String message) {
        throw new SemanticException(node, message);
    }

}
