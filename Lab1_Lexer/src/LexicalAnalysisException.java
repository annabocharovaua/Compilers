
import token.TokenType;
import token.TokenStateManager;
import token.StateProcess;
import token.LexerState;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LexicalAnalysisException extends Exception {
    private TokenStateManager stateManager = new TokenStateManager();
    private String message;

    public LexicalAnalysisException(String source, int position, int line, TokenType errorType) {
        String substring = source.substring(
                position - findStartOfToken(source, position),
                position + findEndOfToken(source, position)
        ).trim();
        this.message = "You have an error in your syntax near ";
        handleError(substring, line, position, errorType);
    }



    private static final Logger LOGGER = Logger.getLogger(LexicalAnalysisException.class.getName());

    private void printErrorMessage(String message, int line, int position) {
        String separator = "--------------------------------------------------------------------------------->";
        String logMessage = String.format("LINE: %d POSITION: %d  ------------------->  %s", line, position, message);

        LOGGER.log(Level.SEVERE, this.message);
        LOGGER.log(Level.SEVERE, separator);
        LOGGER.log(Level.SEVERE, logMessage);
        LOGGER.log(Level.SEVERE, separator);
    }

    private void handleError(String substring, int line, int position, TokenType errorType) {
        String errorMessage;
        switch (errorType) {
            case DIVISION_BY_ZERO:
                errorMessage = "You have a division by zero error near " + substring;
                break;
            case TYPE_MISMATCH:
                errorMessage = "You have a type mismatch error near " + substring;
                break;
            case INVALID_EXPRESSION:
                errorMessage = "You have an invalid expression error near " + substring;
                break;
            case UNCLOSED_BLOCK_COMMENT:
                errorMessage = "You have an unclosed block comment error near " + substring;
                break;
            default:
                errorMessage = "An error occurred near " + substring;
                break;
        }
        printErrorMessage(errorMessage, line, position);
    }

    private int findStartOfToken(String source, int position) {
        int index = 0;
        StateProcess state = stateManager.getStates().get(LexerState.START);

        while (position - index != 0 && state != null &&
                state.getWhitespacesCount() != stateManager.WORD_LIMIT &&
                state != stateManager.getStates().get(LexerState.NEWLINE)) {
            index++;

            String ch = Character.toString(source.charAt(position - index));
            LexerState property = state.next(ch);

            if (property != null) {
                state = stateManager.getStates().get(property);
            }
        }
        stateManager.reset();
        return index;
    }

    private int findEndOfToken(String source, int position) {
        int index = 0;
        StateProcess state = stateManager.getStates().get(LexerState.START);
        while (
                position + index != source.length() - 1 &&
                        state.getWhitespacesCount() != stateManager.WORD_LIMIT &&
                        state != stateManager.getStates().get(LexerState.NEWLINE)
        ) {
            index++;

            String ch = Character.toString(source.charAt(position + index));
            LexerState property = state.next(ch);

            if (property != null) {
                state = stateManager.getStates().get(property);
            }
        }
        stateManager.reset();
        return index;
    }
}