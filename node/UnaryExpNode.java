package node;

import frontend.Parser;
import java.io.IOException;

public class UnaryExpNode {
    private String functionName;
    private FuncRParamsNode funcRParams;
    private UnaryOpNode unaryOp;
    private UnaryExpNode unaryExp;
    private PrimaryExpNode primaryExp;

    // 构造器1：函数调用
    public UnaryExpNode(String functionName, FuncRParamsNode funcRParams) {
        this.functionName = functionName;
        this.funcRParams = funcRParams;
    }

    // 构造器2：一元运算
    public UnaryExpNode(UnaryOpNode unaryOp, UnaryExpNode unaryExp) {
        this.unaryOp = unaryOp;
        this.unaryExp = unaryExp;
    }

    // 构造器3：基本表达式
    public UnaryExpNode(PrimaryExpNode primaryExp) {
        this.primaryExp = primaryExp;
    }


    public void printNode(Parser parser) throws IOException {
        if (functionName != null) {
            parser.write("IDENFR " + functionName);
            parser.write("LPARENT (");
            if (funcRParams != null) {
                funcRParams.printNode(parser);
            }
            parser.write("RPARENT )");
        } else if (unaryOp != null) {
            unaryOp.printNode(parser);
            unaryExp.printNode(parser);
        } else if (primaryExp != null) {
            primaryExp.printNode(parser);
        }
        parser.write("<UnaryExp>");
    }
}
