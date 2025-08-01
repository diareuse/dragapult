package app.dragapult

sealed interface Source : FileKind {

    val label: String

    data object Json : Source {
        override val label get() = LABEL
        const val LABEL = "json"
    }

    data object Csv : Source {
        override val label get() = LABEL
        const val LABEL = "csv"
    }

    data object Yaml : Source {
        override val label get() = LABEL
        const val LABEL = "yaml"
    }

    companion object {

        val entries = sequence {
            yield(Json.label)
            yield(Csv.label)
            yield(Yaml.label)
        }

        fun valueOf(value: String) = when (value) {
            Json.label -> Json
            Csv.label -> Csv
            Yaml.label -> Yaml
            else -> error("Unknown value $value")
        }

    }

}