import stanford.karel.*;

public class BlankKarel extends SuperKarel {

	// get width
	// on the way of going back
	// go to the middle
	//if mid is for an even width we need to create two columns
	//if mid is odd then we need the
	// mid = num / 2 + 1 to create four equal chambers

	//beepers
	// get width
	//go back to mid based on width value
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

		goToMid();

//		if (evenWidthFlag){
//			createTwoColmuns();
//		}else {
//		}

//		createOneColmuns();

//		go_toMiddle_From_Width();
//
//		get_height_buildColumn();

//		goToMidOnCol();
////
//		buildMidRow();
////
//		goToMid();
////
//		buildMidRow();
//
//		goToMid();
//		move();
//		turnLeft();

//		while (frontIsClear()){
//			move();
//			putbeepers();
//		}
//		if (frontIsBlocked())
//			goToMid();
//		while (frontIsClear()){
//			move();
//			putbeepers();
//		}


	}

	private void createOneColmuns() {
		goToMid();
//		turnRight();
//		while (frontIsClear()){
//			putbeepers();
//			move();
//		}
	}

	private void createTwoColmuns() {
		goToMid();
		turnRight();
		while (frontIsClear()){
			putbeepers();
			move();
		}
		putbeepers();
		turnRight();
		move();
		turnRight();

		while (frontIsClear()){
			putbeepers();
			move();
		}
	}

	void go_toMiddle_From_Width(){
		int counter = 0;
		int mid ;
		if (!evenWidthFlag)
			mid = (width / 2) + 1;
		else
			mid = width / 2;
		System.out.println("Mid : "+ mid);
		turnAround();
		while (counter != mid){
			counter++;
			move();
		}
		turnAround();
	}
turnAround

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

	void get_height_buildColumn(){
		int counter = 1;
		putbeepers();
		turnLeft();
		while (frontIsClear()){
			counter++;
			move();
			putbeepers();
		}

		this.height = counter;
		System.out.println("Height : "+ height);
		if (this.height == this.width)
			sameWidthHeightFlag = true;
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
	void buildMidRow(){
		while (frontIsClear()){
			move();
			putbeepers();
		}
	}

	void goToMid(){
		int counter = 0;
		int mid ;
		if (width % 2 == 0)
			mid = width / 2;
		else{
			mid = (width+1) / 2;
			System.out.println("Width inside : " + mid);
		}

		System.out.println("Mid " + mid);
		turnAround();
		while (counter < mid){
			move();
			counter++;
		}
	}

}











