package node;

import frontend.Parser;
import java.io.IOException;

public class ForStmtNode extends StmtNode {
    private LValNode initLVal;    // 用于初始化的左值
    private ExpNode initExp;      // 用于初始化的表达式
    private CondNode condition;   // for 循环条件
    private LValNode iterLVal;    // 用于更新的左值
    private ExpNode iterExp;      // 用于更新的表达式
    private StmtNode body;        // 循环体

    // 构造函数：LVal = Exp 的形式
    public ForStmtNode(LValNode initLVal, ExpNode initExp, StmtNode body) {
        this.initLVal = initLVal;
        this.initExp = initExp;
        this.condition = null;  // 不涉及条件
        this.iterLVal = null;   // 不涉及更新部分
        this.iterExp = null;
        this.body = body;
    }

    // 构造函数：完整的 for 语句形式
    public ForStmtNode(LValNode initLVal, ExpNode initExp, CondNode condition, LValNode iterLVal, ExpNode iterExp, StmtNode body) {
        this.initLVal = initLVal;
        this.initExp = initExp;
        this.condition = condition;
        this.iterLVal = iterLVal;
        this.iterExp = iterExp;
        this.body = body;
    }

    // 构造函数：简单的初始化、条件、更新部分的形式
    public ForStmtNode(StmtNode initStmt, CondNode condition, StmtNode updateStmt, StmtNode body) {
        this.initLVal = null;
        this.initExp = null;
        this.iterLVal = null;
        this.iterExp = null;
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void printNode(Parser parser) throws IOException {
        parser.write("FORTK for");
        parser.write("LPARENT (");

        if (initLVal != null) {
            initLVal.printNode(parser);
            parser.write("ASSIGN =");
            initExp.printNode(parser);
            parser.write("<ForStmt>");
        }

        parser.write("SEMICN ;");

        if (condition != null) {
            condition.printNode(parser);
        }

        parser.write("SEMICN ;");

        if (iterLVal != null) {
            iterLVal.printNode(parser);
            parser.write("ASSIGN =");
            iterExp.printNode(parser);
            parser.write("<ForStmt>");
        }

        parser.write("RPARENT )");

        body.printNode(parser);
        parser.write("<Stmt>");

    }
}
