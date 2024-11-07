package node;

import frontend.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import symbol.*;

public class FuncFParamNodes {
    private List<FuncFParamNode> funcFParamNodes;

    public FuncFParamNodes(List<FuncFParamNode> funcFParamNodes) {
        this.funcFParamNodes = funcFParamNodes;
    }
    
    public List<FuncFParamNode> getFuncFParamNodes() {
        return funcFParamNodes;
    }
    public List<Symbol.SymbolType> getSymbolTypes() {
        List<Symbol.SymbolType> symbolTypes = new ArrayList<>();
        for (FuncFParamNode param : funcFParamNodes) {
            symbolTypes.add(param.getSymbolType());
        }
        return symbolTypes;
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
