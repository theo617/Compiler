package node;

import frontend.Parser;
import java.io.IOException;

public class GetCharStmtNode extends StmtNode {
    private LValNode lval;

    public GetCharStmtNode(LValNode lval) {
        this.lval = lval;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        lval.printNode(parser);
        parser.write("ASSIGN =");
        parser.write("GETCHARTK getchar");
        parser.write("LPARENT (");
        parser.write("RPARENT )");
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
