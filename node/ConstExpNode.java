package node;

import frontend.Parser;
import java.io.IOException;

public class ConstExpNode {
    private AddExpNode addExp;

    public ConstExpNode(AddExpNode addExp) {
        this.addExp = addExp;
    }


    public void printNode(Parser parser) throws IOException {
        // 输出表达式内容
        addExp.printNode(parser);
        // 输出非终结符 <ConstExp>
        parser.write("<ConstExp>");
    }
}
