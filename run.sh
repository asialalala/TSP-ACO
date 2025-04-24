#!/bin/bash

PLOT=false

# Parse flag
if [[ "$1" == "-p" ]]; then
  PLOT=true
fi

result=$(java -cp out/production/TSP-ACO implementation.App "$@")

if [[ -z "$result" ]]; then
    echo "Error: No output received from Java program!"
    exit 1
fi

filename=$(echo $result | awk '{print $1}')
path=$(echo "$result" | awk '{print $2}')

if [ "$PLOT" = true ]; then
  python3 Visualizer/main.py "$filename" "$path"
fi
