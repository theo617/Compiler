package node;

import frontend.Parser;
import java.io.IOException;

public class ExpNode {
    private AddExpNode addExpNode;
    public ExpNode(AddExpNode addExpNode) {
        this.addExpNode = addExpNode;
    }
    public void printNode(Parser parser) throws IOException {
        addExpNode.printNode(parser);
        parser.write("<Exp>");
    }
}
