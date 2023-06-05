package token;
import java.util.Map;
import java.util.HashMap;

public class TokenStateManager {
    public static final int WORD_LIMIT = 5; //
    private final Map<LexerState, StateProcess> states = new HashMap<>(4);

    public TokenStateManager() {
        states.put(LexerState.START, new StartState());
        states.put(LexerState.WHITESPACE, new WhitespaceState());
        states.put(LexerState.INTOKEN, new InTokenState());
        states.put(LexerState.NEWLINE, new NewLineState());
    }
    public Map<LexerState, StateProcess> getStates() {
        return states;
    }

    public static class StartState extends StateProcess {
        public LexerState next(String ch) {
            return ch.equals("\n") ? LexerState.NEWLINE : LexerState.WHITESPACE;
        }
    }

    public static class WhitespaceState extends StateProcess {
        public LexerState next(String ch) {
            boolean notWhitespace = !ch.equals(" ");
            LexerState next = notWhitespace ? LexerState.INTOKEN : LexerState.WHITESPACE;
            return ch.equals("\n") ? LexerState.NEWLINE : next;
        }
    }
    public static class InTokenState extends StateProcess {
        public LexerState next(String ch) {
            if (ch.equals("\n")) {
                return LexerState.NEWLINE;
            }
            if (ch.equals(" ")) {
                whitespacesCount++;
                return LexerState.WHITESPACE;
            }
            return LexerState.INTOKEN;
        }
    }

    public static class NewLineState extends StateProcess {
        public LexerState next(String ch) {
            return null;
        }
    }
    public void reset() {
        states.forEach((key, value) -> value.closeState());
    }
}
