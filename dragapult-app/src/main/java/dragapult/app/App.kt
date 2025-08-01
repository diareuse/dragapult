package dragapult.app

import dagger.BindsInstance
import dagger.Component
import dragapult.app.di.CommandModule
import jakarta.inject.Singleton

@Singleton
@Component(modules = [CommandModule::class])
interface App {

    val command: Command

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance args: Array<out String>): App
    }

}