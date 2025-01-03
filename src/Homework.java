import stanford.karel.*;

public class Homework extends SuperKarel {
    int width;
    int height;
    int movesCounter;
    boolean IsHeightTwo;
    boolean IsHeightOne;

    public void run() {
        analyzeAndBuild();
    }

    private void analyzeAndBuild() {
        checkAndBuildSpecialCases();

        // Here I checked for only the height special cases since I do not want
        // to waste moves getting the full height when I can get it from building the columns
        if (width > 2 && !IsHeightTwo && !IsHeightOne) {
            buildFourChambers();
        }
        System.out.println("Height : " + height);
        System.out.println("Total Moves : " + movesCounter);
    }

    void checkAndBuildSpecialCases() {
        checkForWidthSpecialCases();
        checkForHeightSpecialCases();
        // for one chamber case
        if ((IsHeightOne && width == 1) ||
                (IsHeightOne && width == 2) ||
                (IsHeightTwo && width == 1)) {
            System.out.println("Nothing to do");
        }
    }

    void checkForHeightSpecialCases() {
        // if height is one special case
        if (IsHeightOne && width > 2 && width <= 8) {
            turnAround();
            fillAlternatingLine();
        }
        else if(IsHeightOne && width > 8){
            turnAround();
            fillAlternatingLineWithNeglected(width);
        }
        //height 2 special case
        else if (IsHeightTwo && width > 2 && width < 8) {
            turnAround();
            fillAlternatingLine();
            turnRight();
            moveAndCount();
            turnRight();
            fillAlternatingLine();
        }

        else if (IsHeightTwo && width > 8) {
            turnAround();
            fillAlternatingLineWithNeglected(width);

            // go to the second column
            turnRight();
            moveAndCount();
            turnRight();
            goToWall();
            turnRight();
            turnRight();

            fillAlternatingLineWithNeglected(width);
        }

    }

    void checkForWidthSpecialCases() {
        // check for width = 1 special case
        if (frontIsBlocked()) {
            set_height();
            if (height > 2 && height <= 8) {
                turnAround();
                fillAlternatingLine();
            }
            //width is one and height is n where n > 2
            else if (frontIsBlocked() && height > 8)  {
                turnAround();
                fillAlternatingLineWithNeglected(height);
            }
        } else {
            //the width is not one special case it
            //checks if height is a special case the
            //then set width
            checkIfHeightIsNotOneOrTwo();
            set_width();
        }

        // when width = 2 special case
        if (width == 2 && !IsHeightOne) {
            set_height();
            if (height >= 2 && height <= 9) {
                turnAround();
                fillAlternatingLine();
                turnRight();
                moveAndCount();
                turnRight();
                fillAlternatingLine();
            } else {
                turnAround();
                fillAlternatingLineWithNeglected(height);
                // go to the second column
                turnRight();
                moveAndCount();
                turnRight();
                goToWall();
                turnRight();
                turnRight();
                //fill the second column
                fillAlternatingLineWithNeglected(height);
            }
        }
    }

    void checkIfHeightIsNotOneOrTwo() {
        turnLeft();
        if (frontIsBlocked()) {
            System.out.println("Height is one ");
            turnRight();
            IsHeightOne = true;
            height = 1;
        } else if (frontIsClear()) {
            // move to next squar
            moveAndCount();
            if (frontIsBlocked()) {
                // check blocked after moving one square, height is 2
                IsHeightTwo = true;
                height = 2;
            }
            // Turn back to return to the starting position
            turnAround();
            moveAndCount();
            turnLeft();
        }
    }

    // used when the width or height is > than 9
    void fillAlternatingLineWithNeglected(int length) {
        int chambers = 4; // used to divide the line into 4 chambers
        int chamberSize = length / chambers; // full Size of chambers combined

        int counter = 0; // Tracks position
        System.out.println("chamberSize With beepers = " + chamberSize);
        System.out.println("The Full Size of not neglected cells = "+"\n"+"Chambers * chamberSize = " + chambers*chamberSize);
        while (counter < length) {
            // Place beepers in chamber boundaries or handle extra squares
            if (counter % chamberSize == 0 || counter >= (chambers * chamberSize)) {
                System.out.println("Placed a beeper at Counter : " + counter);
                putBeeperSafely(); // Place a beeper at chamber boundaries or extra squares
            }
            // Move to the next position
            counter++;
            if (frontIsClear()) {
                moveAndCount();
            }
        }
    }

    void fillAlternatingLine() {
        boolean placeBeeper = true; // Start with placing a beeper
        while (frontIsClear()) {
            if (placeBeeper) {
                putBeeperSafely();
            }
            moveAndCount();
            // Change between placing and not placing a beeper
            placeBeeper = !placeBeeper;
        }
        // Place a beeper on the last position if needed
        if (placeBeeper && !beepersPresent()) {
            putBeeperSafely();
        }
    }

    void set_height() {
        checkIfHeightIsNotOneOrTwo();
        if (!IsHeightOne && !IsHeightTwo) {
            int counter = 0;
            turnLeft();
            while (frontIsClear()) {
                moveAndCount();
                counter++;
            }
            //for the last step
            counter++;
            this.height = counter;
            System.out.println("Height : " + height);
        } else {
            System.out.println("Height Special Case ! : " + height);

        }
    }

    private void buildFourChambers() {
        if (width % 2 == 0) {
            goToMid(width);
            createTwoColumns();
        } else {
            goToMid(width);
            createOneColumn();
        }
        if (!IsHeightTwo && this.height % 2 == 0) {
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

    private void createOneRow() {
        int counter = 1;
        while (counter < this.width) {
            putBeeperSafely();
            moveAndCount();
            if (frontIsBlocked()) {
                turnAround();
            }
            putBeeperSafely();
            counter++;
        }
    }

    // Create two horizontal rows for even height
    private void createTwoRows() {
        createOneRow();

        turnLeft();
        putBeeperSafely();
        moveAndCount();
        turnRight();

        createOneRow();
        putBeeperSafely();
    }

    private void createOneColumn() {
        int counter = 1;
        turnRight();
        while (frontIsClear() & height != 2) {
            counter++;
            putBeeperSafely();
            moveAndCount();
        }
        putBeeperSafely();
        this.height = counter;
        if (height == 2) {
            IsHeightTwo = true;
        }
    }

    private void createTwoColumns() {
        int counter = 1;
        turnRight();
        while (frontIsClear()) {
            counter++;
            putBeeperSafely();
            moveAndCount();
        }

        this.height = counter;
        if (height == 2)
            IsHeightTwo = true;
        System.out.println("Height : " + this.height);
        putBeeperSafely();
        turnRight();
        moveAndCount();
        turnRight();
        while (frontIsClear()) {
            putBeeperSafely();
            moveAndCount();
        }
        putBeeperSafely();
    }


    void goToWall() {
        while (frontIsClear()) {
            moveAndCount();
        }
    }

    void set_width() {
        int counter = 1;
        while (frontIsClear()) {
            move();
            counter++;
        }
        this.width = counter;
        System.out.println("Width : " + width);

    }

    void putBeeperSafely() {
        if (!beepersPresent())
            putBeeper();
    }

    void moveAndCount() {
        move();
        movesCounter++;

    }

    void goToMid(int size) {
        if (size % 2 == 0) {
            // For even sizes
            int mid = size / 2;
            turnAround();
            for (int i = 1; i <= mid; i++) {
                moveAndCount();
            }
            System.out.println("Even Size Midpoint Reached at Step: " + mid);
        } else {
            // For odd sizes
            int mid = (size / 2) + 1;
            turnAround();
            for (int i = 1; i < mid; i++) {
                moveAndCount();
            }
            System.out.println("Odd Size Midpoint Reached at Step: " + mid);
        }
    }
}

