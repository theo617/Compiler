package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VarDeclNode {
    private String bType;
    private List<VarDefNode> varDefs;

    public VarDeclNode(String bType, List<VarDefNode> varDefs) {
        this.bType = bType;
        this.varDefs = varDefs;
    }

    public String getBType() {
        return bType;
    }

    public List<VarDefNode> getVarDefs() {
        return varDefs;
    }

    public void printNode(Parser parser) throws IOException {
        if(Objects.equals(bType, "int")){
            parser.write("INTTK int");
        } else if(Objects.equals(bType, "char")){
            parser.write("CHARTK char");
        }
        boolean isFirst = true;
        for (VarDefNode varDef : varDefs) {
            if(isFirst){
                isFirst = false;
            }else {
                parser.write("COMMA ,");
            }
            varDef.printNode(parser); // 输出每个变量定义
        }
        parser.write("SEMICN ;");
        parser.write("<VarDecl>");
    }
}
