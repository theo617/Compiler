package node;

import frontend.Token;
import frontend.Parser;
import java.io.IOException;

public class StringConstNode {
    private Token stringConst;

    public StringConstNode(Token stringConst) {
        this.stringConst = stringConst;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("STRCON " + stringConst.getValue());  // 输出字符串常量的值
        parser.write("<StringConst>");
    }

    public String getValue() {
        return stringConst.getValue();  // 获取字符串的实际值
    }

    public Token getStringConst() {
        return stringConst;
    }
}
