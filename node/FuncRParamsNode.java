package node;

import frontend.Parser;
import java.io.IOException;
import java.util.List;
import symbol.Type;

public class FuncRParamsNode {
    private List<ExpNode> expressions;

    public FuncRParamsNode(List<ExpNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExpNode> getExpressions() {
        return expressions;
    }

    public void printNode(Parser parser) throws IOException {
        
        for (int i = 0; i < expressions.size(); i++) {
            expressions.get(i).printNode(parser);
            if (i < expressions.size() - 1) {
                parser.write("COMMA ,");
            }
        }
        parser.write("<FuncRParams>");
    }
}
