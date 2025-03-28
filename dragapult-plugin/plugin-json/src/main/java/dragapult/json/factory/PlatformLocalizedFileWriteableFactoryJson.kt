package dragapult.json.factory

import dragapult.core.LocalizationType
import dragapult.core.PlatformLocalizedFile
import dragapult.core.PlatformLocalizedFileWriteable
import dragapult.json.LocalizationTypeJson
import dragapult.json.adapter.PlatformLocalizedFileWriteableJsonAdapter

class PlatformLocalizedFileWriteableFactoryJson : PlatformLocalizedFileWriteable.Factory {

    private var allowBlankValues = false

    override val type: LocalizationType
        get() = LocalizationTypeJson

    override fun setAllowBlankValues(value: Boolean) = apply {
        this.allowBlankValues = value
    }

    override fun writeable(file: PlatformLocalizedFile): PlatformLocalizedFileWriteable {
        return PlatformLocalizedFileWriteableJsonAdapter(file, allowBlankValues)
    }

}