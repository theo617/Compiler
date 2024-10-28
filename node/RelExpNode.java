package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;

public class RelExpNode {
    private AddExpNode addExpNode;  // 存储加法表达式
    private Token operator;         // 存储关系操作符
    private RelExpNode relExpNode;  // 递归存储关系表达式

    // 构造函数：关系表达式
    public RelExpNode(AddExpNode addExpNode, Token operator, RelExpNode relExpNode) {
        this.addExpNode = addExpNode;
        this.operator = operator;
        this.relExpNode = relExpNode;
    }

    public void printNode(Parser parser) throws IOException {
        addExpNode.printNode(parser);  // 打印加法表达式
        parser.write("<RelExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());  // 打印操作符
            relExpNode.printNode(parser);  // 打印剩下的关系表达式
        }
    }
}
