package app.dragapult.android

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import org.gradle.api.Project

fun Project.forEachVariant(action: (variant: Variant) -> Unit) {
    extensions.findByType(AndroidComponentsExtension::class.java)?.onVariants { variant ->
        action(variant)
    }
}