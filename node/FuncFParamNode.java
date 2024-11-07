package node;

import frontend.Parser;
import java.io.IOException;
import java.util.Objects;
import symbol.*;

public class FuncFParamNode {
    private String paramType;  // 参数类型, 比如 'int', 'char'
    private String paramName;  // 参数名
    private boolean isArray;   // 参数是否是数组
    private Symbol.SymbolType symbolType;

    public FuncFParamNode(String paramType, String paramName, boolean isArray, Symbol.SymbolType symbolType) {
        this.paramType = paramType;
        this.paramName = paramName;
        this.isArray = isArray;
        this.symbolType = symbolType;
    }

    public Symbol.SymbolType getSymbolType() {
        return symbolType;
    }

    public void printNode(Parser parser) throws IOException {
        if(Objects.equals(paramType, "int")){
            parser.write("INTTK int");
        } else if(Objects.equals(paramType, "char")){
            parser.write("CHARTK char");
        }
        parser.write("IDENFR " + paramName);
        if (isArray) {
            parser.write("LBRACK [");
            parser.write("RBRACK ]");
        }
        parser.write("<FuncFParam>");
    }
}
