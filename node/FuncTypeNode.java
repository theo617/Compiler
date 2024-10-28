package node;

import frontend.Parser;
import java.io.IOException;
import java.util.Objects;

public class FuncTypeNode {
    private String returnType;  // 返回类型，比如 'int', 'void', 'char'

    public FuncTypeNode(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void printNode(Parser parser) throws IOException {
        if(Objects.equals(returnType, "int")){
            parser.write("INTTK int");
        } else if(Objects.equals(returnType, "char")){
            parser.write("CHARTK char");
        } else {
            parser.write("VOIDTK void");
        }
        parser.write("<FuncType>");
    }
}
