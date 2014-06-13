@echo off
echo [INFO] generate sdbx_ss.xsd mapping java class.

cd %~dp0
cd ../..

xjc -d src/main/java -p org.blue.sdbx.xsd.ss src/main/resources/xsd/sdbx_ss.xsd

cd bin/xsd_generate_java
pause