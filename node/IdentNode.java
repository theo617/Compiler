package node;

import frontend.Parser;
import java.io.IOException;

public class IdentNode {
    private String name;

    public IdentNode(String name) {
        this.name = name;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("IDENFR " + name);
    }
}
