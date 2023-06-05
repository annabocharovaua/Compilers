import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import token.LexerToken;
import token.Token;
import token.TokenType;

public class Lexer {
    private Map<TokenType, String> regularExpressions;
    private List<LexerToken> result;
    public Lexer(){
        result = new ArrayList<>();
        regularExpressions = new HashMap<>();
        // Operators
        regularExpressions.put(TokenType.ADDITION, "(\\+{1}).*");
        regularExpressions.put(TokenType.AND, "(&&).*");
        regularExpressions.put(TokenType.ASSIGNMENT, "(=).*");
        regularExpressions.put(TokenType.DIVISION, "(/).*");
        regularExpressions.put(TokenType.EQUALS, "(==).*");
        regularExpressions.put(TokenType.LESS, "(<).*");
        regularExpressions.put(TokenType.MORE, "(>).*");
        regularExpressions.put(TokenType.MULTIPLICATION, "(\\*).*");
        regularExpressions.put(TokenType.NOT_EQUALS, "(\\!=).*");
        regularExpressions.put(TokenType.OR, "(\\|\\|).*");
        regularExpressions.put(TokenType.SUBTRACTION, "(\\-{1}).*");
        regularExpressions.put(TokenType.XOR, "(\\^).*");
        regularExpressions.put(TokenType.INCREMENT, "(\\+{2}).*");
        regularExpressions.put(TokenType.DECREMENT, "(\\-{2}).*");
        regularExpressions.put(TokenType.MODULO, "(%).*");
        regularExpressions.put(TokenType.POWER, "(\\*{2}).*");
        
        // Logical operator
        regularExpressions.put(TokenType.LOGICAL_AND, "(and).*");
        regularExpressions.put(TokenType.LOGICAL_OR, "(or).*");
        regularExpressions.put(TokenType.LOGICAL_NOT, "(not).*");

        // Punctuation marks
        regularExpressions.put(TokenType.CLOSING_BRACE, "(\\}).*");
        regularExpressions.put(TokenType.CLOSING_PAREN, "(\\)).*");
        regularExpressions.put(TokenType.CLOSING_CURLY_BRACE, "(\\}).*");
        regularExpressions.put(TokenType.CLOSING_SQUARE_BRACE, "(\\]).*");
        regularExpressions.put(TokenType.COLON, "(:).*");
        regularExpressions.put(TokenType.COMMA, "(,).*");
        regularExpressions.put(TokenType.OPENING_BRACE, "(\\{).*");
        regularExpressions.put(TokenType.OPENING_CURLY_BRACE, "(\\{).*");
        regularExpressions.put(TokenType.OPENING_SQUARE_BRACE, "(\\[).*");
        regularExpressions.put(TokenType.POINT, "(\\.).*");
        regularExpressions.put(TokenType.SEMICOLON, "(;).*");


        // Literals
        regularExpressions.put(TokenType.BINARY_LITERAL, "\\b(0[bB][01]+)\\b.*");
        regularExpressions.put(TokenType.CHAR_LITERAL, "('(.{1}|\\\\n|\\\\t)').*");
        regularExpressions.put(TokenType.DOUBLE_LITERAL, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regularExpressions.put(TokenType.HEX_LITERAL, "\\b(0[xX][0-9a-fA-F]+)\\b.*");
        regularExpressions.put(TokenType.INT_LITERAL, "\\b(\\d{1,9})\\b.*");
        regularExpressions.put(TokenType.SCIENTIFIC_LITERAL, "\\b([+\\-]?(?:0|[1-9]\\d*)(?:\\.\\d*)?(?:[eE][+\\-]?\\d+))\\b.*");
        regularExpressions.put(TokenType.STRING_LITERAL, "(\\\"([^\\\\\\\"]|\\\\.)*\\\").*");

        // Data types
        regularExpressions.put(TokenType.BOOLEAN, "\\b(boolean)\\b.*");
        regularExpressions.put(TokenType.CHAR, "\\b(char)\\b.*");
        regularExpressions.put(TokenType.DOUBLE, "\\b(double)\\b.*");
        regularExpressions.put(TokenType.INT, "\\b(int)\\b.*");

        //Error type
        regularExpressions.put(TokenType.DIVISION_BY_ZERO, "/\\s*0\\s*/"); // Регулярний вираз для ділення на нуль
        regularExpressions.put(TokenType.TYPE_MISMATCH, "((?:\\bint\\b)|(?:\\bfloat\\b))\\s*\\+\\s*((?:\\bstring\\b)|(?:\\bboolean\\b))"); // Регулярний вираз для невідповідності типів
        regularExpressions.put(TokenType.INVALID_EXPRESSION, "((?:\\bint\\b)|(?:\\bfloat\\b))\\s*\\+\\s*\\((?:\\bstring\\b)|(?:\\bboolean\\b))\\)"); // Регулярний вираз для некоректного виразу
        regularExpressions.put(TokenType.UNCLOSED_BLOCK_COMMENT, "/\\*.*"); // Регулярний вираз для незакритого блоку коментаря

        // AuxiliaryToken
        regularExpressions.put(TokenType.TAB, "(\\t).*");
        regularExpressions.put(TokenType.WHITE_SPACE, "( ).*");
        regularExpressions.put(TokenType.NEW_LINE, "(\\n).*");
        regularExpressions.put(TokenType.IDENTIFIER, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
        regularExpressions.put(TokenType.KEYWORD, "\\b(await|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|false|finally|for|function|if|implements|import|in|instanceof|interface|let|new|null|package|private|protected|public|return|super|switch|static|this|throw|try|true|typeof|var|void|while|with|yield)\\b.*");
        regularExpressions.put(TokenType.LINE_COMMENT, "(//.*?)[\\r$]?\\n.*");
        regularExpressions.put(TokenType.OCTAL_LITERAL, "\\b(0[0-7]+)\\b.*");
        regularExpressions.put(TokenType.STRING, "\\b(string)\\b.*");
        
    }

    public List<Token> tokenize(String source) throws LexicalAnalysisException {
        int position = 0;
        LexerToken token = null;
        LexerToken prevToken = null;
        boolean hasError = false;

        do {
            token = this.getNextToken(source, position);

            if (token != null) {
                position = token.getEndPosition();

                if (prevToken != null && prevToken.getType() == TokenType.DIVISION && token.getType() == TokenType.INT_LITERAL) {
                    String literal = token.getLiteral();
                    if (literal.equals("0")) {
                        throw new LexicalAnalysisException(source, prevToken.getPosition(), prevToken.getLine(), TokenType.DIVISION_BY_ZERO);
                    }
                }

                this.result.add(token);

                if (token.getType().isErrorType()) {
                    hasError = true;
                }

                prevToken = token;
            }
        } while (token != null && position != source.length());

        if (position != source.length() || hasError) {
            throw new LexicalAnalysisException(source, position, getLineNumber(source, position), token.getType());
        }

        return this.getFilteredTokens();
    }


    private List<Token> getFilteredTokens() {
        return result.stream()
                .filter(token -> token.isNotAuxiliaryToken())
                .collect(Collectors.toList());
    }

    private LexerToken getNextToken(String source, int startIndex) throws LexicalAnalysisException {
        if (startIndex < 0 || startIndex >= source.length()) {
            throw new IndexOutOfBoundsException("Invalid starting index");
        }

        for (Map.Entry<TokenType, String> entry : this.regularExpressions.entrySet()) {
            TokenType tokenType = entry.getKey();
            String patternString = entry.getValue();
            Pattern pattern = Pattern.compile("^" + patternString + "$", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source.substring(startIndex));

            if (matcher.find()) {
                String literal = matcher.group(1);
                int line = getLineNumber(source, startIndex);
                int position = getPositionInLine(source, line, startIndex);
                int endPosition = startIndex + literal.length();
                int i = 1;

                LexerToken nextToken = new LexerToken(literal, tokenType, line, position, endPosition);


                return nextToken;
            }
        }

        return null;
    }




    private int getLineNumber(String source, int from) {
           return source.substring(0, from + 1).split("\n").length;
    }
    private int getPositionInLine(String source, int line, int from) {
       LineIterator scanner = new LineIterator(source);
       int index = 0;
       int pos = from;
       while (scanner.hasNextLine() && index != line - 1) {
           String skipped = scanner.readNextLine();
           pos -= skipped.length() + 1;
           index++;
       }
       return pos;
    }
}