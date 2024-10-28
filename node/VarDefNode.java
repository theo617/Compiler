package node;

import frontend.Parser;
import java.io.IOException;

public class VarDefNode {
    private String ident;
    private ConstExpNode constExp;  // 数组定义时的大小
    private InitValNode initVal;    // 初始化值

    public VarDefNode(String ident, ConstExpNode constExp, InitValNode initVal) {
        this.ident = ident;
        this.constExp = constExp;
        this.initVal = initVal;
    }

    public String getIdent() {
        return ident;
    }

    public ConstExpNode getConstExp() {
        return constExp;
    }

    public InitValNode getInitVal() {
        return initVal;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("IDENFR " + ident); // 输出标识符
        if (constExp != null) {
            parser.write("LBRACK [");
            constExp.printNode(parser);  // 输出数组维度
            parser.write("RBRACK ]");
        }
        if (initVal != null) {
            parser.write("ASSIGN =");
            initVal.printNode(parser);   // 输出初值
        }
        parser.write("<VarDef>");
    }
}
