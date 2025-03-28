package dragapult.csv.factory

import dragapult.core.ExternalLocalizationHolder
import dragapult.core.LocalizationType
import dragapult.csv.LocalizationTypeCsv

class ExternalLocalizationHolderFactoryCsv : ExternalLocalizationHolder.Factory {

    override val type: LocalizationType
        get() = LocalizationTypeCsv

}