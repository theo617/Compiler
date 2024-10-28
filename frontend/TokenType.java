package frontend;

public enum TokenType {
    // 标识符和常量
    IDENFR,      // Ident 标识符
    INTCON,      // IntConst 整型常量
    STRCON,      // StringConst 字符串常量
    CHRCON,      // CharConst 字符常量

    // 关键字
    MAINTK,      // main
    CONSTTK,     // const
    INTTK,       // int
    CHARTK,      // char
    BREAKTK,     // break
    CONTINUETK,  // continue
    IFTK,        // if
    ELSETK,      // else
    VOIDTK,      // void
    FORTK,       // for
    GETINTTK,    // getint
    GETCHARTK,   // getchar
    PRINTFTK,    // printf
    RETURNTK,    // return

    // 运算符和分隔符
    NOT,         // !
    AND,         // &&
    OR,          // ||
    PLUS,        // +
    MINU,        // -
    MULT,        // *
    DIV,         // /
    MOD,         // %
    ASSIGN,      // =
    EQL,         // ==
    NEQ,         // !=
    LSS,         // <
    LEQ,         // <=
    GRE,         // >
    GEQ,         // >=

    // 分隔符和括号
    SEMICN,      // ;
    COMMA,       // ,
    LPARENT,     // (
    RPARENT,     // )
    LBRACK,      // [
    RBRACK,      // ]
    LBRACE,      // {
    RBRACE,      // }

    // 特殊符号
    ERROR,       // 错误
    EOF          // 文件结束
}
