package guru.drako.interpreters.brainfuck

data class ScopeOptimizer(val prevCommand: Command? = null, val scope: Scope = Scope()) {
  fun process(command: Command): ScopeOptimizer {
    return when {
      prevCommand == null -> copy(prevCommand = command)
      prevCommand is ModifyValue && command is ModifyValue ->
        copy(prevCommand = ModifyValue(delta = prevCommand.delta + command.delta))
      prevCommand is ModifyPointer && command is ModifyPointer ->
        copy(prevCommand = ModifyPointer(delta = prevCommand.delta + command.delta))
      else -> appendPrevIfHasEffect(command)
    }
  }

  fun finish(): Scope {
    return appendPrevIfHasEffect().scope
  }

  /**
   * Ignores [ModifyValue] and [ModifyPointer] if their delta is 0.
   * Also ignores empty [Loop]s.
   */
  private fun appendPrevIfHasEffect(command: Command? = null): ScopeOptimizer {
    return when {
      prevCommand is ModifyValue && prevCommand.delta != 0 -> appendPrev(command)
      prevCommand is ModifyPointer && prevCommand.delta != 0 -> appendPrev(command)
      prevCommand is Loop && prevCommand.scope.commands.isNotEmpty() -> appendPrev(
        command,
        Loop(scope = prevCommand.scope.commands
          .fold(ScopeOptimizer()) { optimizer, cmd -> optimizer.process(cmd) }
          .finish()
        )
      )
      prevCommand is PrintValue -> appendPrev(command)
      else -> copy(prevCommand = command)
    }
  }

  private fun appendPrev(command: Command?, prevCommand: Command? = null): ScopeOptimizer {
    val prev = prevCommand ?: this.prevCommand ?: throw IllegalStateException("no previous command to append")
    return ScopeOptimizer(command, Scope(scope.commands + prev))
  }
}
