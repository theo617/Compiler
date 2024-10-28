package node;

import frontend.Parser;
import java.io.IOException;

public class ExpStmtNode extends StmtNode {
    private ExpNode exp;

    public ExpStmtNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        exp.printNode(parser);
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
