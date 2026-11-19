param(
    [string]$Ndk = "",
    [string]$Sdk = "",
    [switch]$Clean
)
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
if (-not $Sdk) { $Sdk = $env:ANDROID_HOME }
if (-not $Sdk) { $Sdk = $env:ANDROID_SDK_ROOT }
if (-not $Sdk) { throw "Set ANDROID_HOME or pass -Sdk path." }
if (-not $Ndk) {
    $Ndk = $env:ANDROID_NDK_HOME
    if (-not $Ndk) { $Ndk = $env:ANDROID_NDK_ROOT }
    if (-not $Ndk) {
        $NdkRoot = Join-Path $Sdk "ndk"
        if (Test-Path $NdkRoot) {
            $Ndk = Get-ChildItem $NdkRoot -Directory | Sort-Object Name -Descending | Select-Object -First 1 -ExpandProperty FullName
        }
    }
}
if (-not $Ndk -or -not (Test-Path (Join-Path $Ndk "build/cmake/android.toolchain.cmake"))) {
    throw "Android NDK not found. Install NDK in Android SDK or pass -Ndk."
}
$BuildDir = Join-Path $Root "build-arm64"
if ($Clean -and (Test-Path $BuildDir)) { Remove-Item $BuildDir -Recurse -Force }
cmake -S $Root -B $BuildDir -G Ninja -DCMAKE_TOOLCHAIN_FILE=(Join-Path $Ndk "build/cmake/android.toolchain.cmake") -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-28 -DANDROID_STL=c++_shared
cmake --build $BuildDir
$Lib = Join-Path $BuildDir "libSparkVisuals.so"
if (-not (Test-Path $Lib)) { throw "Expected library was not produced: $Lib" }
$Dist = Join-Path $Root "dist/arm64-v8a"
$PackageRoot = Join-Path $Dist "SparkVisuals"
if (Test-Path $PackageRoot) { Remove-Item $PackageRoot -Recurse -Force }
New-Item $PackageRoot -ItemType Directory -Force | Out-Null
Copy-Item (Join-Path $Root "manifest.json") $PackageRoot
Copy-Item $Lib $PackageRoot
$ConfigSource = Join-Path $Root "config"
if (Test-Path $ConfigSource) {
    Copy-Item $ConfigSource (Join-Path $PackageRoot "config") -Recurse -Force
}
$Zip = Join-Path $Dist "SparkVisuals.zip"
if (Test-Path $Zip) { Remove-Item $Zip -Force }
Compress-Archive -Path (Join-Path $PackageRoot "*") -DestinationPath $Zip
$LeviPack = Join-Path $Dist "SparkVisuals.levipack"
if (Test-Path $LeviPack) { Remove-Item $LeviPack -Force }
Move-Item $Zip $LeviPack
Write-Host "Package created: $LeviPack"
