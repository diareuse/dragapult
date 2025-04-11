package dragapult.app.di

import dagger.Module
import dagger.Provides
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

@Module(
    includes = [
        OptionGenerateModule::class,
        OptionConsumeModule::class,
        OptionModule::class
    ]
)
class OptionsModule {

    @Provides
    fun options(
        options: Set<@JvmSuppressWildcards Option>
    ): Options = Options().apply {
        for (option in options)
            addOption(option)
    }

    @Generate
    @Provides
    fun generateOptions(
        @Generate options: Set<@JvmSuppressWildcards Option>
    ): Options = Options().apply {
        for (option in options)
            addOption(option)
    }

    @Consume
    @Provides
    fun consumeOptions(
        @Consume options: Set<@JvmSuppressWildcards Option>
    ): Options = Options().apply {
        for (option in options)
            addOption(option)
    }

}