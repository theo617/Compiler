package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;
import symbol.Type;

public class RelExpNode {
    private AddExpNode addExpNode;  // 存储加法表达式
    private Token operator;         // 存储关系操作符
    private RelExpNode relExpNode;  // 递归存储关系表达式
    private Type type;              // 新增类型属性
    // 构造函数：关系表达式
    public RelExpNode(AddExpNode addExpNode, Token operator, RelExpNode relExpNode) {
        this.addExpNode = addExpNode;
        this.operator = operator;
        this.relExpNode = relExpNode;
    }
    public Type getType(Parser parser) {
        // 类型推断
        if (addExpNode.getType(parser) == Type.INT) {
            this.type = Type.INT;
        } else if(addExpNode.getType(parser) == Type.CHAR){
            this.type = Type.CHAR;
        } else if(addExpNode.getType(parser) == Type.INT_ARRAY){
            this.type = Type.INT_ARRAY;
        } else if(addExpNode.getType(parser) == Type.CHAR_ARRAY){
            this.type = Type.CHAR_ARRAY;
        } else {
            this.type = Type.UNKNOWN;
        }
        //System.out.println("RelExpNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
