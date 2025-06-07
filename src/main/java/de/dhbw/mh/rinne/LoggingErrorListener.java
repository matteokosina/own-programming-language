package de.dhbw.mh.rinne;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import de.dhbw.mh.rinne.ast.AstNode;

public final class LoggingErrorListener extends BaseErrorListener implements RinneErrorListener {

    private final List<String> messages = new LinkedList<>();
    private final String prefix;

    public LoggingErrorListener() {
        this("");
    }

    public LoggingErrorListener(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void receive(AstNode node, String message) {
        messages.add(prefix + message);
    }

    public List<String> messages() {
        return messages;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
            String msg, RecognitionException e) {
        messages.add(prefix + line + ":" + charPositionInLine + ": " + msg);
    }

}
