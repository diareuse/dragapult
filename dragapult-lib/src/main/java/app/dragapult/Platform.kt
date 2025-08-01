package app.dragapult

sealed interface Platform : FileKind {

    val label: String

    data object Android : Platform {
        override val label get() = LABEL
        const val LABEL = "android"
    }

    data object Apple : Platform {
        override val label get() = LABEL
        const val LABEL = "apple"
    }

    data object Json : Platform {
        override val label get() = LABEL
        const val LABEL = "json"
    }

    data object Unity : Platform {
        override val label get() = LABEL
        const val LABEL = "unity"
    }

    companion object {

        val entries = sequence {
            yield(Android.label)
            yield(Apple.label)
            yield(Json.label)
            yield(Unity.label)
        }

        fun valueOf(value: String) = when (value) {
            Android.label -> Android
            Apple.label -> Apple
            Json.label -> Json
            Unity.label -> Unity
            else -> error("Unknown value $value")
        }

    }

}