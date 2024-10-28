package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class InitValNode {
    private ExpNode expression;  // 表达式
    private List<ExpNode> initValues;  // 如果是初始化列表，用表达式列表表示
    private String stringConst;  // 字符串常量

    // 用于普通的初始化表达式
    public InitValNode(ExpNode expression) {
        this.expression = expression;
    }

    // 用于初始化列表
    public InitValNode(List<ExpNode> initValues) {
        this.initValues = initValues;
    }

    // 用于字符串初始化
    public InitValNode(String stringConst) {
        this.stringConst = stringConst;
    }

    public void printNode(Parser parser) throws IOException {
        if (expression != null) {
            expression.printNode(parser);
        } else if (initValues != null) {
            parser.write("LBRACE {");
            for (int i = 0; i < initValues.size(); i++) {
                initValues.get(i).printNode(parser);
                if (i < initValues.size() - 1) {
                    parser.write("COMMA ,");
                }
            }
            parser.write("RBRACE }");
        } else if (stringConst != null) {
            parser.write("STRCON " + stringConst);
        }
        parser.write("<InitVal>");
    }
}
