-keep class com.nvidia.** {*;}
-keep class com.wardrumstudios.** {*;}
-keepclassmembers  class com.nvidia.** {*;}
-keepclassmembers  class com.wardrumstudios.** {*;}
-keepclasseswithmembernames class * {
     native <methods>;
 }

-keepattributes SourceFile
-keepattributes SourceFile
-renamesourcefileattribute SourceFile

# 
# FROM WEIKTON | PRIVATE GROUP
#