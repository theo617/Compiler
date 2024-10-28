package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;

public class LOrExpNode {
    private LAndExpNode landExpNode;  // 存储逻辑与表达式
    private Token operator;           // 存储 '||' 操作符
    private LOrExpNode lorExpNode;    // 递归存储逻辑或表达式

    // 构造函数：逻辑或表达式
    public LOrExpNode(LAndExpNode landExpNode, Token operator, LOrExpNode lorExpNode) {
        this.landExpNode = landExpNode;
        this.operator = operator;
        this.lorExpNode = lorExpNode;
    }

    public void printNode(Parser parser) throws IOException {
        landExpNode.printNode(parser);  // 打印逻辑与表达式
        parser.write("<LOrExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());  // 打印 '||'
            lorExpNode.printNode(parser);  // 打印递归的逻辑或表达式
        }

    }
}
