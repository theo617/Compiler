package node;

import frontend.Parser;
import java.io.IOException;
import symbol.Type;

public class ExpNode {
    private AddExpNode addExpNode;
    private Type type; // 新增类型属性
    public ExpNode(AddExpNode addExpNode) {
        this.addExpNode = addExpNode;
        
    }

    public Type getType(Parser parser) {
        this.type = addExpNode.getType(parser);
        //System.out.println("ExpNode: " + this.type);
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    

    public void printNode(Parser parser) throws IOException {
        addExpNode.printNode(parser);
        
        parser.write("<Exp>");
    }
}
