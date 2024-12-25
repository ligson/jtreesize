#!/bin/bash

export JAVA_HOME=$JAVA_21_HOME
echo "JAVA_HOME=$JAVA_HOME"

# Get the current directory
currentDir=$(pwd)

# Get the Maven version and store it in the PKG_VERSION variable
PKG_VERSION=`mvn help:evaluate -Dexpression=project.version -q -DforceStdout`

# Print the version
echo "The version is: $PKG_VERSION"

echo "开始构建"
mvn -Dmaven.test.skip=true clean package

echo "构建完成"
cd target
tar -zxvf jtreesize-$PKG_VERSION-jtreesize.tar.gz
cd jtreesize-$PKG_VERSION
jpackage --input . \
  --name jtreesize \
  --main-jar lib/jtreesize-$PKG_VERSION.jar \
  --main-class org.ligson.jtreesize.App \
  --type dmg \
  --resource-dir conf \
  --icon conf/treesize.icns \
  --app-version $PKG_VERSION

# Check if the dist folder exists
if [ -d "$currentDir/dist" ]; then
    echo "dist folder exists. Deleting contents..."
    rm -rf "$currentDir/dist"
    mkdir "$currentDir/dist"
else
    echo "dist folder does not exist. Creating..."
    mkdir "$currentDir/dist"
fi

mv jtreesize-$PKG_VERSION.dmg "$currentDir/dist"
echo "构建文件是：$currentDir/dist/jtreesize-$PKG_VERSION.dmg"
cd $currentDir
