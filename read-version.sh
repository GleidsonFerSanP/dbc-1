#!/bin/sh

PACKAGE_VERSION=$(grep -m1 version version.json | awk -F: '{ print $2 }' | sed 's/[", ]//g')
echo $PACKAGE_VERSION | tr -d '\r'