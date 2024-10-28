package node;
import frontend.Parser;
import java.io.IOException;

public class StmtNodeWithBlock extends StmtNode {
    private BlockNode blockNode;

    public StmtNodeWithBlock(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        blockNode.printNode(parser);
        parser.write("<Stmt>");
    }
}
