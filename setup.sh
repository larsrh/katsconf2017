#!/bin/bash

set -e

echo "Downloading paulp's SBT runner ..."
cd "$(dirname "$0")"

curl -s https://raw.githubusercontent.com/paulp/sbt-extras/master/sbt > ./sbt

chmod 0755 ./sbt

echo "Done!"
echo "Start SBT with ./sbt"
