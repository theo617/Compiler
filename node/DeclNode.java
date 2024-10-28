package node;

import frontend.Parser;
import java.io.IOException;

public class DeclNode {
    private ConstDeclNode constDecl;
    private VarDeclNode varDecl;

    public DeclNode(ConstDeclNode constDecl) {
        this.constDecl = constDecl;
    }

    public DeclNode(VarDeclNode varDecl) {
        this.varDecl = varDecl;
    }

    public void printNode(Parser parser) throws IOException {
        if (constDecl != null) {
            constDecl.printNode(parser);
        } else if (varDecl != null) {
            varDecl.printNode(parser);
        }
        // 不输出非终结符 <Decl>
        //parser.write("<Decl>");
    }
}
