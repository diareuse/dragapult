package dragapult.apple.factory

import dragapult.apple.LocalizationTypeApple
import dragapult.apple.adapter.PlatformLocalizedFileWriteableAppleAdapter
import dragapult.core.LocalizationType
import dragapult.core.PlatformLocalizedFile
import dragapult.core.PlatformLocalizedFileWriteable

class PlatformLocalizedFileWriteableFactoryApple : PlatformLocalizedFileWriteable.Factory {

    private var allowBlankValues = false

    override val type: LocalizationType
        get() = LocalizationTypeApple

    override fun setAllowBlankValues(value: Boolean) = apply {
        this.allowBlankValues = value
    }

    override fun writeable(file: PlatformLocalizedFile): PlatformLocalizedFileWriteable {
        return PlatformLocalizedFileWriteableAppleAdapter(file, allowBlankValues)
    }

}