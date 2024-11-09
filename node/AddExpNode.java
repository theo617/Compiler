package node;
import frontend.Parser;
import frontend.SemanticError;

import java.io.IOException;
import frontend.Token;
import symbol.Type;

public class AddExpNode {
    private MulExpNode mulExpNode;
    private Token operator;
    private AddExpNode addExpNode;
    private Type type; // 新增类型属性

    // 构造函数：加法或减法
    public AddExpNode(MulExpNode mulExpNode, Token operator, AddExpNode addExpNode) {
        this.mulExpNode = mulExpNode;
        this.operator = operator;
        this.addExpNode = addExpNode;
    }

    public Type getType(Parser parser) {
        // 类型推断
        if (mulExpNode.getType(parser) == Type.INT) {
            this.type = Type.INT;
        } else if(mulExpNode.getType(parser) == Type.CHAR){
            this.type = Type.CHAR;
        } else if(mulExpNode.getType(parser) == Type.INT_ARRAY){
            this.type = Type.INT_ARRAY;
        } else if(mulExpNode.getType(parser) == Type.CHAR_ARRAY){
            this.type = Type.CHAR_ARRAY;
        } else {
            this.type = Type.UNKNOWN;
        }
        //System.out.println("AddExpNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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