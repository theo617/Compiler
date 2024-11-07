package symbol;

public class SymbolRecord {
    private int scopeNum;
    private Symbol symbol;
    private int declarationOrder;

    private static int globalOrder = 0;

    public SymbolRecord(int scopeNum, Symbol symbol) {
        this.scopeNum = scopeNum;
        this.symbol = symbol;
        this.declarationOrder = globalOrder++;
    }

    public int getScopeNum() {
        return scopeNum;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getDeclarationOrder() {
        return declarationOrder;
    }
}
