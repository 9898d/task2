import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.optional
import kotlinx.cli.vararg
import java.io.File


var count = 0
var isChar = true
var name: String? = null
var countOfFiles = 0


fun main(args: Array<String>) {
    val parser = ArgParser("")

    val files by parser.argument(ArgType.String).vararg().optional()

    val cOption by parser.option(ArgType.Int, shortName = "c")
    val nOption by parser.option(ArgType.Int, shortName = "n")
    val oOption by parser.option(ArgType.String, shortName = "o")

    parser.parse(args)
    if (cOption != null && nOption != null) throw Exception("Can't add c and n options")
    val a = choseStringOrChar(cOption, nOption)
    count = a.first
    isChar = a.second
    name = oOption
    if (files.isEmpty()) {
        readFromConsole()
    } else {
        readFromFile(files)
    }
}

fun readFromConsole() {
    val text = mutableListOf<String>()
    var line = ""
    while (true) {
        line = readln()
        if (line != "end") text.add(line)
        else break
    }
    printResult(selectRange(text, "console"))
}


fun readFromFile(files: List<String>) {
    countOfFiles = files.size
    for (file in files) {
        val text = File(file).readLines()
        printResult(selectRange(text, file))
    }
}


fun choseStringOrChar(c: Int?, n: Int?): Pair<Int, Boolean> {
    val count = (c ?: n) ?: 10
    val char = c != null
    return Pair(count, char)
}

var result = ""

fun selectRange(text: List<String>, name: String): String {
    if (isChar) {
        val string = text.joinToString("")
        result += name
        result += "\n"
        result += string.substring(string.length - count, string.length)
        result += "\n"
    } else {
        if (text.size < count) return text.joinToString("\n")
        result += name
        result += "\n"
        result += text.subList(text.size - count, text.size).joinToString("\n")
        result += "\n"
    }
    return if (countOfFiles == 1) {
        result.substringAfter("\n")
    } else result
}

fun printResult(result: String) {
    if (name == null) {
        println(result)
    }
    else {
        val res = File(name).bufferedWriter()
        res.write(result)
        res.close()
    }
}