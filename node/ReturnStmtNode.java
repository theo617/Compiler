package node;

import frontend.Parser;
import java.io.IOException;

public class ReturnStmtNode extends StmtNode {
    private ExpNode returnValue;

    public ReturnStmtNode(ExpNode returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        parser.write("RETURNTK return");
        if (returnValue != null) {
            returnValue.printNode(parser);
        }
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
