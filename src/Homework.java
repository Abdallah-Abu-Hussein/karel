import stanford.karel.SuperKarel;

class HomeWork extends SuperKarel {

    int width;
    int height;
    int movesCounter;
    boolean IsHeightTwo;
    boolean IsHeightOne;

    public void run() {
        analyzeAndBuild();
    }

    private void analyzeAndBuild() {
        if (frontIsBlocked()){
            set_height();
            if (height > 2 && height <= 9) {
                turnAround();
                fillAlternatingLine();
            }
            //width is one height is n where n > 2
            else {
                turnAround();
                fillAlternatingLineWithNeglected(height);
            }
        }
        else{
            checkIfHeightIsNotOneOrTwo();
            set_width();
        }

        // when width = 2 special case
        if (width == 2 && !IsHeightOne){
            turnLeft();
            fillAlternatingLine();
            turnLeft();
            moveAndCount();
            turnLeft();
            fillAlternatingLine();
        }

        // if height is one works with both odd and even
        if (IsHeightOne){
            turnAround();
            fillAlternatingLineWithNeglected(width);
        }
        //height 2 special case
        if (IsHeightTwo){
            System.out.println("I am here");
            turnAround();
            fillAlternatingLine();
            turnRight();
            moveAndCount();
            turnRight();
            fillAlternatingLine();
        }


        if (width > 2 && !IsHeightTwo && ! IsHeightOne) {
            buildFourChambers();
        }
        System.out.println("Height : " + height);
        System.out.println("Total Moves : " + movesCounter);
    }

    void checkIfHeightIsNotOneOrTwo() {
        turnLeft();
        if (frontIsBlocked()) {
            System.out.println("Height is one ");
            turnRight();
            IsHeightOne = true ;
            height = 1;
        } else if (frontIsClear()) {
            // move to next squar
            moveAndCount();
            if (frontIsBlocked()) {
                // check blocked after moving one square, height is 2
                IsHeightTwo = true;
                height=2;
            }
            // Turn back to return to the starting position
            turnAround();
            moveAndCount();
            turnLeft();
        }
    }

    void fillAlternatingLineWithNeglected(int length) {
        int chambers = 4; // Divide the line into 4 chambers
        int chamberSize = length / chambers; // Size of each chamber

        int counter = 0; // Tracks position
//        boolean placeBeeper = false; // Start without placing a beeper

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

    private void set_height() {
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
        }
        else {
            System.out.println("Height Special Case !" + height);

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

    private void createOneColumn() {
        int counter = 1;
        turnRight();
        if (frontIsBlocked()) {
            heightSpecialCase();
        }
        else {
            while (frontIsClear() & height !=2){
                counter++;
                putBeeperSafely();
                moveAndCount();
            }
            putBeeperSafely();
            this.height = counter;
            if (height == 2){
                IsHeightTwo = true;
                return;
            }
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

    }
    private void heightSpecialCase() {
        goToWall();
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