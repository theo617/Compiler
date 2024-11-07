package symbol;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import frontend.SemanticError;

public class SymbolTable {
    private Map<String, Symbol> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void insert(Symbol symbol, List<SemanticError> semanticErrors) {
        if (contains(symbol.getName())) {
            semanticErrors.add(new SemanticError("重复定义: " + symbol.getName(), symbol));
        } else {
            table.put(symbol.getName(), symbol);
        }
    }

    public Symbol lookup(String name, List<SemanticError> semanticErrors) {
        if (!contains(name)) {
            semanticErrors.add(new SemanticError("未定义的标识符: " + name, null));
            return null;
        }
        return table.get(name);
    }

    public boolean contains(String name) {
        return table.containsKey(name);
    }

    public Map<String, Symbol> getTable() {
        return table;
    }
}