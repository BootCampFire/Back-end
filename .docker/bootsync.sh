#!/bin/sh
sed -i '/if \[ -n \"\$dns\" \]/a     dns=\"$dns 8.8.8.8\"' /usr/share/udhcpc/default.script
#sed -i '/if \[ -n \"\$domain\" \]/a     domain=\"$domain dev\"' /usr/share/udhcpc/default.script

### cicd push를 위한 변화주기 -> ubuntu에 docker설치

### cicd push를 위한 변화 주기 -> ubuntu에 docker권한