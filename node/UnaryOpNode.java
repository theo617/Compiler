package node;

import frontend.Token;
import frontend.Parser;
import java.io.IOException;
import java.util.Objects;

public class UnaryOpNode {
    private Token operator;


    public UnaryOpNode(Token operator) {
        this.operator = operator;
    }

    public void printNode(Parser parser) throws IOException {
        if(Objects.equals(operator.getValue(), "+")){
            parser.write("PLUS +");
        } else if(Objects.equals(operator.getValue(), "-")){
            parser.write("MINU -");
        } else if(Objects.equals(operator.getValue(), "!")){
            parser.write("NOT !");
        }
        parser.write("<UnaryOp>");
    }
}