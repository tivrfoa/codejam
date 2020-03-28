#!/usr/bin/env bash

# python interactive_runner.py python testing_tool.py 1 -- java Solution
# python interactive_runner.py python testing_tool.py 4 -- java Solution

echo 'Testing ' $1

python interactive_runner.py python testing_tool.py $1 -- java Solution
