package node;

import frontend.Parser;
import java.io.IOException;

public class EmptyStmtNode extends StmtNode {

    public EmptyStmtNode() {
        // 空构造函数
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
