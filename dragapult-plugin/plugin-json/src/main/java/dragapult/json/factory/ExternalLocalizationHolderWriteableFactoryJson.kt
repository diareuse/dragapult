package dragapult.json.factory

import dragapult.core.ExternalLocalizationHolder
import dragapult.core.ExternalLocalizationHolderWriteable
import dragapult.core.LocalizationType
import dragapult.json.LocalizationTypeJson
import dragapult.json.adapter.ExternalLocalizationHolderWriteableJson

class ExternalLocalizationHolderWriteableFactoryJson : ExternalLocalizationHolderWriteable.Factory {

    override val type: LocalizationType
        get() = LocalizationTypeJson

    override fun writeable(holders: Sequence<ExternalLocalizationHolder>): ExternalLocalizationHolderWriteable {
        return ExternalLocalizationHolderWriteableJson(holders)
    }

}