package token;

public class LexerToken extends Token {
    private int endPosition;

    public LexerToken(String literal, TokenType type, int line, int position, int endPosition) {
        super(literal, type, line, position);
        this.endPosition = endPosition;
    }
    public int getEndPosition() {
        return endPosition;
    }

    public boolean isNotAuxiliaryToken() {
        return !type.isAuxiliaryToken();
    }

    @Override
    public String toString() {
        if (isNotAuxiliaryToken()) {
            return type + " '" + literal.trim() + "' [Line: " + line + ", Position: " + position + "]";
        } else {
            return type + " [Line: " + line + ", Position: " + position + "]";
        }
    }
}

