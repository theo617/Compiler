package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConstDeclNode {
    private String bType;
    private List<ConstDefNode> constDefs;

    public ConstDeclNode(String bType, List<ConstDefNode> constDefs) {
        this.bType = bType;
        this.constDefs = constDefs;
    }

    public String getBType() {
        return bType;
    }

    public List<ConstDefNode> getConstDefs() {
        return constDefs;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("CONSTTK const");
        // 输出类型
        if(Objects.equals(bType, "int")){
            parser.write("INTTK int");
        } else if(Objects.equals(bType, "char")){
            parser.write("CHARTK char");
        }
        boolean isFirst = true;
        // 输出常量定义
        for (ConstDefNode def : constDefs) {
            if(isFirst){
                isFirst = false;
            }else {
                parser.write("COMMA ,");
            }
            def.printNode(parser);
        }
        parser.write("SEMICN ;");
        // 输出非终结符 <ConstDecl>
        parser.write("<ConstDecl>");
    }
}
