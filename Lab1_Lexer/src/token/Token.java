package token;
public abstract class Token {
    protected String literal;
    protected TokenType type;
    protected int line;
    protected int position;

    public Token(String literal, TokenType type, int line, int position) {
        this.literal = literal;
        this.type = type;
        this.line = line;
        this.position = position;
    }

    public String getLiteral() {
        return literal;
    }

    public TokenType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }
}