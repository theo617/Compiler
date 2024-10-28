package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;

public class EqExpNode {
    private RelExpNode relExpNode;  // 存储关系表达式
    private Token operator;         // 存储相等性操作符
    private EqExpNode eqExpNode;    // 递归存储相等性表达式

    // 构造函数：相等性表达式
    public EqExpNode(RelExpNode relExpNode, Token operator, EqExpNode eqExpNode) {
        this.relExpNode = relExpNode;
        this.operator = operator;
        this.eqExpNode = eqExpNode;
    }

    public void printNode(Parser parser) throws IOException {
        relExpNode.printNode(parser);  // 打印关系表达式
        parser.write("<EqExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());  // 打印操作符
            eqExpNode.printNode(parser);  // 打印剩下的相等性表达式
        }

    }
}
