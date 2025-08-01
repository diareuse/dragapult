-keepattributes *Annotation*,Signature,InnerClasses
-verbose
-dontwarn app.dragapult.**

-keep class nl.adaptivity.xmlutil.jdk.StAXStreamingFactory
-dontnote **
-dontwarn nl.adaptivity.xmlutil.**
-dontwarn okio.**
-keep public class dragapult.app.MainKt {
    public static void main(java.lang.String[]);
}

# === Kotlin ===
-dontwarn kotlin.concurrent.atomics.**
-keep class kotlin.jvm.internal.Intrinsics { *; }
-keep class kotlin.Metadata { *; }
-keepclassmembers class ** {
    @kotlin.Metadata <fields>;
    @kotlin.Metadata <methods>;
}
-keep class kotlin.coroutines.** { *; }
-keep interface kotlin.coroutines.** { *; }
-keepclassmembers class ** {
    private final kotlin.coroutines.Continuation continuation;
}

# === Kotlinx ===
# When editing this file, update the following files as well:
# - META-INF/com.android.tools/proguard/coroutines.pro
# - META-INF/com.android.tools/r8/coroutines.pro

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

# These classes are only required by kotlinx.coroutines.debug.internal.AgentPremain, which is only loaded when
# kotlinx-coroutines-core is used as a Java agent, so these are not needed in contexts where ProGuard is used.
-dontwarn java.lang.instrument.ClassFileTransformer
-dontwarn sun.misc.SignalHandler
-dontwarn java.lang.instrument.Instrumentation
-dontwarn sun.misc.Signal

# Only used in `kotlinx.coroutines.internal.ExceptionsConstructor`.
# The case when it is not available is hidden in a `try`-`catch`, as well as a check for Android.
-dontwarn java.lang.ClassValue

# An annotation used for build tooling, won't be directly accessed.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# === Kotlinx Serialization ===
-keep @kotlinx.serialization.Serializable class * { *; }
-keep class *$$serializer { *; }
-keepnames class kotlinx.serialization.SerialName
-keepnames class kotlinx.serialization.Required
-keepnames class kotlinx.serialization.Transient
-keepnames class kotlinx.serialization.Optional

-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
    @kotlinx.serialization.Required <fields>;
    @kotlinx.serialization.Transient <fields>;
    @kotlinx.serialization.Optional <fields>;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    public static final kotlinx.serialization.KSerializer serializer();
}
-keepclassmembers class kotlinx.serialization.internal.*GeneratedSerializer {
    public static final kotlinx.serialization.KSerializer INSTANCE;
    public static final kotlinx.serialization.KSerializer[] $childSerializers;
}
-keepclassmembers class kotlinx.serialization.internal.* {
    public static final kotlinx.serialization.KSerializer[] $childSerializers;
}
-keepnames class kotlinx.serialization.Polymorphic
-keepnames class kotlinx.serialization.modules.PolymorphicModuleBuilder
-keepnames class kotlinx.serialization.modules.SerializersModule
-keepclassmembers class * {
    @kotlinx.serialization.Polymorphic <fields>;
}

# === CSV ===
-dontwarn org.apache.commons.csv.**

# === Optimization settings ===
-optimizationpasses 5
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
-mergeinterfacesaggressively