# Karel Chamber Division

## Description
This program divides a grid into chambers using Karel the Robot. It handles special cases for grids with unusual dimensions (e.g., height = 1 or 2) while minimizing movement and beeper placement.

## Features
- Divides grids into four chambers.
- Handles edge cases like single-row and single-column grids.
- Optimized for efficiency in movement and beeper usage.

## Usage
1. Place Karel at the starting position.
2. Run the program.
3. Observe the grid divided into chambers.

## Input
- Grid dimensions (width and height) are determined dynamically by Karel.

## Output
- Beeper placement divides the grid into chambers.
- Logs include total moves and grid dimensions.

## Special Cases
- Single-row grids.
- Single-column grids.
- Grids with height = 2.

## Requirements
- Stanford Karel Framework.

## How to Run
1. Place the code in a class extending `SuperKarel`.
2. Compile and run using the Stanford Karel IDE.

## Example Run Outputs
### Case 1: `5x5` Grid
- Divides into four chambers with single row and column dividers.
- Outputs:
  - `"Width: 5"`
  - `"Height: 5"`
  - `"Total Moves: <count>"`

### Case 2: `1x8` Grid
- Places alternating beepers with optimized neglected squares.
- Outputs:
  - `"Width: 1"`
  - `"Height: 8"`
  - `"Total Moves: <count>"`

