#!/bin/bash  -ex

APPNAME=$1
TARGET=android-19

export ANT_HOME=/opt/apache-ant-1.8.2/
#export SDK_HOME=/opt/android-sdk-linux_x86/
export SDK_HOME=/opt/android-sdk/android-sdk-linux/
export NDK_HOME=/opt/android-ndk-r7/
export PATH=$NDK_HOME:$SDK_HOME:$SDK_HOME/tools:$ANT_HOME:$ANT_HOME/bin:$PATH


cd $APPNAME

android update project -n $APPNAME -p `pwd` -t $TARGET  

#if [[ $1 == "x86" ]]; then
#        ndk-build APP_ABI=x86
#elif [[ $1 == "arm" || "$1" == "" ]]; then
#        ndk-build APP_ABI=armeabi
#elif [[ $1 == "all" ]]; then
#        ndk-build APP_ABI=x86
#        ndk-build APP_ABI=armeabi
#else
#        echo arg erro!!;
#fi
#echo 111
#cp arc_libs/armeabi/* libs/armeabi/
#echo 222
#ndk-build APP_ABI=armeabi-64
#ndk-build APP_ABI=armeabi
ant clean 
#ant  release
#ant -f buildlib.xml

cd ..
#java -jar ./sign/signapk.jar ./sign/shared.x509.pem ./sign/shared.pk8 $APPNAME/bin/$APPNAME-release-unsigned.apk $APPNAME/bin/$APPNAME.apk
java -jar /opt/In-House-KEY/signapk.jar /opt/In-House-KEY/releasekey.x509.pem /opt/In-House-KEY/releasekey.pk8  $APPNAME/bin/$APPNAME-release-unsigned.apk $APPNAME/bin/$APPNAME.apk
cp $APPNAME/bin/$APPNAME.apk ./1.apk
