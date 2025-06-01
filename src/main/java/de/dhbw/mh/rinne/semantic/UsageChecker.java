package de.dhbw.mh.rinne.semantic;

public class UsageChecker extends AstSemanticVisitor<Void> {

    public void beforeScopeExit() {
        scopes.innermostScope().forEach(symbol -> {
            if (!symbol.isUsed()) {
                reportSemanticError(symbol.getDeclNode(), "'" + symbol.getDeclNode().getName() + "' is never used");
            }
        });
    }

}
