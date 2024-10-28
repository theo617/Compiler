package node;

import frontend.Parser;
import java.io.IOException;

public class ContinueStmtNode extends StmtNode {

    public ContinueStmtNode() {
        // 空构造函数
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        // 输出 continue 语句
        parser.write("CONTINUETK continue");
        parser.write("SEMICN ;");
        // 输出非终结符 <Stmt>
        parser.write("<Stmt>");
    }
}
