package guru.drako.interpreters.brainfuck

sealed class Command : (State) -> State

data class ModifyPointer(val delta: Int) : Command() {
  override fun invoke(state: State) = with(state) {
    val limit = memory.size
    val newIndex = (index + delta) % limit
    copy(index = if (newIndex < 0) newIndex + limit else newIndex)
  }
}

data class ModifyValue(val delta: Int) : Command() {
  override fun invoke(state: State) = with(state) {
    copy(memory = memory.replace(index) { it + delta })
  }
}

object PrintValue : Command() {
  override fun invoke(state: State) = with(state) {
    copy(output = output + memory[index].toChar())
  }
}

data class Scope(val commands: List<Command>) : Command() {
  constructor(vararg commands: Command) : this(commands.asList())

  override fun invoke(oldState: State): State {
    return commands.fold(oldState) { state, command -> command(state) }
  }
}

data class Loop(val scope: Scope) : Command() {
  constructor(vararg commands: Command) : this(Scope(commands.asList()))

  override tailrec fun invoke(state: State): State {
    if (state.memory[state.index] == 0) {
      return state
    }

    return invoke(scope(state))
  }
}
