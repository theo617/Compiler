package frontend;

import symbol.Symbol;

public class SemanticError implements Comparable<SemanticError> {
    private String message;
    private int line;
    private int column;
    private Symbol symbol;

    public SemanticError(String message, Symbol symbol) {
        this.message = message;
        if (symbol != null) {
            this.line = 0;    // 你可以根据需求设置行号和列号
            this.column = 0;  // 这里假设Symbol类没有行列信息
        }
    }

    // 如果需要根据Token获取行列信息，可以添加构造函数
    public SemanticError(String message, int line, int column) {
        this.message = message;
        this.line = line;
        this.column = column;
    }

    public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        if (line > 0 && column > 0) {
            return "SemanticError at line " + line + ", column " + column + ": " + message;
        } else {
            return "SemanticError: " + message;
        }
    }

    @Override
    public int compareTo(SemanticError other) {
        if (this.line != other.line) {
            return this.line - other.line;
        }
        return this.column - other.column;
    }
}
