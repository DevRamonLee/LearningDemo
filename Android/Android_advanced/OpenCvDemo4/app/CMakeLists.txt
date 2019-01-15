cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)
set(libs "${CMAKE_SOURCE_DIR}/src/main/jniLibs")

#设置头文件路径
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)

add_library( native-lib
             SHARED
             src/main/cpp/native-lib.cpp )

find_library( log-lib
              log )

#添加库
add_library(libopencv_java3 SHARED IMPORTED )
set_target_properties(libopencv_java3
                        PROPERTIES
                    IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_java3.so")

#find_package(OpenCV REQUIRED)

#添加链接的opencv库和 android一些常用的库比如ljnigraphics，bitmap需要用到
target_link_libraries( native-lib android log -ljnigraphics libopencv_java3
                       ${log-lib})