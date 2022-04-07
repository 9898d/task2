import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class Task2Tests {

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        Assertions.assertEquals(expectedContent, content)
    }

    @Test
    fun onlyOneNameOfFile() {
        main(arrayOf("src/test/resources/test5.txt", "-c", "3", "-o", "src/test/resources/ooo.txt"))
        assertFileContent("src/test/resources/ooo.txt", "тая")
    }

    @Test
    fun twoNameOfFile() {
        main(arrayOf("src/test/resources/test5.txt", "src/test/resources/test11.txt", "-c", "3", "-o", "src/test/resources/ooo.txt"))
        assertFileContent("src/test/resources/ooo.txt",
            """src/test/resources/test5.txt
тая
src/test/resources/test11.txt
чка""")
    }


    @Test
    fun oneFileAndStrings() {
        main(arrayOf("src/test/resources/test5.txt", "-n", "3", "-o", "src/test/resources/ooo.txt"))
        assertFileContent("src/test/resources/ooo.txt", """Третья строчка
Четвертая
Пятая""")
    }

    @Test
    fun threeFilesAndStrings() {
        main(arrayOf("src/test/resources/test5.txt", "src/test/resources/test11.txt", "src/test/resources/test11_4.txt", "-n", "3", "-o", "src/test/resources/ooo.txt"))
        assertFileContent("src/test/resources/ooo.txt", """src/test/resources/test5.txt
Третья строчка
Четвертая
Пятая
src/test/resources/test11.txt
Девятая
Десятая
Одиннадцатая строчка
src/test/resources/test11_4.txt
Одиннадцатая строчка

""")
    }

    @Test
    fun twoFileWithoutOption() {
        main(arrayOf("src/test/resources/test5.txt", "src/test/resources/test11.txt", "-o", "src/test/resources/ooo.txt"))
        assertFileContent("src/test/resources/ooo.txt", """src/test/resources/test5.txt
Первая строчка
Вторая строчка
Третья строчка
Четвертая
Пятая
src/test/resources/test11.txt
Вторая строчка
Третья строчка
Четвертая
Пятая
Шестая
Седьмая
Восьмая
Девятая
Десятая
Одиннадцатая строчка""")
    }

    @Test
    fun ExeptionCAndN() {
        assertThrows<Exception> {main(arrayOf("src/test/resources/test5.txt", "-c", "3", "-n", "23", "-o", "src/test/resources/ooo.txt"))}
    }
}