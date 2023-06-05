package token;

public enum TokenType {
    // Operators
    ADDITION,               // Addition operator (+)
    AND,                    // Logical AND operator (&&)
    ASSIGNMENT,             // Assignment operator (=)
    DIVISION,               // Division operator (/)
    EQUALS,                 // Equality operator (==)
    LESS,                   // Less than operator (<)
    MORE,                   // Greater than operator (>)
    MULTIPLICATION,         // Multiplication operator (*)
    NOT_EQUALS,             // Not equals operator (!=)
    OR,                     // Logical OR operator (||)
    SUBTRACTION,            // Subtraction operator (-)
    XOR,                    // Exclusive OR operator (^)
    INCREMENT,              // The increment operator (e.g. "++")
    DECREMENT,              //Decrement operator (for example, "--")
    MODULO,                 //Overtime operator ("%")
    POWER,                  // Power-up operator ("**")

    // Logical operator
    LOGICAL_AND,            //Logical "i" operator (verbally represented, for example, "and")
    LOGICAL_OR,             // Logical "or" operator (verbally represented, for example, "or")
    LOGICAL_NOT,            //Logical list operator (verbal representation, e.g., "not")


    // Punctuation marks
    CLOSING_BRACE,          // Closing brace (})
    CLOSING_PAREN,          // Closing parenthesis ())
    CLOSING_CURLY_BRACE,    // Closing curly brace (})
    CLOSING_SQUARE_BRACE,   // Closing square bracket (])
    COLON,                  // Colon (:)
    COMMA,                  // Comma (,)
    OPENING_BRACE,          // Opening brace ({)
    OPENING_CURLY_BRACE,    // Opening curly brace ({)
    OPENING_SQUARE_BRACE,   // Opening square bracket ([)
    POINT,                  // Period (.)
    SEMICOLON,          // Semicolon (;)


    // Literals
    BINARY_LITERAL,         // Binary literal (e.g., 1010)
    CHAR_LITERAL,           // Character literal (e.g., 'a')
    DOUBLE_LITERAL,         // Double literal (e.g., 3.14)
    HEX_LITERAL,            // Hexadecimal literal (e.g., 0xFF)
    INT_LITERAL,            // Integer literal (e.g., 42)
    SCIENTIFIC_LITERAL,     // Scientific notation literal (e.g., 1.5E10)
    STRING_LITERAL,         // String literal (e.g., "Hello, World!")

    // Data types
    BOOLEAN,                // Boolean data type
    CHAR,                   // Character data type
    DOUBLE,                 // Double data type
    INT,                    // Integer data type

    // AuxiliaryToken
    TAB,                    // Tab character
    WHITE_SPACE,            // White space
    NEW_LINE,               // New line

    //error type
    DIVISION_BY_ZERO,
    TYPE_MISMATCH,
    INVALID_EXPRESSION,
    UNCLOSED_BLOCK_COMMENT,

    // Other tokens
    IDENTIFIER,             // Identifier (variable name, function name, etc.)
    KEYWORD,                // Keyword (e.g., "if", "while")
    LINE_COMMENT,           // Line comment
    OCTAL_LITERAL,          // Octal literal (e.g., 0123)
    STRING;                 // String data type

    public boolean isAuxiliaryToken() {
        if ( this == TAB || this == WHITE_SPACE || this == NEW_LINE) return true;

        return false;
    }

    public boolean isErrorType() {
        return this == DIVISION_BY_ZERO
                || this == TYPE_MISMATCH
                || this == INVALID_EXPRESSION
                || this == UNCLOSED_BLOCK_COMMENT;
    }

}
