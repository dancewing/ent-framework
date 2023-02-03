#!/bin/bash
find . -name ".settings" -exec rm -rf {} \;
find . -name ".classpath" -exec rm -rf {} \;
find . -name ".factorypath" -exec rm -rf {} \;
find . -name ".project" -exec rm -rf {} \;
find . -name ".apt_generated" -exec rm -rf {} \;
find . -name ".apt_generated_tests" -exec rm -rf {} \;