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
    var wordsList: ArrayList<String> = getWordsList(text, lineWidth) /* getting array list, each element
    of the list is a separate word or a word divided into several parts (due to the limitation of lines in
    width */
    var oneStringList: ArrayList<String> = ArrayList() // array list of one line that consist of words
    var correctText: String = "" // text with correct spaces and line breaks
    var lineWidthCur: Int = wordsList[0].length // current line width
    for (i in 1 until wordsList.size) {
        // if the next word does not fit, add the words before it to the output text and place spaces
        if (lineWidthCur - 1 > lineWidth || lineWidthCur + wordsList[i].length - 1 > lineWidth) {
            lineWidthCur = wordsList[i].length
            oneStringList.add(wordsList[i - 1].trim(' '))
            correctText += getString(oneStringList, lineWidth, alignment)
            oneStringList.clear()
            wordsList[i - 1] = wordsList[i - 1].trim(' ')
        } else {
            lineWidthCur += wordsList[i].length
            oneStringList.add(wordsList[i - 1])
        }
    }
    // last iteration
    oneStringList.add(wordsList.last().trim(' '))
    correctText += getString(oneStringList, lineWidth, alignment)
    return correctText
}

fun getWordsList(text: String, lineWidth: Int): ArrayList<String> {
    // replace line breaks with spaces
    var uncorrectWordsList: ArrayList<String> = text.replace('\n', ' ').split(' ') as ArrayList<String>

    var wordsList: ArrayList<String> = ArrayList()
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

fun getString(stringList: ArrayList<String>, lineWidth: Int, alignment: Alignment): String {
    // collecting a string from words
    var stringWithSpace: String = ""
    for (i in 0 until stringList.size) {
        stringWithSpace += stringList[i]
    }
    return stringWithSpace + '\n'
}