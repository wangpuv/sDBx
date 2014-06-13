@echo off
echo [INFO] generate all xsd mapping java class by maven.

cd %~dp0
cd ../..

mvn generate-sources -Pxsd-generate-java

cd bin/xsd_generate_java
pause