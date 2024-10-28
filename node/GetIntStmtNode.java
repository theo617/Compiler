package node;

import frontend.Parser;
import java.io.IOException;

public class GetIntStmtNode extends StmtNode {
    private LValNode lval;

    public GetIntStmtNode(LValNode lval) {
        this.lval = lval;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        lval.printNode(parser);
        parser.write("ASSIGN =");
        parser.write("GETINTTK getint");
        parser.write("LPARENT (");
        parser.write("RPARENT )");
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
