package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;

public class AddExpNode {
    private MulExpNode mulExpNode;
    private Token operator;
    private AddExpNode addExpNode;

    // 构造函数：加法或减法
    public AddExpNode(MulExpNode mulExpNode, Token operator, AddExpNode addExpNode) {
        this.mulExpNode = mulExpNode;
        this.operator = operator;
        this.addExpNode = addExpNode;
    }

    public void printNode(Parser parser) throws IOException {
        mulExpNode.printNode(parser);
        parser.write("<AddExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());
            addExpNode.printNode(parser);
        }

    }
}