package node;
import frontend.Parser;
import java.io.IOException;
import frontend.Token;
import symbol.Type;

public class MulExpNode  {
    private UnaryExpNode unaryExpNode;  // 存储一元表达式
    private Token operator;             // 存储操作符（* / %）
    private MulExpNode mulExpNode;      // 存储嵌套的乘除模表达式
    private Type type;                  // 新增类型属性

    // 构造函数：乘法、除法或取模
    public MulExpNode(UnaryExpNode unaryExpNode, Token operator, MulExpNode mulExpNode) {
        this.unaryExpNode = unaryExpNode;
        this.operator = operator;
        this.mulExpNode = mulExpNode;
    }

    public Type getType(Parser parser) {
        // 类型推断
        if (unaryExpNode.getType(parser) == Type.INT) {
            this.type = Type.INT;
        } else if(unaryExpNode.getType(parser) == Type.CHAR){
            this.type = Type.CHAR;
        } else if(unaryExpNode.getType(parser) == Type.INT_ARRAY){
            this.type = Type.INT_ARRAY;
        } else if(unaryExpNode.getType(parser) == Type.CHAR_ARRAY){
            this.type = Type.CHAR_ARRAY;
        } else {
            this.type = Type.UNKNOWN;

        }
        //System.out.println("MulExpNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
