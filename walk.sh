#!/usr/bin/env bash

./gradlew build shadowJar

watch --interval=3600 java -Djava.awt.headless=true -Dapple.awt.UIElement=true -jar build/libs/insta-0.1.0-all.jar insta.Do