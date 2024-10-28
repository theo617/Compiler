package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;

public class MulExpNode  {
    private UnaryExpNode unaryExpNode;  // 存储一元表达式
    private Token operator;             // 存储操作符（* / %）
    private MulExpNode mulExpNode;      // 存储嵌套的乘除模表达式

    // 构造函数：乘法、除法或取模
    public MulExpNode(UnaryExpNode unaryExpNode, Token operator, MulExpNode mulExpNode) {
        this.unaryExpNode = unaryExpNode;
        this.operator = operator;
        this.mulExpNode = mulExpNode;
    }


    public void printNode(Parser parser) throws IOException {
        unaryExpNode.printNode(parser);
        parser.write("<MulExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());
            mulExpNode.printNode(parser);
        }

    }
}
