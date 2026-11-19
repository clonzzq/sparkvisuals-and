cmake_minimum_required(VERSION 3.22)
project(SparkVisuals LANGUAGES CXX)

include(FetchContent)
FetchContent_Declare(
    preloader_android
    GIT_REPOSITORY https://github.com/LiteLDev/preloader-android.git
    GIT_TAG 0.2.2
)
FetchContent_MakeAvailable(preloader_android)

add_library(SparkVisuals SHARED src/SparkVisualsMod.cpp)
target_compile_features(SparkVisuals PRIVATE cxx_std_20)
target_link_libraries(SparkVisuals PRIVATE preloader)
set_target_properties(SparkVisuals PROPERTIES OUTPUT_NAME "SparkVisuals")
