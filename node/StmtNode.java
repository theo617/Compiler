package node;

import frontend.Parser;
import java.io.IOException;

public class StmtNode {
    public void printNode(Parser parser) throws IOException {
        parser.write("<Stmt>");
    }
}
