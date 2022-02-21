#!/usr/bin/env bash

#java -jar build/libs/insta-0.1.0-all.jar insta.Do

./gradlew build shadowJar
java -Djava.awt.headless=true -Dapple.awt.UIElement=true -jar build/libs/insta-0.1.0-all.jar insta.Do