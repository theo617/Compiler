package node;
import frontend.Parser;
import java.io.IOException;
import java.util.List;

public class CompUnitNode {
    private List<DeclNode> declarations;
    private List<FuncDefNode> functionDefinitions;
    private MainFuncDefNode mainFunction;

    public CompUnitNode(List<DeclNode> declarations, List<FuncDefNode> functionDefinitions, MainFuncDefNode mainFunction) {
        this.declarations = declarations;
        this.functionDefinitions = functionDefinitions;
        this.mainFunction = mainFunction;
    }

    // getters
    public List<DeclNode> getDeclarations() {
        return declarations;
    }

    public List<FuncDefNode> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public MainFuncDefNode getMainFunction() {
        return mainFunction;
    }


    public void printNode(Parser parser) throws IOException {
        // 先输出声明部分
        for (DeclNode decl : declarations) {
            decl.printNode(parser);  // 输出每个声明节点
        }

        // 输出函数定义部分
        for (FuncDefNode funcDef : functionDefinitions) {
            funcDef.printNode(parser);  // 输出每个函数定义节点
        }

        // 输出主函数定义部分
        mainFunction.printNode(parser);

        // 最后输出非终结符 <CompUnit>
        parser.write("<CompUnit>");
    }
}