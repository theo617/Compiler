package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;
import symbol.Type;

public class LAndExpNode {
    private EqExpNode eqExpNode;    // 存储相等性表达式
    private Token operator;         // 存储 '&&' 操作符
    private LAndExpNode landExpNode;  // 递归存储逻辑与表达式
    private Type type;              // 新增类型属性
    // 构造函数：逻辑与表达式
    public LAndExpNode(EqExpNode eqExpNode, Token operator, LAndExpNode landExpNode) {
        this.eqExpNode = eqExpNode;
        this.operator = operator;
        this.landExpNode = landExpNode;
    }

    public Type getType(Parser parser) {
        // 类型推断
        if (eqExpNode.getType(parser) == Type.INT) {
            this.type = Type.INT;
        } else if(eqExpNode.getType(parser) == Type.CHAR){
            this.type = Type.CHAR;
        } else if(eqExpNode.getType(parser) == Type.INT_ARRAY){
            this.type = Type.INT_ARRAY;
        } else if(eqExpNode.getType(parser) == Type.CHAR_ARRAY){
            this.type = Type.CHAR_ARRAY;
        } else {
            this.type = Type.UNKNOWN;
        }
        //System.out.println("LAndExpNode: " + this.type);
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public void printNode(Parser parser) throws IOException {
        eqExpNode.printNode(parser);  // 打印相等性表达式
        parser.write("<LAndExp>");
        if (operator != null) {
            parser.write(operator.getType() + " " + operator.getValue());  // 打印 '&&'
            landExpNode.printNode(parser);  // 打印递归的逻辑与表达式
        }

    }
}
