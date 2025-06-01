package de.dhbw.mh.rinne;

import de.dhbw.mh.rinne.ast.AstNode;

public interface RinneErrorListener {

    public void receive(AstNode node, String message);

}
