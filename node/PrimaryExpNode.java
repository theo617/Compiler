package node;

import frontend.Parser;
import java.io.IOException;

public class PrimaryExpNode {
    private ExpNode exp;
    private LValNode lVal;
    private NumberNode number;
    private CharacterNode character;

    // 构造器1：括号表达式
    public PrimaryExpNode(ExpNode exp) {
        this.exp = exp;
    }

    // 构造器2：LVal
    public PrimaryExpNode(LValNode lVal) {
        this.lVal = lVal;
    }

    // 构造器3：数字常量
    public PrimaryExpNode(NumberNode number) {
        this.number = number;
    }

    // 构造器4：字符常量
    public PrimaryExpNode(CharacterNode character) {
        this.character = character;
    }

    public void printNode(Parser parser) throws IOException {

        if (exp != null) {
            parser.write("LPARENT (");
            exp.printNode(parser);
            parser.write("RPARENT )");
        } else if (lVal != null) {
            lVal.printNode(parser);
        } else if (number != null) {
            number.printNode(parser);
        } else if (character != null) {
            character.printNode(parser);
        }
        parser.write("<PrimaryExp>");
    }
}
