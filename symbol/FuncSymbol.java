package symbol;
import java.util.List;

public class FuncSymbol extends Symbol{
    private List<Symbol.SymbolType> symbolTypes;

    public FuncSymbol(String name, SymbolType type, int scopeNum, List<Symbol.SymbolType> symbolTypes) {
        super(name, type, scopeNum);
        this.symbolTypes = symbolTypes;
    }

    public List<Symbol.SymbolType> getSymbolTypes() {
        return symbolTypes;
    }

    public int getParamCount() {
        return symbolTypes.size();
    }

    @Override
    public String toString() {
        return "FuncSymbol{" +
                "name='" + getName() + '\'' +
                ", type=" + getType() +
                ", scopeNum=" + getScopeNum() +
                ", symbolTypes=" + symbolTypes +
                '}';
    }

}
