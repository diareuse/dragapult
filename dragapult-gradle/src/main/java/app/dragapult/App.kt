package app.dragapult

import app.dragapult.di.DragapultModule
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(modules = [DragapultModule::class])
interface App {

    val dragapult: Dragapult

}