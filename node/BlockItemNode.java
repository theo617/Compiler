package node;

import frontend.Parser;
import java.io.IOException;

public class BlockItemNode {
    private DeclNode decl;
    private StmtNode stmt;

    public BlockItemNode(DeclNode decl) {
        this.decl = decl;
    }

    public BlockItemNode(StmtNode stmt) {
        this.stmt = stmt;
    }

    public void printNode(Parser parser) throws IOException {
        if (decl != null) {
            // 输出声明节点
            decl.printNode(parser);
        } else if (stmt != null) {
            // 输出语句节点
            stmt.printNode(parser);
        }
        // 不输出非终结符 <BlockItem>
        //parser.write("<BlockItem>");
    }
}
