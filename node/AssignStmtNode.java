package node;

import frontend.Parser;
import java.io.IOException;

public class AssignStmtNode extends StmtNode {
    private LValNode lval;
    private ExpNode exp;

    public AssignStmtNode(LValNode lval, ExpNode exp) {
        this.lval = lval;
        this.exp = exp;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        // 输出 LVal 部分
        lval.printNode(parser);
        // 输出赋值符号 '='
        parser.write("ASSIGN =");
        // 输出表达式部分
        exp.printNode(parser);
        // 输出分号 ';'
        parser.write("SEMICN ;");

        // 输出非终结符 <Stmt>
        parser.write("<Stmt>");
    }
}
