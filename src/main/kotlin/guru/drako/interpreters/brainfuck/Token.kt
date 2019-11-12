package guru.drako.interpreters.brainfuck

enum class Token(val character: Char) {
  PLUS('+'),
  MINUS('-'),
  GREATER('>'),
  LESS('<'),
  RBRACKET(']'),
  LBRACKET('['),
  DOT('.');

  companion object {
    fun fromChar(c: Char): Token? =
      values().firstOrNull { it.character == c }
  }
}
