#!/bin/bash

PLOT=false

if [ $# -lt 1 ]; then
  echo "Usage: $0 <path_to_file> [-p]"
  exit 1
fi

filepath="$1"
shift

if [[ "$1" == "-p" ]]; then
  PLOT=true
  shift
fi

args=("$filepath")
if [ "$PLOT" = true ]; then
  args+=("-p")
fi

result=$(java -cp out/production/TSP-ACO implementation.App "${args[@]}")

if [[ -z "$result" ]]; then
    echo "Error: No output received from Java program!"
    exit 1
fi

if [ "$PLOT" = false ]; then
  echo -e "$result"
fi

if [ "$PLOT" = true ]; then
  filename=$(echo $result | awk '{print $1}')
  path=$(echo "$result" | awk '{print $2}')
  python3 Visualizer/main.py "$filename" "$path"
fi
