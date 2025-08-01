package app.dragapult.android

import dagger.Module
import dagger.Provides
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlSerializationPolicy

@Module
class AndroidDepModule {
    @Provides
    internal fun xml(): XML = XML {
        recommended {
            ignoreUnknownChildren()
            this.pedantic = false
            encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.NEVER
        }
        this.xmlDeclMode = XmlDeclMode.Minimal
        this.xmlVersion = XmlVersion.XML10
    }

}