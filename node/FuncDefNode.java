package node;

import frontend.Parser;
import frontend.Token;
import java.io.IOException;
import java.util.List;

public class FuncDefNode {
    private FuncTypeNode funcType;
    private Token funcName;
    private FuncFParamNodes funcFParams;
    private BlockNode block;

    public FuncDefNode(FuncTypeNode funcType, Token funcName, FuncFParamNodes funcFParams, BlockNode block) {
        this.funcType = funcType;
        this.funcName = funcName;
        this.funcFParams = funcFParams;
        this.block = block;
    }

    public void printNode(Parser parser) throws IOException {
        // 输出函数类型和名称
        funcType.printNode(parser);
        parser.write("IDENFR " + funcName.getValue());
        parser.write("LPARENT (");

        // 如果函数有形参，打印形参节点
        if (funcFParams != null) {
            funcFParams.printNode(parser);
        }

        parser.write("RPARENT )");
        block.printNode(parser);
        parser.write("<FuncDef>");
    }
}
