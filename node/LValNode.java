package node;

import frontend.Parser;
import frontend.SemanticError;

import java.io.IOException;
import symbol.*;

public class LValNode {
    private String ident;
    private ExpNode index;
    private Type type;

    public LValNode(String ident, ExpNode index) {
        this.ident = ident;
        this.index = index;
    }

    public String getIdent() {
        return ident;
    }


    public Type getType(Parser parser) {
        if (index != null) {
            // 数组的元素，（先默认为一维数组）
            Symbol symbol = parser.lookupSymbol(ident);
            if (symbol != null) {
                if (symbol.getType() == Symbol.SymbolType.IntArray || symbol.getType() == Symbol.SymbolType.CharArray) {
                    this.type = symbol.getType() == Symbol.SymbolType.IntArray ? Type.INT : Type.CHAR; // INTARRAY 或 CHARARRAY
                } else if(symbol.getType() == Symbol.SymbolType.ConstIntArray || symbol.getType() == Symbol.SymbolType.ConstCharArray){
                    this.type = symbol.getType() == Symbol.SymbolType.ConstIntArray ? Type.INT : Type.CHAR; // 常量数组转化为变量数组处理   
                }//System.out.println("LValNode: " + this.type + " ,Symbol: " + symbol.getType());
            } else {
                this.type = Type.UNKNOWN; 
            }

        } else {
            // 变量或数组
            Symbol symbol = parser.lookupSymbol(ident);
            if (symbol != null) {
                if (symbol.getType() == Symbol.SymbolType.Int || symbol.getType() == Symbol.SymbolType.Char) {
                    this.type = symbol.getType() == Symbol.SymbolType.Int ? Type.INT : Type.CHAR; // INT 或 CHAR
                } else if(symbol.getType() == Symbol.SymbolType.ConstInt || symbol.getType() == Symbol.SymbolType.ConstChar){
                    this.type = symbol.getType() == Symbol.SymbolType.ConstInt ? Type.INT : Type.CHAR;//常量转化为变量处理
                } else if (symbol.getType() == Symbol.SymbolType.IntArray || symbol.getType() == Symbol.SymbolType.CharArray) {
                    this.type = symbol.getType() == Symbol.SymbolType.IntArray ? Type.INT_ARRAY : Type.CHAR_ARRAY; // INTARRAY 或 CHARARRAY
                } else if(symbol.getType() == Symbol.SymbolType.ConstIntArray || symbol.getType() == Symbol.SymbolType.ConstCharArray){
                    this.type = symbol.getType() == Symbol.SymbolType.ConstIntArray ? Type.INT_ARRAY : Type.CHAR_ARRAY; // 常量数组转化为变量数组处理   
                }//System.out.println("LValNode: " + this.type + " ,Symbol: " + symbol.getType());
            } else {
                this.type = Type.UNKNOWN;
                
            }
        }
        //System.out.println("LValNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("IDENFR " + ident);
        if (index != null) {
            parser.write("LBRACK [");
            index.printNode(parser);
            parser.write("RBRACK ]");
            // 判断是否为数组
            Symbol symbol = parser.lookupSymbol(ident);
            if (symbol != null) {
                if (symbol.getType() == Symbol.SymbolType.IntArray || symbol.getType() == Symbol.SymbolType.CharArray) {
                    this.type = symbol.getType() == Symbol.SymbolType.IntArray ? Type.INT_ARRAY : Type.CHAR_ARRAY; // INT 或 CHAR
                } else {
                    this.type = Type.UNKNOWN;
                    
                }
            } else {
                this.type = Type.UNKNOWN;

            }
        } else {
            // 变量
            Symbol symbol = parser.lookupSymbol(ident);
            if (symbol != null) {
                if (symbol.getType() == Symbol.SymbolType.Int || symbol.getType() == Symbol.SymbolType.Char) {
                    this.type = symbol.getType() == Symbol.SymbolType.Int ? Type.INT : Type.CHAR; // INT 或 CHAR
                } else {
                    this.type = Type.UNKNOWN;
                    
                }
            } else {
                this.type = Type.UNKNOWN;
                
            }
        }
        parser.write("<LVal>");

    }
}
