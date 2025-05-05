package app.dragapult

sealed interface Platform : FileKind {

    val label: String

    data object Android : Platform {
        override val label = "android"
    }

    data object Apple : Platform {
        override val label = "apple"
    }

    data object Json : Platform {
        override val label = "json"
    }

    companion object {

        val entries = sequence {
            yield(Android.label)
            yield(Apple.label)
            yield(Json.label)
        }

        fun valueOf(value: String) = when (value) {
            Android.label -> Android
            Apple.label -> Apple
            Json.label -> Json
            else -> error("Unknown value $value")
        }

    }

}