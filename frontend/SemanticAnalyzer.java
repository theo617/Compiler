package frontend;
import symbol.SymbolTable;
import java.util.*;



public class SemanticAnalyzer {
    private SymbolTable symbolTable;
    private List<SemanticError> semanticErrors;
    private List<ParserError> parserErrors;

    public List<SemanticError> getSemanticErrors() {
        return semanticErrors;
    }

}
