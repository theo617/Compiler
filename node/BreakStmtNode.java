package node;

import frontend.Parser;
import java.io.IOException;

public class BreakStmtNode extends StmtNode {

    public BreakStmtNode() {
        // 空构造函数
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        // 输出 'break;' 和非终结符 <Stmt>
        parser.write("BREAKTK break");
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
