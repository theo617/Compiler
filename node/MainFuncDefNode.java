package node;

import frontend.Parser;
import java.io.IOException;

public class MainFuncDefNode {
    private String returnType;  // 返回类型, 如 'int'
    private String functionName;  // 函数名, 这里固定为 'main'
    private BlockNode body;  // 函数体

    public MainFuncDefNode(String returnType, String functionName, BlockNode body) {
        this.returnType = returnType;
        this.functionName = functionName;
        this.body = body;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("INTTK int");
        parser.write("MAINTK main");
        parser.write("LPARENT (");
        parser.write("RPARENT )");
        body.printNode(parser);
        parser.write("<MainFuncDef>");
    }
}
