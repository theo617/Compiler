package node;

import frontend.Parser;
import frontend.SemanticError;

import java.io.IOException;

import symbol.FuncSymbol;
import symbol.Type;

public class UnaryExpNode {
    private String functionName;
    private FuncRParamsNode funcRParams;
    private UnaryOpNode unaryOp;
    private UnaryExpNode unaryExp;
    private PrimaryExpNode primaryExp;
    private Type type; // 新增类型属性

    // 构造器1：函数调用
    public UnaryExpNode(String functionName, FuncRParamsNode funcRParams) {
        this.functionName = functionName;
        this.funcRParams = funcRParams;
    }

    // 构造器2：一元运算
    public UnaryExpNode(UnaryOpNode unaryOp, UnaryExpNode unaryExp) {
        this.unaryOp = unaryOp;
        this.unaryExp = unaryExp;
    }

    // 构造器3：基本表达式
    public UnaryExpNode(PrimaryExpNode primaryExp) {
        this.primaryExp = primaryExp;
    }

    public Type getType(Parser parser) {
        if (functionName != null) {
            // 函数调用的返回类型
            FuncSymbol funcSymbol = parser.lookupFuncSymbol(functionName);
            if (funcSymbol != null) {
                // 假设函数返回类型存储在 funcSymbol 中，可以根据实际情况调整
                // 这里假设 void 函数返回 UNKNOWN
                switch (funcSymbol.getType()) {
                    case VoidFunc:
                        this.type = Type.UNKNOWN;
                        break;
                    case IntFunc:
                        this.type = Type.INT;
                        break;
                    case CharFunc:
                        this.type = Type.CHAR;
                        break;
                    default:
                        this.type = Type.UNKNOWN;
                }
            } else {
                this.type = Type.UNKNOWN;
            }
        } else if (unaryOp != null) {
            this.type = unaryExp.getType(parser);
        } else if (primaryExp != null) {
            this.type = primaryExp.getType(parser);
        }
        //System.out.println("UnaryExpNode: " + this.type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public void printNode(Parser parser) throws IOException {
        if (functionName != null) {
            parser.write("IDENFR " + functionName);
            parser.write("LPARENT (");
            if (funcRParams != null) {
                funcRParams.printNode(parser);
            }
            parser.write("RPARENT )");


        } else if (unaryOp != null) {
            unaryOp.printNode(parser);
            unaryExp.printNode(parser);
        } else if (primaryExp != null) {
            primaryExp.printNode(parser);
        }
        parser.write("<UnaryExp>");
    }
}
