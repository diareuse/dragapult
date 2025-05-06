package app.dragapult

import app.dragapult.di.DragapultModule
import app.dragapult.di.Input
import app.dragapult.di.Output
import dagger.BindsInstance
import dagger.Component
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [DragapultModule::class])
interface App {

    val dragapult: Dragapult

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance source: Source,
            @BindsInstance platform: Platform,
            @BindsInstance @Input ingress: File,
            @BindsInstance @Output egress: File
        ): App
    }

}