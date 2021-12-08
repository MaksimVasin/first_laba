enum class Alignment {
    LEFT,
    RIGHT,
    CENTER,
    JUSTIFY,
}

fun alignText(
    text: String, // input text with line breaks
    lineWidth: Int = 120, // line width
    alignment: Alignment = Alignment.LEFT,
): String {
    if (lineWidth < 1) throw IllegalArgumentException("Line length can't be less than one") // exception
    val wordsList: ArrayList<String> = getWordsList(text, lineWidth) /* getting array list, each element
    of the list is a separate word or a word divided into several parts (due to the limitation of lines in
    width */
    val oneStringList: ArrayList<String> = ArrayList() // array list of one line that consist of words
    var correctText = "" // text with correct spaces and line breaks
    var lineWidthCur: Int = wordsList[0].length // current line width
    for (i in 1 until wordsList.size) {
        // if the next word does not fit, add the words before it to the output text and place spaces
        if (lineWidthCur + wordsList[i].length - 1 >= lineWidth) {
            lineWidthCur = wordsList[i].length
            oneStringList.add(wordsList[i - 1].trim(' '))
            correctText += setSpace(oneStringList, lineWidth, alignment)
            oneStringList.clear()
        } else {
            lineWidthCur += wordsList[i].length
            oneStringList.add(wordsList[i - 1])
        }
    }
    // last iteration
    oneStringList.add(wordsList.last().trim(' '))
    correctText += setSpace(oneStringList, lineWidth, alignment)
    return correctText
}

private fun getWordsList(text: String, lineWidth: Int): ArrayList<String> {
    // replace line breaks with spaces
    var uncorrectWordsList = ArrayList<String>()
    if (" " in text) {
        uncorrectWordsList = (text.replace('\n', ' ')).split(' ') as ArrayList<String>
    } else {
        uncorrectWordsList.add(text)
    }

    val wordsList: ArrayList<String> = ArrayList()
    for (i in 0 until uncorrectWordsList.size) {
        // if a word fits into a string, add it
        if (uncorrectWordsList[i].length <= lineWidth) {
            wordsList.add(uncorrectWordsList[i] + ' ')
        }
        // otherwise, we divide it into parts
        else {
            for (j in 1..(uncorrectWordsList[i].length) / lineWidth) {
                wordsList.add(uncorrectWordsList[i].take(lineWidth))
                uncorrectWordsList[i] = uncorrectWordsList[i].removeRange(0, lineWidth)
            }
            if (uncorrectWordsList[i].isNotEmpty()) {
                wordsList.add(uncorrectWordsList[i] + ' ')
            }
        }
    }
    return wordsList
}


private fun setSpace(
    stringList: ArrayList<String>,
    lineWidth: Int = 120,
    alignment: Alignment = Alignment.LEFT,
): String {
    var countSpace = lineWidth
    // counting required spaces
    for (i in 0 until stringList.size) {
        countSpace -= stringList[i].length
    }
    val stringWithSpace = when (alignment) {
        Alignment.LEFT -> {
            left(stringList)
        }
        Alignment.RIGHT -> {
            right(stringList, countSpace)
        }
        Alignment.CENTER -> {

            center(stringList, countSpace)
        }
        Alignment.JUSTIFY -> {
            justify(stringList, countSpace)
        }
    }
    return stringWithSpace + '\n'
}

private fun left(stringList: ArrayList<String>): String {
    // collecting a string from words
    var stringWithSpace = ""
    for (i in 0 until stringList.size) {
        stringWithSpace += stringList[i]
    }
    return stringWithSpace
}

private fun right(stringList: ArrayList<String>, countSpace: Int): String {
    // add the required number of spaces and collecting a string from words
    var stringWithSpace: String = " ".repeat(countSpace)
    for (i in 0 until stringList.size) {
        stringWithSpace += stringList[i]
    }
    return stringWithSpace
}

private fun center(stringList: ArrayList<String>, countSpace: Int): String {
    // add the required number of spaces and collecting a string from words
    return right(stringList, countSpace / 2)
}

private fun justify(stringList: ArrayList<String>, countSpace: Int): String {
    var stringWithSpace = ""
    /* add the required number of spaces between words.
    First the minimum spaces, and then the rest spaces */
    if (stringList.size != 1) {
        val minSpace: Int = countSpace / (stringList.size - 1)
        for (index in 0 until stringList.size - 1) {
            stringList[index] += " ".repeat(minSpace)
        }
        for (index in 0 until (countSpace % (stringList.size - 1))) {
            stringList[index] += " "
        }
    }
    // collecting a string from words
    for (i in 0 until stringList.size) {
        stringWithSpace += stringList[i]
    }
    return stringWithSpace
}