#!/bin/bash

# 실행 중인 json-converter 컨테이너 ID 가져오기
CONTAINER_ID=$(docker ps -q --filter ancestor=json-converter)

start() {
    # 실행 중인 json-converter 컨테이너 ID 다시 확인
    CONTAINER_ID=$(docker ps -q --filter ancestor=json-converter)

    if [ ! -z "$CONTAINER_ID" ]; then
        echo "JSON Converter 컨테이너가 이미 실행 중입니다. 기존 컨테이너를 삭제하고 새로운 컨테이너를 시작합니다."
        docker stop $CONTAINER_ID
        docker rm $CONTAINER_ID
    fi

    # 로컬에서 8080 포트를 사용 중인 다른 서비스 확인
    if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null ; then
        echo "로컬에서 8080 포트를 사용 중인 다른 서비스가 있습니다. 포트 충돌로 인해 JSON Converter 컨테이너를 시작할 수 없습니다."
    else
        # Gradle 프로젝트 확인
        if [ ! -f "build.gradle" ]; then
            echo "Gradle 프로젝트가 아닙니다. build.gradle 파일이 존재하지 않습니다."
            exit 1
        fi

        if ! ./gradlew build; then
            echo "빌드에 실패하였습니다. 로그를 확인해주세요."
            exit 1
        fi

        # Dockerfile 확인
        if [ ! -f "Dockerfile" ]; then
            echo "Dockerfile이 존재하지 않습니다. Dockerfile을 프로젝트 루트 디렉토리에 생성해주세요."
            exit 1
        fi

        docker build -t json-converter .
        if [ $? -ne 0 ]; then
            echo "Docker 이미지 빌드에 실패하였습니다. Dockerfile을 확인해주세요."
            exit 1
        fi

        # 현재 시각을 포함한 컨테이너 이름 생성
        CONTAINER_NAME="json-converter-$(date +%Y%m%d%H%M%S)"

        docker run -d -p 8080:8080 --name $CONTAINER_NAME json-converter
        echo "JSON Converter 컨테이너($CONTAINER_NAME)를 시작했습니다."
    fi
}

restart() {
    stop
    start
}

stop() {
    if [ ! -z "$CONTAINER_ID" ]; then
        docker stop $CONTAINER_ID
        docker rm $CONTAINER_ID
        echo "JSON Converter 컨테이너를 중지하고 삭제했습니다."
    else
        echo "실행 중인 JSON Converter 컨테이너가 없습니다."
    fi
}

status() {
    if [ ! -z "$CONTAINER_ID" ]; then
        echo "JSON Converter 컨테이너가 실행 중입니다."
        docker ps --filter ancestor=json-converter
        echo "최근 로그:"
        docker logs --tail 10 $CONTAINER_ID
    else
        echo "실행 중인 JSON Converter 컨테이너가 없습니다."
    fi
}

clean_build_and_start() {
    # 실행 중인 컨테이너 확인 및 삭제
    if [ ! -z "$CONTAINER_ID" ]; then
        docker stop $CONTAINER_ID
        docker rm $CONTAINER_ID
        echo "기존 JSON Converter 컨테이너를 중지하고 삭제했습니다."
    fi

    # Gradle 프로젝트 확인
    if [ ! -f "build.gradle" ]; then
        echo "Gradle 프로젝트가 아닙니다. build.gradle 파일이 존재하지 않습니다."
        exit 1
    fi

    ./gradlew clean build
    if [ $? -ne 0 ]; then
        echo "Clean 빌드에 실패하였습니다. 로그를 확인해주세요."
        exit 1
    fi

    # Dockerfile 확인
    if [ ! -f "Dockerfile" ]; then
        echo "Dockerfile이 존재하지 않습니다. Dockerfile을 프로젝트 루트 디렉토리에 생성해주세요."
        exit 1
    fi

    docker build -t json-converter .
    if [ $? -ne 0 ]; then
        echo "Docker 이미지 빌드에 실패하였습니다. Dockerfile을 확인해주세요."
        exit 1
    fi

    # 현재 시각을 포함한 컨테이너 이름 생성
    CONTAINER_NAME="json-converter-$(date +%Y%m%d%H%M%S)"

    docker run -d -p 8080:8080 --name $CONTAINER_NAME json-converter
    echo "전체 Clean 빌드 및 배포가 완료되었습니다."
}

echo "JSON Converter 서비스 관리"
echo "1. 시작"
echo "2. 재시작"
echo "3. 중지"
echo "4. 상태 확인"
echo "9. 전체 Clean 빌드 및 시작"
echo "선택할 작업의 번호를 입력하세요:"
read choice

case $choice in
    1)
        start
        ;;
    2)
        restart
        ;;
    3)
        stop
        ;;
    4)
        status
        ;;
    9)
        clean_build_and_start
        ;;
    *)
        echo "잘못된 선택입니다. 1, 2, 3, 4, 9 중 하나를 선택해주세요."
        exit 1
esac