package node;

import frontend.Parser;
import frontend.Token;
import java.io.IOException;

public class CharacterNode {
    private Token character;

    public CharacterNode(Token character) {
        this.character = character;
    }


    public void printNode(Parser parser) throws IOException {
        // 输出字符常量
        parser.write("CHRCON " + character.getValue());
        // 输出非终结符
        parser.write("<Character>");
    }
}
