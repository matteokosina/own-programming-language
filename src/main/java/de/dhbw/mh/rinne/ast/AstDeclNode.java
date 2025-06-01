package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public abstract class AstDeclNode extends AstStmtNode {

    protected final String name;
    private RinneType type;

    public AstDeclNode(CodeLocation codeLocation, String name, RinneType type) {
        super(codeLocation);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public RinneType getType() {
        return type;
    }

}
