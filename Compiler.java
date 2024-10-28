import frontend.Lexer;
import frontend.Parser;
import frontend.Token;
import frontend.LexicalError;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) {
        String inputFilePath = "testfile.txt";
        String lexerOutputPath = "lexer.txt";
        String parserOutputPath = "parser.txt";
        String errorOutputPath = "error.txt";

        try {
            Lexer lexer = new Lexer(inputFilePath);
            lexer.analyze();

            lexer.writeTokens(lexerOutputPath);
            //lexer.writeErrors(errorOutputPath);

            System.out.println("词法分析完成。");


            Parser parser = new Parser(lexer, parserOutputPath, errorOutputPath);
            parser.analyze();
            parser.printParseAns();
            parser.writeErrors(errorOutputPath);
            System.out.println("语法分析完成。");


        } catch (IOException e) {
            System.err.println("文件处理出错: " + e.getMessage());
        }


    }
}
