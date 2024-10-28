package node;

import frontend.Parser;
import java.io.IOException;

public class IfStmtNode extends StmtNode {
    private CondNode condition;
    private StmtNode thenStmt;
    private StmtNode elseStmt;

    public IfStmtNode(CondNode condition, StmtNode thenStmt, StmtNode elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        parser.write("IFTK if");
        parser.write("LPARENT (");
        condition.printNode(parser);
        parser.write("RPARENT )");
        thenStmt.printNode(parser);
        if (elseStmt != null) {
            parser.write("ELSETK else");
            elseStmt.printNode(parser);
        }
        parser.write("<Stmt>");
    }
}
