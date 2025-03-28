package dragapult.android.factory

import dragapult.android.LocalizationTypeAndroid
import dragapult.android.adapter.PlatformLocalizedFileWriteableAndroidAdapter
import dragapult.core.LocalizationType
import dragapult.core.PlatformLocalizedFile
import dragapult.core.PlatformLocalizedFileWriteable

class PlatformLocalizedFileWriteableFactoryAndroid : PlatformLocalizedFileWriteable.Factory {

    private var allowBlankValues = false

    override val type: LocalizationType
        get() = LocalizationTypeAndroid

    override fun setAllowBlankValues(value: Boolean) = apply {
        this.allowBlankValues = value
    }

    override fun writeable(file: PlatformLocalizedFile): PlatformLocalizedFileWriteable {
        return PlatformLocalizedFileWriteableAndroidAdapter(file, allowBlankValues)
    }

}