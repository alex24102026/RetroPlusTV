RETRO PLUS TV - DOS VARIANTES DE ARQUITECTURA

Este es un proyecto Android Studio, NO son APK ya compiladas.
Incluye dos variantes de compilación:
- arm32Debug: destinada a ABI armeabi-v7a (32 bits).
- arm64Debug: destinada a ABI arm64-v8a (64 bits).

La app muestra una pantalla de inicio sencilla con botón VER EN VIVO y abre
https://retroplustv.com/ en WebView. No incorpora contenido audiovisual ni
código de la aplicación oficial. Es una aplicación independiente no oficial.

IMPORTANTE SOBRE LA ARQUITECTURA:
La aplicación usa WebView del sistema y no incluye bibliotecas nativas propias.
Por ello las variantes sirven para seleccionar ABI en caso de añadir bibliotecas
nativas en el futuro, pero la compatibilidad real de WebView depende del firmware
del TV Box. Un Android de 32 bits no se convierte en 64 bits por instalar arm64.

REQUISITOS PARA COMPILAR:
- Android Studio
- Android SDK Platform 33
- Gradle 7.5 y Android Gradle Plugin 7.4.2 (Android Studio puede sincronizarlo)

PASOS:
1. Descomprime el ZIP.
2. Abre la carpeta RetroPlusTV_DosArquitecturas en Android Studio.
3. Espera la sincronización de Gradle.
4. En Build > Select Build Variant elige arm32Debug o arm64Debug.
5. Build > Build Bundle(s) / APK(s) > Build APK(s).

RUTAS RESULTANTES:
app/build/outputs/apk/arm32/debug/app-arm32-debug.apk
app/build/outputs/apk/arm64/debug/app-arm64-debug.apk

MIN SDK 16 (Android 4.1+). La reproducción depende de la versión del WebView,
los códecs, DRM, la conexión y las características del sitio. No se garantiza
que el sitio permita reproducir todas las transmisiones dentro de WebView.

Para el TV Box Z6 con Android 10, prueba primero ARM32 si no conoces la ABI.
