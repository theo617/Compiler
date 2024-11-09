package node;

import frontend.Parser;
import java.io.IOException;
import symbol.Type;

public class ConstExpNode {
    private AddExpNode addExp;
    private Type type;

    public ConstExpNode(AddExpNode addExp) {
        this.addExp = addExp;
    }

    public Type getType(Parser parser) {
        // 类型推断
        if (addExp.getType(parser) == Type.INT) {
            this.type = Type.INT;
        } else if(addExp.getType(parser) == Type.CHAR){
            this.type = Type.CHAR;
        } else if(addExp.getType(parser) == Type.INT_ARRAY){
            this.type = Type.INT_ARRAY;
        } else if(addExp.getType(parser) == Type.CHAR_ARRAY){
            this.type = Type.CHAR_ARRAY;
        } else {
            this.type = Type.UNKNOWN;
        }
        //System.out.println("ConstExpNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public void printNode(Parser parser) throws IOException {
        // 输出表达式内容
        addExp.printNode(parser);
        // 输出非终结符 <ConstExp>
        parser.write("<ConstExp>");
    }
}
