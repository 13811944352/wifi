#!/bin/bash -ex

DATE=`date +%y%m%d-%H%M`
BUILDS=`printf %04d ${BUILD_NUMBER}`
TARGET=android-14

if [ x$1 = x ] ; then
main_version=\"1.0\"
else
main_version=$1
fi

if [ x$2 = x ] ; then
debug=true
else
debug=$2
fi

if [ x$3 = x ] ; then
inner=true
else
inner=$3
fi

export ANT_HOME=/opt/apache-ant-1.8.2/
export SDK_HOME=/opt/android-sdk/android-sdk-linux/
export SDK_HOME_JAR=$SDK_HOME/platforms/android-19/android.jar
export NDK_HOME=/opt/android-ndk-r7/

export DX_TOOLS=$SDK_HOME/build-tools/19.0.3/dx

export PATH=$NDK_HOME:$SDK_HOME:$SDK_HOME/tools:$ANT_HOME:$ANT_HOME/bin:$JRE_HOME/bin:$PATH

#ant build-lib -f jar.xml -lib libs/server-api-1.1.2.1.jar -lib libs/org-apache-http.jar -lib libs/framework.jar -lib libs/framework.jar_5.1
#ant jars  -f jar.xml  -lib lib/netty-all-4.0.9.Final.jar  
ant jars  -f jar.xml  -lib lib/netty-all-4.0.33.Final.jar  -lib lib/json.jar -lib /var/lib/tomcat7/webapps/nal/WEB-INF/lib/nal.jar

