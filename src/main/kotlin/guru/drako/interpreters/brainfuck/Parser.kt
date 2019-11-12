package guru.drako.interpreters.brainfuck

data class Parser(val scope: Scope = Scope(), val stack: List<Scope> = listOf()) {
  fun parse(token: Token): Parser {
    return when (token) {
      Token.PLUS -> copy(scope = Scope(commands = scope.commands + ModifyValue(delta = 1)))
      Token.MINUS -> copy(scope = Scope(commands = scope.commands + ModifyValue(delta = -1)))
      Token.GREATER -> copy(scope = Scope(commands = scope.commands + ModifyPointer(delta = 1)))
      Token.LESS -> copy(scope = Scope(commands = scope.commands + ModifyPointer(delta = -1)))
      Token.DOT -> copy(scope = Scope(commands = scope.commands + PrintValue))
      Token.LBRACKET -> Parser(scope = Scope(), stack = stack + scope)
      Token.RBRACKET -> Parser(
        scope = (stack.lastOrNull() ?: throw IllegalStateException("unexpected closing bracket")).run {
          copy(commands = commands + Loop(scope))
        },
        stack = stack.dropLast(1)
      )
    }
  }

  fun finish(): Scope {
    check(stack.isEmpty()) { "missing closing bracket" }

    return scope
  }
}
