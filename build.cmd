@echo off
chcp 65001
:: Get the current directory
set "currentDir=%cd%"

echo "开始构建"
call mvn -Dmvn.test.skip=true clean package

echo "构建完成"
cd target
tar -zxvf jtreesize-1.0-SNAPSHOT-jtreesize.tar.gz
cd /d jtreesize-1.0-SNAPSHOT
jpackage --input . --name jtreesize --main-jar lib/jtreesize-1.0-SNAPSHOT.jar --main-class org.ligson.jtreesize.App --type exe --resource-dir conf --icon conf/treesize.ico --win-dir-chooser --win-shortcut

:: Check if the dist folder exists
if exist "%currentDir%\dist" (
    echo "dist folder exists. Deleting contents..."
    rd /s /q "%currentDir%\dist"
    mkdir "%currentDir%\dist"
) else (
    echo "dist folder does not exist. Creating..."
    mkdir "%currentDir%\dist"
)
MOVE jtreesize-1.0.exe "%currentDir%\dist"
echo "构建文件是：%currentDir%\dist\jtreesize-1.0.exe"
cd %currentDir%

