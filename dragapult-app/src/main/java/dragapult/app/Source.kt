package dragapult.app

sealed interface Source : FileKind {

    val label: String

    data object Json : Source {
        override val label = "json"
    }

    data object Csv : Source {
        override val label = "csv"
    }

    data object Yaml : Source {
        override val label = "yaml"
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