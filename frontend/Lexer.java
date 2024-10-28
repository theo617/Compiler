package frontend;

import java.io.*;
import java.util.*;

public class Lexer {
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "main", "const", "int", "char", "break", "continue", "if", "else", "void",
            "for", "getint", "getchar", "printf", "return"
    ));



    private List<Token> tokens; // 词法分析结果
    private List<LexicalError> errors;
    private BufferedReader reader;
    private int lineNumber;
    private int columnNumber;

    public Lexer(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        lineNumber = 1;
        columnNumber = 1;
    }

    public void analyze() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            processLine(line);
            lineNumber++;
            columnNumber = 1;
        }
        reader.close();
    }

    private void processLine(String line) {
        int i = 0;
        while (i < line.length()) {
            char currentChar = line.charAt(i);

            if (Character.isWhitespace(currentChar)) {
                i++;
                columnNumber++;
                continue;
            }

            // 字符串常量处理逻辑
            if (currentChar == '"') {
                int startCol = columnNumber;
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(currentChar);  // 添加开头的双引号
                i++;
                columnNumber++;

                // 读取字符串中的内容，直到遇到下一个双引号
                while (i < line.length() && line.charAt(i) != '"') {
                    // 如果遇到换行符，则说明字符串未闭合，记录错误
                    if (line.charAt(i) == '\n') {
                        errors.add(new LexicalError(lineNumber, startCol, "Unterminated string constant"));
                        return;
                    }
                    strBuilder.append(line.charAt(i));
                    i++;
                    columnNumber++;
                }

                // 检查是否遇到了闭合的双引号
                if (i < line.length() && line.charAt(i) == '"') {
                    strBuilder.append('"');  // 添加闭合的双引号
                    i++;
                    columnNumber++;
                    tokens.add(new Token(TokenType.STRCON, strBuilder.toString(), lineNumber, startCol));
                } else {
                    // 如果没有找到闭合的双引号，记录错误
                    errors.add(new LexicalError(lineNumber, startCol, "Unterminated string constant"));
                }
                continue;
            }


            // 修改字符常量的处理逻辑
            if (currentChar == '\'') {
                int startCol = columnNumber;
                StringBuilder charConstant = new StringBuilder();
                charConstant.append(currentChar);  // 添加开头的单引号
                i++;
                columnNumber++;

                // 检查字符是否为有效字符（包括转义字符）
                if (i < line.length()) {
                    char nextChar = line.charAt(i);

                    // 处理转义字符
                    if (nextChar == '\\') {
                        charConstant.append(nextChar);
                        i++;
                        columnNumber++;

                        if (i < line.length()) {
                            char escapeChar = line.charAt(i);
                            if (escapeChar == 'n' || escapeChar == 't' || escapeChar == 'a' || escapeChar == 'b' ||
                                    escapeChar == 'f' || escapeChar == 'r' || escapeChar == 'v' || escapeChar == '0' ||
                                    escapeChar == '\\' || escapeChar == '\'' || escapeChar == '\"') {
                                charConstant.append(escapeChar);
                                i++;
                                columnNumber++;
                            } else {
                                // 非法转义字符
                                errors.add(new LexicalError(lineNumber, startCol, "Invalid escape sequence in character constant"));
                                continue;
                            }
                        } else {
                            errors.add(new LexicalError(lineNumber, startCol, "Unterminated escape sequence in character constant"));
                            continue;
                        }
                    } else if (nextChar != '\'' && nextChar != '\n') {
                        // 普通字符常量
                        charConstant.append(nextChar);
                        i++;
                        columnNumber++;
                    } else {
                        // 非法字符常量
                        errors.add(new LexicalError(lineNumber, startCol, "Invalid character in character constant"));
                        continue;
                    }

                    // 检查是否有闭合的单引号
                    if (i < line.length() && line.charAt(i) == '\'') {
                        charConstant.append('\'');  // 添加闭合的单引号
                        i++;
                        columnNumber++;
                        tokens.add(new Token(TokenType.CHRCON, charConstant.toString(), lineNumber, startCol));
                    } else {
                        errors.add(new LexicalError(lineNumber, startCol, "Unterminated character constant"));
                    }
                } else {
                    // 单引号后无字符
                    errors.add(new LexicalError(lineNumber, startCol, "Unterminated character constant"));
                }
                continue;
            }

            if (currentChar == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                // 遇到单行注释符号，跳过这一行
                break;
            }
            if (currentChar == '/' && i + 1 < line.length() && line.charAt(i + 1) == '*') {
                // 处理多行注释
                i += 2; // 跳过 "/*"
                columnNumber += 2;

                boolean commentClosed = false;

                while (!commentClosed) {
                    while (i < line.length()) {
                        if (line.charAt(i) == '*' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                            // 找到注释的结束符
                            i += 2; // 跳过 "*/"
                            columnNumber += 2;
                            commentClosed = true;
                            break;
                        }
                        i++;
                        columnNumber++;
                    }

                    if (!commentClosed) {
                        // 如果没有找到 "*/"，读取下一行并继续处理注释
                        try {
                            line = reader.readLine(); // 读取下一行
                            if (line == null) {
                                // 如果文件结束且注释未闭合，记录错误
                                errors.add(new LexicalError(lineNumber, columnNumber, "Unterminated block comment"));
                                return;
                            }
                            i = 0; // 重置列索引
                            lineNumber++; // 行号加1
                            columnNumber = 1; // 列号重置为1
                        } catch (IOException e) {
                            errors.add(new LexicalError(lineNumber, columnNumber, "Error reading line: " + e.getMessage()));
                            return;
                        }
                    }
                }

                continue; // 继续处理下一部分
            }




            if (Character.isLetter(currentChar) || currentChar == '_') {
                int startCol = columnNumber;
                StringBuilder identifier = new StringBuilder();
                while (i < line.length() && (Character.isLetterOrDigit(line.charAt(i)) || line.charAt(i) == '_')) {
                    identifier.append(line.charAt(i));
                    i++;
                    columnNumber++;
                }
                String word = identifier.toString();
                if (KEYWORDS.contains(word)) {
                    tokens.add(new Token(getKeywordTokenType(word), word, lineNumber, startCol));
                } else {
                    tokens.add(new Token(TokenType.IDENFR, word, lineNumber, startCol));
                }
                continue;
            }

            if (Character.isDigit(currentChar)) {
                int startCol = columnNumber;
                StringBuilder number = new StringBuilder();
                while (i < line.length() && Character.isDigit(line.charAt(i))) {
                    number.append(line.charAt(i));
                    i++;
                    columnNumber++;
                }
                tokens.add(new Token(TokenType.INTCON, number.toString(), lineNumber, startCol));
                continue;
            }

            switch (currentChar) {
                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", lineNumber, columnNumber));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINU, "-", lineNumber, columnNumber));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.MULT, "*", lineNumber, columnNumber));
                    break;
                case '/':
                    tokens.add(new Token(TokenType.DIV, "/", lineNumber, columnNumber));
                    break;
                case '%':
                    tokens.add(new Token(TokenType.MOD, "%", lineNumber, columnNumber));
                    break;
                case '=':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.EQL, "==", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.ASSIGN, "=", lineNumber, columnNumber));
                    }
                    break;
                case '!':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.NEQ, "!=", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.NOT, "!", lineNumber, columnNumber));
                    }
                    break;
                case '<':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.LEQ, "<=", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.LSS, "<", lineNumber, columnNumber));
                    }
                    break;
                case '>':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.GEQ, ">=", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.GRE, ">", lineNumber, columnNumber));
                    }
                    break;
                case ';':
                    tokens.add(new Token(TokenType.SEMICN, ";", lineNumber, columnNumber));
                    break;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ",", lineNumber, columnNumber));
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPARENT, "(", lineNumber, columnNumber));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPARENT, ")", lineNumber, columnNumber));
                    break;
                case '{':
                    tokens.add(new Token(TokenType.LBRACE, "{", lineNumber, columnNumber));
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RBRACE, "}", lineNumber, columnNumber));
                    break;
                case '[':
                    tokens.add(new Token(TokenType.LBRACK, "[", lineNumber, columnNumber));
                    break;
                case ']':
                    tokens.add(new Token(TokenType.RBRACK, "]", lineNumber, columnNumber));
                    break;
                case '&':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '&') {
                        tokens.add(new Token(TokenType.AND, "&&", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.AND, "&&", lineNumber, columnNumber));
                        errors.add(new LexicalError(lineNumber, columnNumber, "a"));  // 错误类别 a
                    }
                    break;
                case '|':
                    if (i + 1 < line.length() && line.charAt(i + 1) == '|') {
                        tokens.add(new Token(TokenType.OR, "||", lineNumber, columnNumber));
                        i++;
                        columnNumber++;
                    } else {
                        tokens.add(new Token(TokenType.OR, "||", lineNumber, columnNumber));
                        errors.add(new LexicalError(lineNumber, columnNumber, "a"));  // 错误类别 a
                    }
                    break;
                default:
                    errors.add(new LexicalError(lineNumber, columnNumber, "Unrecognized symbol: " + currentChar));
            }
            i++;
            columnNumber++;
        }
    }

    private TokenType getKeywordTokenType(String keyword) {
        switch (keyword) {
            case "main":
                return TokenType.MAINTK;
            case "const":
                return TokenType.CONSTTK;
            case "int":
                return TokenType.INTTK;
            case "char":
                return TokenType.CHARTK;
            case "break":
                return TokenType.BREAKTK;
            case "continue":
                return TokenType.CONTINUETK;
            case "if":
                return TokenType.IFTK;
            case "else":
                return TokenType.ELSETK;
            case "void":
                return TokenType.VOIDTK;
            case "for":
                return TokenType.FORTK;
            case "getint":
                return TokenType.GETINTTK;
            case "getchar":
                return TokenType.GETCHARTK;
            case "printf":
                return TokenType.PRINTFTK;
            case "return":
                return TokenType.RETURNTK;
            default:
                return TokenType.ERROR;
        }
    }

    public void writeTokens(String outputFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        for (Token token : tokens) {
            writer.write(token.getType() + " " + token.getValue());
            writer.newLine();
        }
        writer.close();
    }

    public void writeErrors(String errorFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(errorFilePath));
        for (LexicalError error : errors) {
            writer.write(error.getLineNumber() + " " + error.getErrorCode());
            writer.newLine();
        }
        writer.close();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<LexicalError> getErrors() {
        return errors;
    }
}
