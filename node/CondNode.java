package node;

import frontend.Parser;
import java.io.IOException;

public class CondNode {
    private LOrExpNode condition;

    public CondNode(LOrExpNode condition) {
        this.condition = condition;
    }


    public void printNode(Parser parser) throws IOException {
        // 输出条件表达式
        condition.printNode(parser);
        // 输出非终结符 <Cond>
        parser.write("<Cond>");
    }
}
