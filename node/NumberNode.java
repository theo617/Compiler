package node;

import frontend.Token;
import frontend.Parser;
import java.io.IOException;

public class NumberNode {
    private Token number;

    public NumberNode(Token number) {
        this.number = number;
    }

    public void printNode(Parser parser) throws IOException {
        parser.write("INTCON " + number.getValue());
        parser.write("<Number>");
    }
}
