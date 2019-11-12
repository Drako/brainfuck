package guru.drako.interpreters.brainfuck

@Suppress("NOTHING_TO_INLINE")
inline fun <T> List<T>.replace(index: Int, value: T): List<T> {
  return toMutableList().apply { set(index, value) }
}

inline fun <T> List<T>.replace(index: Int, generator: (T) -> T): List<T> {
  return replace(index, generator(get(index)))
}
