# Karel Chamber Division
- [Video Link]( https://youtu.be/1s-ANbRO8d8)
- [Report Link](/Assests/Karel_Chamber_Division_Documentation.pdf)
- [Homework.java](/src/Homework.java)


# Note To ensure Correct resluts restart The Program for Each Map

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

------
  ![](/Assests/5x5.png)

### Case 2: `1x8` Grid
- Places alternating beepers with optimized neglected squares.
- Outputs:
  - `"Width: 1"`
  - `"Height: 8"`
  
  ----

- ![](/Assests/8x1.png)

### Case 3: `10x10` Grid 

- ![](/Assests/10x10.png)

### Case 4: `9x9` Grid 

- ![](/Assests/9x9.png)

### Case 5: `5x2` Grid 

- ![](/Assests/5x2.png)

### Case 6: `2x2` Grid 

- ![](/Assests/2x2.png)

### Case 7: `6x5` Grid

- ![](/Assests/6x5.png)

### Case 8: `1x17` Grid

- ![](/Assests/17x1.png)


### Case 9: `2x15` Grid
![](/Assests/2x15.png)


## **Code Breakdown**

### **1. `run` Method**
```java
public void run() {
    analyzeAndBuild();
}
```
**Explanation**:  
The main entry point of the program. It invokes the `analyzeAndBuild` method to analyze the grid dimensions and handle special and general cases.

---

### **2. `analyzeAndBuild` Method**
```java
private void analyzeAndBuild() {
    checkAndBuildSpecialCases();
    if (width > 2 && !IsHeightTwo && !IsHeightOne) {
        buildFourChambers();
    }
    System.out.println("Height : " + height);
    System.out.println("Total Moves : " + movesCounter);
}
```
**Explanation**:  
This method:
1. Handles special cases (width = 1, height = 1 or 2) via `checkAndBuildSpecialCases`.
2. For larger grids, invokes `buildFourChambers` to divide the grid into four chambers.
3. Logs the height and total moves for debugging.

---

### **3. `checkAndBuildSpecialCases` Method**
```java
private void checkAndBuildSpecialCases() {
    if (frontIsBlocked()) {
        set_height();
        if (height > 2 && height <= 9) {
            turnAround();
            fillAlternatingLine();
        } else if (height > 2) {
            turnAround();
            fillAlternatingLineWithNeglected(height);
        }
    } else {
        checkIfHeightIsNotOneOrTwo();
        set_width();
    }

    if (width == 2 && !IsHeightOne) {
        turnLeft();
        fillAlternatingLine();
        turnLeft();
        moveAndCount();
        turnLeft();
        fillAlternatingLine();
    }

    if (IsHeightOne && width > 2) {
        turnAround();
        fillAlternatingLineWithNeglected(width);
    }

    if (IsHeightTwo && width > 2) {
        turnAround();
        fillAlternatingLine();
        turnRight();
        moveAndCount();
        turnRight();
        fillAlternatingLine();
    }

    if ((IsHeightOne && width == 1) || (IsHeightOne && width == 2) || (IsHeightTwo && width == 1)) {
        System.out.println("Nothing to do");
    }
}
```
**Explanation**:  
Handles special cases for:
- Width = 1: Uses alternating beepers or neglected chambers for large heights.
- Width = 2: Creates two vertical columns.
- Height = 1 or 2: Creates one or two horizontal rows as needed.

---

### **4. `checkIfHeightIsNotOneOrTwo` Method**
```java
void checkIfHeightIsNotOneOrTwo() {
    turnLeft();
    if (frontIsBlocked()) {
        System.out.println("Height is one");
        turnRight();
        IsHeightOne = true;
        height = 1;
    } else if (frontIsClear()) {
        moveAndCount();
        if (frontIsBlocked()) {
            IsHeightTwo = true;
            height = 2;
        }
        turnAround();
        moveAndCount();
        turnLeft();
    }
}
```
**Explanation**:  
Determines if the height is a special case:
- **Height = 1**: Sets `IsHeightOne = true`.
- **Height = 2**: Sets `IsHeightTwo = true`.

---

### **5. `fillAlternatingLineWithNeglected` Method**
```java
void fillAlternatingLineWithNeglected(int length) {
    int chambers = 4;
    int chamberSize = length / chambers;

    int counter = 0;

    while (counter < length) {
        if (counter % chamberSize == 0 || counter >= (chambers * chamberSize)) {
            putBeeperSafely();
        }
        counter++;
        if (frontIsClear()) {
            moveAndCount();
        }
    }
}
```
**Explanation**:  
Divides a line into four chambers, placing beepers at boundaries and handling neglected squares.

---

### **6. `fillAlternatingLine` Method**
```java
void fillAlternatingLine() {
    boolean placeBeeper = true;
    while (frontIsClear()) {
        if (placeBeeper) {
            putBeeperSafely();
        }
        moveAndCount();
        placeBeeper = !placeBeeper;
    }
    if (placeBeeper && !beepersPresent()) {
        putBeeperSafely();
    }
}
```
**Explanation**:  
Places alternating beepers along a line, skipping every other square.

---

### **7. `set_height` and `set_width` Methods**
#### `set_height`:
```java
private void set_height() {
    checkIfHeightIsNotOneOrTwo();
    if (!IsHeightOne && !IsHeightTwo) {
        int counter = 0;
        turnLeft();
        while (frontIsClear()) {
            moveAndCount();
            counter++;
        }
        counter++;
        this.height = counter;
        System.out.println("Height : " + height);
    } else {
        System.out.println("Height Special Case ! : " + height);
    }
}
```
**Explanation**:  
Calculates the height of the grid and handles special cases for height = 1 or 2.

#### `set_width`:
```java
void set_width() {
    int counter = 1;
    while (frontIsClear()) {
        move();
        counter++;
    }
    this.width = counter;
    System.out.println("Width : " + width);
}
```
**Explanation**:  
Calculates the width of the grid.

---

### **8. `buildFourChambers` Method**
```java
private void buildFourChambers() {
    if (width % 2 == 0) {
        goToMid(width);
        createTwoColumns();
    } else {
        goToMid(width);
        createOneColumn();
    }
    if (this.height != 2 && this.height % 2 == 0) {
        goToMid(height);
        turnLeft();
        goToWall();
        turnAround();
        createTwoRows();
    } else {
        goToMid(height);
        turnLeft();
        goToWall();
        turnAround();
        createOneRow();
    }
}
```
**Explanation**:  
Divides the grid into four chambers:
- Places one or two vertical columns based on the width.
- Places one or two horizontal rows based on the height.

---

### **9. Helper Methods**
#### `createOneRow` and `createTwoRows`
Handles horizontal rows for even and odd heights.

#### `createOneColumn` and `createTwoColumns`
Handles vertical columns for even and odd widths.

#### `goToWall`, `goToMid`, `putBeeperSafely`, and `moveAndCount`
Utility methods to handle movement, placement, and alignment operations.
