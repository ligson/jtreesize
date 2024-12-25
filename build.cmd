@echo off
chcp 65001
:: Get the current directory
set "currentDir=%cd%"

:: 获取 Maven 的版本号并存储到变量 PKG_VERSION
for /f "tokens=* delims=" %%i in ('mvn help:evaluate "-Dexpression=project.version" -q "-DforceStdout"') do (
    if not "%%i"=="" set "PKG_VERSION=%%i"
)

:: 打印版本号
echo The version is: %PKG_VERSION%

echo "开始构建"
call mvn -Dmvn.test.skip=true clean package

echo "构建完成"
cd target
tar -zxvf jtreesize-%PKG_VERSION%-jtreesize.tar.gz
cd /d jtreesize-%PKG_VERSION%
jpackage --input . --name jtreesize --main-jar lib/jtreesize-%PKG_VERSION%.jar --main-class org.ligson.jtreesize.App --type exe --resource-dir conf --icon conf/treesize.ico --win-dir-chooser --win-shortcut --app-version %PKG_VERSION%

:: Check if the dist folder exists
if exist "%currentDir%\dist" (
    echo "dist folder exists. Deleting contents..."
    rd /s /q "%currentDir%\dist"
    mkdir "%currentDir%\dist"
) else (
    echo "dist folder does not exist. Creating..."
    mkdir "%currentDir%\dist"
)
MOVE jtreesize-%PKG_VERSION%.exe "%currentDir%\dist"
echo "构建文件是：%currentDir%\dist\jtreesize-%PKG_VERSION%.exe"
cd %currentDir%

