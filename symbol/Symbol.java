package symbol;

public class Symbol {
    private String name;
    private SymbolType type;
    private int scopeNum;

    public Symbol(String name, SymbolType type, int scopeNum) {
        this.name = name;
        this.type = type;
        this.scopeNum = scopeNum;
    }

    public String getName() {
        return name;
    }

    public SymbolType getType() {
        return type;
    }

    public int getScopeNum() {
        return scopeNum;
    }


    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", scopeNum=" + scopeNum +
                '}';
    }

    // 定义符号类型枚举
    public enum SymbolType {
        ConstChar, Char,
        VoidFunc, CharFunc, IntFunc,
        ConstInt, Int,
        ConstCharArray, CharArray,
        ConstIntArray, IntArray
    }
}
