package dragapult.json.factory

import dragapult.core.ExternalLocalizationHolder
import dragapult.core.LocalizationType
import dragapult.json.LocalizationTypeJson

class ExternalLocalizationHolderFactoryJson : ExternalLocalizationHolder.Factory {

    override val type: LocalizationType
        get() = LocalizationTypeJson

}