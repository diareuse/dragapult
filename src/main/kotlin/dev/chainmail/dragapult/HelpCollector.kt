package dev.chainmail.dragapult

import dev.chainmail.dragapult.args.*

@Suppress("FunctionName")
fun PrintHelpCollector() {
    val help =
        StringBuilder("Dragapult is a CLI localization / translation managing tool. It's capable of parsing common translation file to various files required by Android, Apple platforms and Web (through jquery-localize or other DIY solutions).")
            .appendLine()
            .appendLine()
            .appendLine(InputFile.toString())
            .appendLine(InputFormat.toString())
            .appendLine(InputSeparator.toString())
            .appendLine(OutputDirectory.toString())
            .appendLine(OutputFormat.toString())
            .appendLine(Flags.toString())
            .toString()

    print(help)
    Exit.ok()
}

@Suppress("FunctionName")
fun PrintExamples() {
    val examples = """
Common usage for Android is:

    dragapult -i localization/translations.csv -iF csv -iS ; -o app/src/main/res -oF android
        or through a gradle task before build
    dragapult -i localization/translations.csv -iF csv -iS ; -o app/build/generated/res -oF android

Common usage for Apple is:

    dragapult -i localization/translations.csv -iF csv -iS ; -o Resources -oF apple

Common usage for Web (Vue.js like template) is:

    dragapult -i localization/translations.csv -iF csv -iS ; -o public/i18n -oF json
    """.trimIndent()

    println(examples)
    Exit.ok()
}