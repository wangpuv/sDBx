@echo off
echo [INFO] generate sdbx_aa.xsd mapping java class.

cd %~dp0
cd ../..

xjc -d src/main/java -p org.blue.sdbx.xsd.aa src/main/resources/xsd/sdbx_aa.xsd

cd bin/xsd_generate_java
pause