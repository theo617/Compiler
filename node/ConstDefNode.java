package node;

import frontend.Parser;
import java.io.IOException;

public class ConstDefNode {
    private IdentNode ident;
    private ConstExpNode constExp;  // 可选，可能为 null
    private ConstInitValNode initVal;

    public ConstDefNode(IdentNode ident, ConstExpNode constExp, ConstInitValNode initVal) {
        this.ident = ident;
        this.constExp = constExp;
        this.initVal = initVal;
    }

    public void printNode(Parser parser) throws IOException {
        // 输出标识符
        ident.printNode(parser);
        // 如果有常量表达式，输出它
        if (constExp != null) {
            parser.write("LBRACK [");
            constExp.printNode(parser);
            parser.write("RBRACK ]");
        }
        // 输出等号和初始值
        if (initVal != null) {
            parser.write("ASSIGN =");
            initVal.printNode(parser);   // 输出初值
        }
        // 输出非终结符 <ConstDef>
        parser.write("<ConstDef>");
    }
}
