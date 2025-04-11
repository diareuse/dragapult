-verbose
-dontwarn nl.adaptivity.xmlutil.**
-dontwarn okio.**
-keep public class dragapult.app.MainKt {
    public static void main(java.lang.String[]);
}

# Optimization settings
-optimizationpasses 5
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
-mergeinterfacesaggressively