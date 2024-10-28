package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class FuncFParamNodes {
    private List<FuncFParamNode> funcFParamNodes;

    public FuncFParamNodes(List<FuncFParamNode> funcFParamNodes) {
        this.funcFParamNodes = funcFParamNodes;
    }

    public void printNode(Parser parser) throws IOException {
        boolean isFirst = true;
        for (FuncFParamNode param : funcFParamNodes) {
            if(isFirst){
                isFirst = false;
            }else {
                parser.write("COMMA ,");
            }
            param.printNode(parser);
        }
        parser.write("<FuncFParams>");
    }
}
