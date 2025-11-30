#!/bin/bash

# Hogwarts Archive - Build and Run Script

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🧙‍♂️ Hogwarts Archive System${NC}"
echo "================================"

# Create output directory
mkdir -p out

# Compile
echo -e "${YELLOW}Compiling...${NC}"
javac -d out src/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Compilation successful!${NC}"
    echo ""
    echo -e "${YELLOW}Starting Hogwarts Archive...${NC}"
    echo "Type 'COMMANDS' for help, 'EXIT' to quit"
    echo "================================"
    echo ""
    
    # Run
    java -cp out HogwartsArchive
else
    echo "✗ Compilation failed!"
    exit 1
fi
