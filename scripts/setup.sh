#!/bin/sh

# Add git hooks.
ln -s ../../scripts/hooks/pre-commit .git/hooks/pre-commit 2> /dev/null
chmod +x .git/hooks/pre-commit
