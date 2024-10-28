package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class PrintfStmtNode extends StmtNode {
    private StringConstNode stringConst;
    private List<ExpNode> args;

    public PrintfStmtNode(StringConstNode stringConst, List<ExpNode> args) {
        this.stringConst = stringConst;
        this.args = args;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        parser.write("PRINTFTK printf");
        parser.write("LPARENT (");
        parser.write("STRCON " + stringConst.getValue());
        for (ExpNode exp : args) {
            parser.write("COMMA ,");
            exp.printNode(parser);
        }
        parser.write("RPARENT )");
        parser.write("SEMICN ;");
        parser.write("<Stmt>");
    }
}
