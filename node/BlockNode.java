package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class BlockNode extends StmtNode {
    private List<BlockItemNode> blockItems;

    public BlockNode(List<BlockItemNode> blockItems) {
        this.blockItems = blockItems;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        // 输出左大括号
        parser.write("LBRACE {");
        // 遍历输出 blockItems
        for (BlockItemNode item : blockItems) {
            item.printNode(parser);
        }
        // 输出右大括号
        parser.write("RBRACE }");
        // 输出非终结符 <Block>
        parser.write("<Block>");
    }
}
