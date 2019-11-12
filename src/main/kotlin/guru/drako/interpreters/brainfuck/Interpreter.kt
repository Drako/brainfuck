package guru.drako.interpreters.brainfuck

object Interpreter {
  fun interpret(program: String): String {
    val rawAst = program.asSequence()
      .mapNotNull { Token.fromChar(it) }
      .fold(Parser()) { parser, token -> parser.parse(token) }
      .finish()

    val optimized = rawAst.commands
      .fold(ScopeOptimizer()) { optimizer, command -> optimizer.process(command) }
      .finish()

    return optimized(State()).output
  }
}
