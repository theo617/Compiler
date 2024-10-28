package node;

import frontend.Parser;
import java.io.IOException;

public class LValNode {
    private String ident;
    private ExpNode index;

    public LValNode(String ident, ExpNode index) {
        this.ident = ident;
        this.index = index;
    }


    public void printNode(Parser parser) throws IOException {
        parser.write("IDENFR " + ident);
        if (index != null) {
            parser.write("LBRACK [");
            index.printNode(parser);
            parser.write("RBRACK ]");
        }
        parser.write("<LVal>");
    }
}
