#!/bin/sh
sed -i '/if \[ -n \"\$dns\" \]/a     dns=\"$dns 8.8.8.8\"' /usr/share/udhcpc/default.script
#sed -i '/if \[ -n \"\$domain\" \]/a     domain=\"$domain dev\"' /usr/share/udhcpc/default.script

### cicd push를 위한 변화 주기 -> ubuntu에 docker설치

### cicd push를 위한 변화 주기 -> ubuntu에 docker권한

### cicd push를 위한 변화 주기 -> application 로그인권한

### cicd push를 위한 변화 주기 -> yml 간격

### cicd push를 위한 변화 주기 -> db 확인

### cicd push를 위한 변화 주기 -> application ddl option

### cicd push를 위한 변화 주기 -> mvc 추가

### cicd push를 위한 변화 주기 -> application ddl option

### cicd push를 위한 변화 주기 -> application database path

### cicd push를 위한 변화 주기 -> application database path - 스키마까지

### cicd push를 위한 변화 주기 -> ec2 3306 port 추가

### cicd push를 위한 변화 주기 -> application database path - 끝까지

### cicd push를 위한 변화 주기 -> ddl create -> update

### cicd push를 위한 변화 주기 -> 여러가지 설정

### cicd push를 위한 변화 주기