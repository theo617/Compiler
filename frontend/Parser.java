package frontend;

import node.*;
import java.io.*;
import java.util.*;

public class Parser {
    private List<Token> tokens;
    private int currentIndex;  // 当前token的索引
    private Token currentToken;
    private CompUnitNode compUnitNode;
    private BufferedWriter writer;
    private List<LexicalError> errors;

    public Parser(Lexer lexer, String outputFilePath, String errorOutputFilePath) throws IOException {
        this.tokens = lexer.getTokens();  // 获取所有tokens
        errors = new ArrayList<>();
        this.errors = lexer.getErrors();
        this.currentIndex = 0;
        this.currentToken = tokens.get(currentIndex);  // 获取第一个token
        this.writer = new BufferedWriter(new FileWriter(outputFilePath));  // 打开文件写入
    }

    public CompUnitNode getCompUnitNode(){
        return compUnitNode;
    }

    public void analyze() {
        this.compUnitNode = parseCompUnit();
    }

    // 前进到下一个token
    private void nextToken() {
        if (currentIndex < tokens.size() - 1) {
            currentIndex++;
            currentToken = tokens.get(currentIndex);
        }
    }

    private Token lookAhead(int offset) {
        int lookaheadIndex = currentIndex + offset;
        if (lookaheadIndex >= 0 && lookaheadIndex < tokens.size()) {
            return tokens.get(lookaheadIndex);
        }
        return null;
    }


    // 错误处理方法
    private void reportError(String message) {
        System.err.println("Error at token " + currentToken + ": " + message + " atLine: " + currentToken.getLineNumber());
        // 这里可以进一步完善错误处理，比如记录错误文件等
    }

    // 解析编译单元
    public CompUnitNode parseCompUnit() {
        List<DeclNode> declarations = new ArrayList<>();
        List<FuncDefNode> functionDefinitions = new ArrayList<>();

        while (lookAhead(1).getType() != TokenType.MAINTK && lookAhead(2).getType() != TokenType.LPARENT) {
            declarations.add(parseDecl());
        }

        while (lookAhead(1).getType() != TokenType.MAINTK) {
            functionDefinitions.add(parseFuncDef());
        }

        MainFuncDefNode mainFunction = parseMainFuncDef();

        return new CompUnitNode(declarations, functionDefinitions, mainFunction);
    }

    // 解析声明
    public DeclNode parseDecl() {
        if (currentToken.getType() == TokenType.CONSTTK) {
            return new DeclNode(parseConstDecl());
        } else {
            return new DeclNode(parseVarDecl());
        }
    }

    // 解析常量声明
    public ConstDeclNode parseConstDecl() {
        expect(TokenType.CONSTTK);
        String bType = parseBType();

        List<ConstDefNode> constDefs = new ArrayList<>();
        constDefs.add(parseConstDef());

        while (currentToken.getType() == TokenType.COMMA) {
            nextToken();
            //System.out.println("1 " + currentToken.getLineNumber() + " "+currentToken.getValue());
            constDefs.add(parseConstDef());
        }

        expect(TokenType.SEMICN);

        return new ConstDeclNode(bType, constDefs);
    }
    public String parseBType() {
        if (currentToken.getType() == TokenType.INTTK || currentToken.getType() == TokenType.CHARTK) {
            String bType = currentToken.getValue();  // 'int' 或 'char'
            nextToken();
            return bType;
        } else {
            return null;
        }
    }
    // 解析常量定义 ConstDef
    public ConstDefNode parseConstDef() {
        //System.out.println("def " + currentToken.getLineNumber() + " "+currentToken.getValue());
        IdentNode ident = parseIdent();  // 解析 Ident

        ConstExpNode constExp = null;
        if (currentToken.getType() == TokenType.LBRACK) {
            nextToken();  // 跳过 '['
            constExp =  parseConstExp();  // 解析 ConstExp
            expect(TokenType.RBRACK);  // 期待 ']'
        }
        expect(TokenType.ASSIGN);  // 期待 '='
        ConstInitValNode initVal = parseConstInitVal();  // 解析 ConstInitVal

        return new ConstDefNode(ident, constExp, initVal);
    }

    // 解析常量初值 ConstInitVal
    public ConstInitValNode parseConstInitVal() {
        //System.out.println("init " + currentToken.getLineNumber() + " "+currentToken.getValue());

        // 直接使用 parseConstExp() 来处理带符号的数字
        if (currentToken.getType() == TokenType.LBRACE) {
            expect(TokenType.LBRACE);
            List<ConstExpNode> constExps = new ArrayList<>();

            if (!currentToken.getType().equals(TokenType.RBRACE)) {
                constExps.add(parseConstExp());

                while (currentToken.getType() == TokenType.COMMA) {
                    expect(TokenType.COMMA);
                    constExps.add(parseConstExp());
                }
            }

            expect(TokenType.RBRACE);
            return new ConstInitValNode(constExps);
        } else if (currentToken.getType() == TokenType.STRCON) {
            String stringConst = currentToken.getValue();
            nextToken();
            return new ConstInitValNode(stringConst);
        } else {
            // 这里直接调用 parseConstExp()，它应该能处理带符号数字
            return new ConstInitValNode(parseConstExp());
        }
    }

    // 解析 Ident（标识符）
    public IdentNode parseIdent() {
        if (currentToken.getType() == TokenType.IDENFR) {
            IdentNode ident = new IdentNode(currentToken.getValue());
            nextToken();
            return ident;
        } else {
            reportError("Expected identifier");
            return null;
        }
    }
    // 解析变量声明
    public VarDeclNode parseVarDecl() {
        String bType = parseBType();
        List<VarDefNode> varDefs = new ArrayList<>();
        varDefs.add(parseVarDef());

        while (currentToken.getType() == TokenType.COMMA) {
            nextToken();
            varDefs.add(parseVarDef());
        }

        if (expect(TokenType.SEMICN)) {
            return new VarDeclNode(bType, varDefs);
        } else {
            reportError("Expected ';' at the end of variable declaration.");
            return null;
        }
    }

    public VarDefNode parseVarDef() {
        String ident = currentToken.getValue();
        nextToken();

        if (currentToken.getType() == TokenType.LBRACK) {
            nextToken();
            ConstExpNode constExp = parseConstExp();

            if (expect(TokenType.RBRACK)) {
                if (expect(TokenType.ASSIGN)) {
                    InitValNode initVal = parseInitVal();
                    return new VarDefNode(ident, constExp, initVal);
                }
                return new VarDefNode(ident, constExp, null);
            } else {
                reportError("Expected ']' in array definition.");
            }
        } else if (expect(TokenType.ASSIGN)) {
            InitValNode initVal = parseInitVal();
            return new VarDefNode(ident, null, initVal);
        }

        return new VarDefNode(ident, null, null);
    }

    public InitValNode parseInitVal() {
        if (currentToken.getType() == TokenType.LBRACE) {
            expect(TokenType.LBRACE);
            List<ExpNode> initValues = new ArrayList<>();
            if (currentToken.getType() != TokenType.RBRACE) {
                initValues.add(parseExp());
                while (currentToken.getType() == TokenType.COMMA) {
                    expect(TokenType.COMMA);
                    initValues.add(parseExp());
                }
            }
            expect(TokenType.RBRACE);
            return new InitValNode(initValues);
        } else if (currentToken.getType() == TokenType.STRCON) {
            String stringConst = currentToken.getValue();
            nextToken();
            return new InitValNode(stringConst);
        } else {
            return new InitValNode(parseExp());
        }
    }

    public FuncDefNode parseFuncDef() {
        FuncTypeNode funcType = parseFuncType();
        Token funcName = currentToken;
        expect(TokenType.IDENFR);

        expect(TokenType.LPARENT);

        FuncFParamNodes funcFParams = null;
        if (currentToken.getType() != TokenType.RPARENT) {
            funcFParams = parseFuncFParams();
        }

        expect(TokenType.RPARENT);
        BlockNode block = parseBlock();

        return new FuncDefNode(funcType, funcName, funcFParams, block);
    }

    public MainFuncDefNode parseMainFuncDef() {
        expect(TokenType.INTTK);
        expect(TokenType.MAINTK);
        expect(TokenType.LPARENT);
        expect(TokenType.RPARENT);
        BlockNode body = parseBlock();
        return new MainFuncDefNode("int", "main", body);
    }

    public FuncTypeNode parseFuncType() {
        if (currentToken.getType() == TokenType.VOIDTK || currentToken.getType() == TokenType.INTTK || currentToken.getType() == TokenType.CHARTK) {
            String returnType = currentToken.getValue();
            nextToken();
            return new FuncTypeNode(returnType);
        }
        return null;
    }

    private FuncFParamNodes parseFuncFParams() {
        List<FuncFParamNode> funcFParams = new ArrayList<>();
        funcFParams.add(parseFuncFParam());

        while (currentToken.getType() == TokenType.COMMA) {
            nextToken();
            funcFParams.add(parseFuncFParam());
        }

        return new FuncFParamNodes(funcFParams);  // 封装到 FuncFParamNodes
    }

    private FuncFParamNode parseFuncFParam() {
        String bType = parseBType();
        String ident = currentToken.getValue();
        nextToken();

        if (currentToken.getType() == TokenType.LBRACK) {
            expect(TokenType.LBRACK);
            expect(TokenType.RBRACK);
            return new FuncFParamNode(bType, ident, true);
        }

        return new FuncFParamNode(bType, ident, false);
    }

    public BlockNode parseBlock() {
        expect(TokenType.LBRACE);

        List<BlockItemNode> blockItems = new ArrayList<>();
        while (currentToken.getType() != TokenType.RBRACE) {
            blockItems.add(parseBlockItem());
        }

        expect(TokenType.RBRACE);

        return new BlockNode(blockItems);
    }

    public BlockItemNode parseBlockItem() {
        if (currentToken.getType() == TokenType.CONSTTK || currentToken.getType() == TokenType.INTTK || currentToken.getType() == TokenType.CHARTK) {
            return new BlockItemNode(parseDecl());
        } else {
            return new BlockItemNode(parseStmt());
        }
    }

    public StmtNode parseStmt() {
        if (currentToken.getType() == TokenType.LBRACE) {
            BlockNode blockNode = parseBlock();  // 解析块
            return new StmtNodeWithBlock(blockNode);  // 创建带 Block 的 StmtNode
        } else if (currentToken.getType() == TokenType.IFTK) {
            nextToken();
            expect(TokenType.LPARENT);
            CondNode cond = parseCond();
            expect(TokenType.RPARENT);
            StmtNode trueBranch = parseStmt();
            StmtNode falseBranch = null;
            if (currentToken.getType() == TokenType.ELSETK) {
                nextToken();
                falseBranch = parseStmt();
            }
            return new IfStmtNode(cond, trueBranch, falseBranch);
        } else if (currentToken.getType() == TokenType.FORTK) {
            return parseForStmt();  // 直接调用 parseForStmt
        } else if (currentToken.getType() == TokenType.BREAKTK) {
            nextToken();
            expect(TokenType.SEMICN);
            return new BreakStmtNode();
        } else if (currentToken.getType() == TokenType.CONTINUETK) {
            nextToken();
            expect(TokenType.SEMICN);
            return new ContinueStmtNode();
        } else if (currentToken.getType() == TokenType.RETURNTK) {
            nextToken();
            ExpNode returnValue = null;
            if (currentToken.getType() != TokenType.SEMICN) {
                returnValue = parseExp();
            }
            expect(TokenType.SEMICN);
            return new ReturnStmtNode(returnValue);
        } else if (currentToken.getType() == TokenType.IDENFR) {

            // 保存当前位置以便回溯
            int assignIndex = currentIndex;
            LValNode lval = parseLVal();
            if (currentToken.getType() == TokenType.ASSIGN) {
                nextToken();
                if (currentToken.getType() == TokenType.GETINTTK) {
                    nextToken();
                    expect(TokenType.LPARENT);
                    expect(TokenType.RPARENT);
                    expect(TokenType.SEMICN);
                    return new GetIntStmtNode(lval);
                } else if (currentToken.getType() == TokenType.GETCHARTK) {
                    nextToken();
                    expect(TokenType.LPARENT);
                    expect(TokenType.RPARENT);
                    expect(TokenType.SEMICN);
                    return new GetCharStmtNode(lval);
                } else {
                    ExpNode exp = parseExp();
                    expect(TokenType.SEMICN);
                    return new AssignStmtNode(lval, exp);
                }
            } else {
                // 如果没有发现赋值号，则回溯并当作 Exp 处理
                currentIndex = assignIndex;
                currentToken = tokens.get(currentIndex);
                ExpNode expNode = parseExp();
                expect(TokenType.SEMICN);
                return new ExpStmtNode(expNode);
            }
        } else if (currentToken.getType() == TokenType.PRINTFTK) {
            nextToken();
            expect(TokenType.LPARENT);
            Token stringConst = currentToken;
            expect(TokenType.STRCON);
            List<ExpNode> args = new ArrayList<>();
            while (currentToken.getType() == TokenType.COMMA) {
                nextToken();
                args.add(parseExp());
            }
            expect(TokenType.RPARENT);
            expect(TokenType.SEMICN);
            return new PrintfStmtNode(new StringConstNode(stringConst), args);
        } else {
            if (currentToken.getType() == TokenType.SEMICN) {
                nextToken();
                return new EmptyStmtNode();
            } else {
                ExpNode exp = parseExp();
                expect(TokenType.SEMICN);
                return new ExpStmtNode(exp);
            }
        }
    }

    public ForStmtNode parseForStmt() {
        //System.out.println("for " + currentToken.getLineNumber() + " "+currentToken.getValue());
        expect(TokenType.FORTK);
        expect(TokenType.LPARENT);

        LValNode initLVal = null;
        ExpNode initExp = null;
        if (currentToken.getType() == TokenType.IDENFR) {
            initLVal = parseLVal();
            if (currentToken.getType() == TokenType.ASSIGN) {
                nextToken();
                initExp = parseExp();
            }
        }
        if(currentToken.getType() == TokenType.SEMICN){
            nextToken();
        }
        //expect(TokenType.SEMICN);

        CondNode condition = null;
        if (currentToken.getType() != TokenType.SEMICN) {
            condition = parseCond();
        }
        if(currentToken.getType() == TokenType.SEMICN){
            nextToken();
        }
        //expect(TokenType.SEMICN);

        LValNode iterLVal = null;
        ExpNode iterExp = null;
        if (currentToken.getType() != TokenType.RPARENT) {
            iterLVal = parseLVal();
            if (currentToken.getType() == TokenType.ASSIGN) {
                nextToken();
                iterExp = parseExp();
            }
        }
        expect(TokenType.RPARENT);

        StmtNode body = parseStmt();

        return new ForStmtNode(initLVal, initExp, condition, iterLVal, iterExp, body);
    }

    public ExpNode parseExp() {
        AddExpNode addExpNode =  parseAddExp();  // 解析 AddExp
        return new ExpNode(addExpNode);  // 返回包含 AddExpNode 的 ExpNode
    }


    public CondNode parseCond() {
        return new CondNode(parseLOrExp());
    }

    public LValNode parseLVal() {
        String ident = currentToken.getValue();
        nextToken();

        ExpNode index = null;
        if (currentToken.getType() == TokenType.LBRACK) {
            expect(TokenType.LBRACK);
            index = parseExp();
            expect(TokenType.RBRACK);
        }

        return new LValNode(ident, index);
    }

    public PrimaryExpNode parsePrimaryExp() {
        if (currentToken.getType() == TokenType.LPARENT) {
            expect(TokenType.LPARENT);
            ExpNode exp = parseExp();
            expect(TokenType.RPARENT);
            return new PrimaryExpNode(exp);
        } else if (currentToken.getType() == TokenType.IDENFR) {
            return new PrimaryExpNode(parseLVal());
        } else if (currentToken.getType() == TokenType.INTCON) {
            return new PrimaryExpNode(parseNumber());
        } else if (currentToken.getType() == TokenType.CHRCON) {
            return new PrimaryExpNode(parseCharacter());
        }
        return null;
    }

    public NumberNode parseNumber() {
        Token number = currentToken;
        expect(TokenType.INTCON);
        return new NumberNode(number);
    }
    public CharacterNode parseCharacter() {
        Token character = currentToken;
        expect(TokenType.CHRCON);
        return new CharacterNode(character);
    }
    public UnaryExpNode parseUnaryExp() {
        if (currentToken.getType() == TokenType.IDENFR && lookAhead(1).getType() == TokenType.LPARENT) {
            //System.out.println("111 " + currentToken.getLineNumber() + " "+currentToken.getValue());
            // Ident '(' [FuncRParams] ')'
            String functionName = currentToken.getValue();
            nextToken(); // consume IDENT
            expect(TokenType.LPARENT);  // 匹配左括号
            FuncRParamsNode params = null;

            if (currentToken.getType() != TokenType.RPARENT) {
                params = parseFuncRParams();
            }

            expect(TokenType.RPARENT);  // 匹配右括号
            return new UnaryExpNode(functionName, params);
        } else if (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINU || currentToken.getType() == TokenType.NOT) {
            //System.out.println("222 " + currentToken.getLineNumber() + " "+currentToken.getValue());
            // UnaryOp UnaryExp
            UnaryOpNode op = parseUnaryOp();
            UnaryExpNode expr = parseUnaryExp();
            return new UnaryExpNode(op, expr);  // 仍然返回一元表达式节点
        } else {
            //System.out.println("333 " + currentToken.getLineNumber() + " "+currentToken.getValue());
            // PrimaryExp
            PrimaryExpNode primary = parsePrimaryExp();
            return new UnaryExpNode(primary);
        }
    }
    public UnaryOpNode parseUnaryOp() {
        if (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINU || currentToken.getType() == TokenType.NOT) {
            Token operator = currentToken;
            nextToken();  // 消费掉运算符
            return new UnaryOpNode(operator);
        }
        return null;
    }
    public FuncRParamsNode parseFuncRParams() {
        List<ExpNode> expressions = new ArrayList<>();
        expressions.add(parseExp());

        while (currentToken.getType() == TokenType.COMMA) {
            nextToken();  // 消费逗号
            expressions.add(parseExp());
        }

        return new FuncRParamsNode(expressions);
    }

    public AddExpNode parseAddExp() {
        //System.out.println("add " + currentToken.getLineNumber() + " "+currentToken.getValue());
        MulExpNode mulExpNode = parseMulExp(); // 先解析乘法表达式
        Token operator = null;
        AddExpNode addExpNode = null;
        if (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINU) {
            operator = currentToken;
            nextToken();
            addExpNode = parseAddExp();
        }

        return new AddExpNode(mulExpNode, operator, addExpNode);
    }

    public MulExpNode parseMulExp() {
        UnaryExpNode unaryExpNode = parseUnaryExp();  // 先解析一元表达式
        Token operator = null;
        MulExpNode mulExpNode = null;

        if (currentToken.getType() == TokenType.MULT || currentToken.getType() == TokenType.DIV || currentToken.getType() == TokenType.MOD) {
            operator = currentToken;  // 保存操作符
            nextToken();  // 跳过操作符
            mulExpNode = parseMulExp();  // 递归解析剩余的乘除模表达式
        }

        return new MulExpNode(unaryExpNode, operator, mulExpNode);
    }

    public RelExpNode parseRelExp() {
        AddExpNode addExpNode = parseAddExp();  // 先解析加法表达式
        Token operator = null;
        RelExpNode relExpNode = null;

        if (currentToken.getType() == TokenType.LSS || currentToken.getType() == TokenType.GRE
                || currentToken.getType() == TokenType.LEQ || currentToken.getType() == TokenType.GEQ) {
            operator = currentToken;  // 保存操作符
            nextToken();  // 跳过操作符
            relExpNode = parseRelExp();  // 递归解析关系表达式
        }

        return new RelExpNode(addExpNode, operator, relExpNode);
    }


    public EqExpNode parseEqExp() {
        RelExpNode relExpNode = parseRelExp();  // 先解析关系表达式
        Token operator = null;
        EqExpNode eqExpNode = null;

        if (currentToken.getType() == TokenType.EQL || currentToken.getType() == TokenType.NEQ) {
            operator = currentToken;  // 保存操作符
            nextToken();  // 跳过操作符
            eqExpNode = parseEqExp();  // 递归解析相等性表达式
        }

        return new EqExpNode(relExpNode, operator, eqExpNode);
    }

    public LOrExpNode parseLOrExp() {
        LAndExpNode landExpNode = parseLAndExp();  // 先解析逻辑与表达式
        Token operator = null;
        LOrExpNode lorExpNode = null;

        if (currentToken.getType() == TokenType.OR) {  // 检查是否为 '||'
            operator = currentToken;  // 保存操作符
            nextToken();  // 跳过 '||'
            lorExpNode = parseLOrExp();  // 递归解析逻辑或表达式
        }

        return new LOrExpNode(landExpNode, operator, lorExpNode);
    }

    public LAndExpNode parseLAndExp() {
        EqExpNode eqExpNode = parseEqExp();  // 先解析相等性表达式
        Token operator = null;
        LAndExpNode landExpNode = null;

        if (currentToken.getType() == TokenType.AND) {  // 检查是否为 '&&'
            operator = currentToken;  // 保存操作符
            nextToken();  // 跳过 '&&'
            landExpNode = parseLAndExp();  // 递归解析逻辑与表达式
        }

        return new LAndExpNode(eqExpNode, operator, landExpNode);
    }

    public ConstExpNode parseConstExp() {
        // 解析并返回一个 AddExp，封装成 ConstExpNode
        AddExpNode addExp = parseAddExp();
        return new ConstExpNode(addExp);
    }

    // 匹配某个特定类型的token
    private boolean expect(TokenType expectedType) {
        if (currentToken.getType() == expectedType) {
            nextToken();
            return true;
        } else if (expectedType == TokenType.SEMICN) {
            addError(new LexicalError(tokens.get(currentIndex - 1).getLineNumber(), tokens.get(currentIndex - 1).getColumnNumber(),"i"));
            return true;
        } else if (expectedType == TokenType.RPARENT) {
            addError(new LexicalError(tokens.get(currentIndex - 1).getLineNumber(), tokens.get(currentIndex - 1).getColumnNumber(),"j"));
            return true;
        } else if (expectedType == TokenType.RBRACK) {
            addError(new LexicalError(tokens.get(currentIndex - 1).getLineNumber(), tokens.get(currentIndex - 1).getColumnNumber(),"k"));
            return true;
        } else {
            //reportError("Expected " + expectedType + " but found " + currentToken.getType() + " at " + currentIndex + 1 );
            return false;
        }
    }

    // 写入内容到文件
    public void write(String content) throws IOException {
        writer.write(content);
        writer.newLine();  // 换行
    }


    public void addError(LexicalError newError){

        for (LexicalError error : errors) {
            if (error.getLineNumber() == newError.getLineNumber()) {
                return;
            }
        }

        errors.add(newError);
    }

    public void sortErrorsByLineNumber() {
        Collections.sort(errors, new Comparator<LexicalError>() {
            @Override
            public int compare(LexicalError e1, LexicalError e2) {
                return Integer.compare(e1.getLineNumber(), e2.getLineNumber());
            }
        });
    }
    public void writeErrors(String errorFilePath) throws IOException {
        sortErrorsByLineNumber();
        BufferedWriter writer = new BufferedWriter(new FileWriter(errorFilePath));
        for (LexicalError error : errors) {
            writer.write(error.getLineNumber() + " " + error.getErrorCode());
            writer.newLine();
        }
        writer.close();
    }

    // 关闭文件
    public void closeWriter() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
    // 输出解析结果
    public void printParseAns() throws IOException {
        compUnitNode.printNode(this);  // 将 this 传入以便所有节点可以调用 write 方法
        closeWriter();  // 关闭文件
    }
}