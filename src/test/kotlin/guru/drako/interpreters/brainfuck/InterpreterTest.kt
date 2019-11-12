package guru.drako.interpreters.brainfuck

import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterpreterTest {
  @Test
  fun `hello world`() {
    val program = """
       ++++++++++
       [
        >+++++++>++++++++++>+++>+<<<<-
       ]                       Prepare characters for text output (loop)
       >++.                    Output of 'H'
       >+.                     'e'
       +++++++.                'l'
       .                       'l'
       +++.                    'o'
       >++.                    White Space
       <<+++++++++++++++.      'W'
       >.                      'o'
       +++.                    'r'
       ------.                 'l'
       --------.               'd'
       >+.                     '!'
       >.                      New Line
       +++.                    Carriage Return
    """.trimIndent()
    val output = Interpreter.interpret(program)
    assertEquals(expected = "Hello World!\n\r", actual = output)
  }
}
