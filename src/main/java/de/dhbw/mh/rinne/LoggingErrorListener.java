package de.dhbw.mh.rinne;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;

import de.dhbw.mh.rinne.ast.AstNode;

public final class LoggingErrorListener extends BaseErrorListener implements RinneErrorListener {

    private final List<String> messages = new LinkedList<>();

    @Override
    public void receive(AstNode node, String message) {
        messages.add(message);
    }

    public List<String> messages() {
        return messages;
    }

}
