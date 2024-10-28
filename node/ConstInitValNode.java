package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class ConstInitValNode {
    private ConstExpNode constExp;  // 可选，可能为 null
    private List<ConstExpNode> constExps;  // 可选，可能为 null
    private String stringConst;  // 可选，可能为 null

    public ConstInitValNode(ConstExpNode constExp) {
        this.constExp = constExp;
    }

    public ConstInitValNode(List<ConstExpNode> constExps) {
        this.constExps = constExps;
    }

    public ConstInitValNode(String stringConst) {
        this.stringConst = stringConst;
    }

    public void printNode(Parser parser) throws IOException {
        if (constExp != null) {
            constExp.printNode(parser);
        }
        if (constExps != null) {
            parser.write("LBRACE {");
            boolean isFirst = true;
            for (ConstExpNode exp : constExps) {
                if(isFirst){
                    isFirst = false;
                }else {
                    parser.write("COMMA ,");
                }
                exp.printNode(parser);
            }
            parser.write("RBRACE }");
        }
        if (stringConst != null) {
            parser.write("STRCON " + stringConst);
        }
        // 输出非终结符 <ConstInitVal>
        parser.write("<ConstInitVal>");
    }
}
