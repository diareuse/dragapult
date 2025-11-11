package app.dragapult.ir.json

interface JsonIRPreferences {
    val prettyPrint: Boolean get() = true
    val prettyPrintIndent: String get() = "\t"
    val explicitNulls: Boolean get() = false
}