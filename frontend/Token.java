package frontend;

public class Token {
    private TokenType type;  // 词法单元的类型
    private String value;    // 词法单元的实际值
    private int lineNumber;  // 所在行号
    private int columnNumber; // 列号

    public Token(TokenType type, String value, int lineNumber, int columnNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    // 获取列号
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}
