package app.dragapult.ir.csv

interface CsvIRPreferences {
    val commentMarker: Char get() = Char(3)
    val recordSeparator: String get() = "\n"
}