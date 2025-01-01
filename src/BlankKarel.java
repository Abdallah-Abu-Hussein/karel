import stanford.karel.*;

public class BlankKarel extends SuperKarel {
    int width;
    int height;
    int movesCounter;
    boolean evenWidthFlag;

    public void run() {

        if (frontIsBlocked())
            width = 1 ;
        else
            get_width();

        System.out.println("Width : " + width);
        if (checkIfHeightIsOne()){
            height = 1;
            System.out.println("Checked Height " + height);
        }


        //if width is one
        if (width == 1) {
            get_height();
            if (height > 2 && height <= 9) {
                turnAround();
                fillAlternatingLine();
            } else {
                turnAround();
                fillAlternatingLineWithNeglected(height);
            }
        }

        // when width = 2 special case
        if (width == 2 &&  !checkIfHeightIsOne()){
            turnLeft();
            fillAlternatingLine();
            turnLeft();
            moveAndCount();
            turnLeft();
            fillAlternatingLine();
        }

        // if height is one works with both odd and even
        if (checkIfHeightIsOne()){
            turnAround();
            fillAlternatingLineWithNeglected(width);
        }


        if (width > 2 && !checkIfHeightIsOne()
        ) {
            buildFourChambers();
        }

        System.out.println("Total Moves : " + movesCounter);
    }

    boolean checkIfHeightIsOne() {
        turnLeft();
        if (frontIsBlocked()) {
            System.out.println("Height is one ");
            turnRight();
            return true;
        }
        turnRight();
        return false;
    }

    void fillAlternatingLineWithNeglected(int length) {
        int chambers = 4; // Divide the line into 4 chambers
        int chamberSize = length / chambers; // Size of each chamber

        int counter = 0; // Tracks position
        boolean placeBeeper = false; // Start without placing a beeper

        while (counter < length) {
            // Place beepers in chamber boundaries or handle extra squares
            if (counter % chamberSize == 0 || counter >= (chambers * chamberSize)) {
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
            placeBeeper = !placeBeeper; // Alternate between placing and not placing a beeper
        }
        // Place a beeper on the last position if needed
        if (placeBeeper && !beepersPresent()) {
            putBeeperSafely();
        }
    }

    private void get_height() {
        int counter = 0;
        turnLeft();
        while (frontIsClear()) {
            moveAndCount();
            counter++;
        }
        counter++; //for the last step+
        this.height = counter;
        System.out.println("Height : " + height);
    }

    private void buildFourChambers() {
        if (width % 2 == 0) {
            goToMid(width);
            createTwoColumns();
        } else {
            goToMid(width);
            createOneColumns();
        }
        if (this.height % 2 == 0) {
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
        while (counter < this.width ) {
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

//    // Move to the nearest wall
//    private void moveToWall() {
//        while (frontIsClear()) {
//            moveAndCount();
//        }
//    }

    private void createOneColumns() {
        int counter = 1;
        turnRight();
        if (frontIsBlocked()) {
            heightSpecialCase();
        }
        else {
            while (frontIsClear()){
                counter++;
                putBeeperSafely();
                moveAndCount();
            }
            putBeeperSafely();
            this.height = counter;
            System.out.println("Height : "+ this.height);
        }

    }

    private void heightSpecialCase() {
        goToWall();
    }

    void goToWall() {
        while (frontIsClear()) {
            moveAndCount();
        }
    }

    private void createTwoColumns() {
        int counter = 1;
        turnRight();
        if (frontIsBlocked()) {
            heightSpecialCase();
        } else {
            while (frontIsClear()) {
                counter++;
                putBeeperSafely();
                moveAndCount();
            }
            this.height = counter;
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

    }

    void get_width() {
        int counter = 1;
        while (frontIsClear()) {
            move();
            counter++;
        }
        this.width = counter;
        System.out.println("Width : " + width);
        if (width % 2 == 0)
            evenWidthFlag = true;
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

