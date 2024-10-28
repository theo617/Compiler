package error;


public class Error {
    private int lineNumber;   // 错误所在行号
    private int columnNumber; // 错误所在列号
    private String errorCode; // 错误类别码

    // 构造函数
    public Error(int lineNumber, int columnNumber, String errorCode) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.errorCode = errorCode;
    }

    // 获取错误行号
    public int getLineNumber() {
        return lineNumber;
    }

    // 获取错误列号
    public int getColumnNumber() {
        return columnNumber;
    }

    // 获取错误类别码
    public String getErrorCode() {
        return errorCode;
    }

    // 返回错误信息的字符串表示形式
    @Override
    public String toString() {
        return lineNumber + " " + errorCode;
    }
}

