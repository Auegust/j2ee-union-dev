@@echo off
call mvn clean install -DfileEncoding=UTF-8 -Dmaven.test.skip=true
pause