package parser;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:11:47
 */

enum Token {
    // Expression borders
    BEGIN,
    END,

    // Brackets
    CLOSING_BRACKET,
    OPENING_BRACKET,

    // Variables
    CONST,
    VARIABLE,

    // Binary operations
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,

    // Unary operations
    NEGATE,
    LOG2,
    POW2,
    LOG,
    POW,
    HIGH,
    LOW,
}
