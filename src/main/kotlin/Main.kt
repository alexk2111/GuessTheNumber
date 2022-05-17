import kotlin.random.Random

fun main(args: Array<String>) {

    val secretNumber: Int
    var userInput: Int
    var machineInput: Int
    var userAnswer: String?

    var game: GuessTheNumberAC = MachineGame()
    secretNumber = Random.nextInt(game.minNumber, game.maxNumber)

    while (true) {
        println("Введите число")
        userAnswer = readLine()
        try {
            if (userAnswer != null){
                userInput = userAnswer.toInt()
                if (!game.checkInput(userInput.toString())) {
                    continue
                }
                if (userInput > secretNumber){
                    game.maxNumber = userInput - 1
                    println("Ваше число больше")
                    continue
                }
                if (userInput < secretNumber){
                    game.minNumber = userInput + 1
                    println("Ваше число меньше")
                    continue
                }
                if (userInput == secretNumber){
                    println("Вы угадали")
                    break
                }
            }
        } catch (e: NumberFormatException) {
            println("Ошибка!!! Введите число.")
            continue
        }
        break
    }

    game = UserGame()
    game.minNumber = 0
    game.maxNumber = 100
    println("Загадайте число от 0 до 100 и нажмите Enter")
    readLine()

    while (true){
        machineInput = if (game.minNumber != game.maxNumber && game.minNumber < game.maxNumber) {
            Random.nextInt(game.minNumber, game.maxNumber)
        } else {
            game.maxNumber
        }
        println("Вы загадали число: $machineInput?")
        userAnswer = readLine()
        while (true){
            if (userAnswer != null){
                if(!game.checkInput(userAnswer.toString())) {
                    userAnswer = readLine()
                    continue
                }
            }
            break
        }
        if (userAnswer != null) {
            when (userAnswer) {
                ">" -> {
                    game.minNumber = machineInput + 1
                    continue
                }
                "<" -> {
                    game.maxNumber = machineInput - 1
                    continue
                }
                "=" -> {
                    println("Игра закончена")
                    break
                }
                else -> {
                    continue
                }
            }
        }
        break
    }
}

abstract class GuessTheNumberAC {
    var minNumber = 0
    var maxNumber = 100

    abstract fun checkInput(inputValue: String): Boolean
}

class UserGame: GuessTheNumberAC() {
    override fun checkInput(inputValue: String): Boolean {
        if (inputValue !in listOf(">", "<" ,"=")) {
            println("Не верный ввод")
            println("> - число больше загаданного  < - число меньше загаданного  = - число угаданно")
            return false
        }
        return true
    }
}

class MachineGame: GuessTheNumberAC() {
    override fun checkInput(inputValue: String): Boolean {
        if (inputValue.toInt() !in minNumber..maxNumber) {
            println("Число должно быть в диапазоне от $minNumber до $maxNumber")
            return false
        }
        return true
    }
}