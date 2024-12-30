import stanford.karel.*;

public class BlankKarel extends SuperKarel {

	// get width
	// on the way of going back
	// go to the middle
	//if mid is for an even width we need to create two mid columns (for even width) and two mid rows (for even height)
	//if any of those is odd then only one colmun or row is needed
	//if mid is odd then we need the
	// mid = num / 2 + 1 to create four equal chambers
	// using beepers,
	// on the way of building columns form the mid of width get height  while building the mid columns / column
	// while going back go to mid of the column and only create one middle row
	int width;
	int height;
	int mid;
	boolean evenWidthFlag;
	boolean sameWidthHeightFlag;

	@Override
	public void run() {
		super.run();
		get_width();
		System.out.println("this width : " +  this.width);
		System.out.println("Is even Width : " +  this.evenWidthFlag);
		if (evenWidthFlag){
			createTwoColumns();
		}else {
			createOneColumns();
		}
		if (this.height % 2 ==0){
		goToMid();
		createTwoRows();
		} else{
			goToMid();
			createOneRow();
		}
	}

	private void createOneRow() {
		turnRight();
		int counter = 0;
		while (counter < this.width * 2 - 2){
			putbeepers();
			move();
			if(frontIsBlocked()) {
				turnAround();
			}
			counter++;
		}
	}

	// Create two horizontal rows for even height
	private void createTwoRows() {
		createOneRow();
		turnRight();
		move();
		createOneRow();
	}

	// Move to the nearest wall
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}

	private void createOneColumns() {
		int counter = 1;
		goToMid();
		turnRight();
		while (frontIsClear()){
			counter++;
			putbeepers();
			move();
		}
		putbeepers();
		this.height = counter;
		System.out.println("Height : "+ this.height);
	}

	private void createTwoColumns() {
		int counter = 1;
		goToMid();
		turnRight();
		while (frontIsClear()){
			counter++;
			putbeepers();
			move();
		}
		this.height = counter;
		System.out.println("Height : "+ this.height);
		putbeepers();
		turnRight();
		move();
		turnRight();
		while (frontIsClear()){
			putbeepers();
			move();
		}
		putbeepers();
	}

	 void get_width(){
		int counter = 1;
		while (frontIsClear()){
			move();
			counter++;
		}
		this.width = counter;
		if (width % 2 == 0)
			 evenWidthFlag = true;
	}

	void putbeepers(){
		if (!beepersPresent())
			putBeeper();
	}

	void goToMidOnCol(){
		int counter = 0;
		int mid;

		if (height % 2 == 0)
			mid = height / 2;
		else
			mid = (height / 2) + 1;
		turnAround();
		while (counter < mid){
			  move();
			  counter++;
		}
		turnLeft();
	}

	void goToMid(){
		moveToWall();
		int mid = width / 2; // Midpoint for even or odd sizes
		turnAround();
		for (int i = 0; i < mid; i++) {
			move();
		}
		System.out.println("The mid : " + mid);
	}

}