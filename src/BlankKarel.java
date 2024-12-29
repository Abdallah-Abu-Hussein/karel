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
	boolean evenWidthFlag;
	boolean sameWidthHeightFlag;

	@Override
	public void run() {
		super.run();

		get_width();
		System.out.println("this width : " +  this.width);
		System.out.println("Is even : " +  this.evenWidthFlag);

		go_toMiddle_From_Width();

		get_height_buildColumn();

		goToMidOnCol();

		buildMidRow();

		goToMid();

		buildMidRow();

		goToMid();
		move();
		turnLeft();

		while (frontIsClear()){
			move();
			putbeepers();
		}
		if (frontIsBlocked())
			goToMid();
		while (frontIsClear()){
			move();
			putbeepers();
		}


	}

	void go_toMiddle_From_Width(){
		int counter = 0;
		int mid = 0;
		if (!evenWidthFlag)
			mid = (width / 2) + 1;
		else
			mid = width / 2;

		turnAround();
		while (counter < mid){
			move();
			counter++;
		}
		turnAround();
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

	void get_height_buildColumn(){
		int counter = 0;
		putbeepers();
		turnLeft();
		while (frontIsClear()){
			move();
			counter++;
			putbeepers();
		}

		this.height = counter;
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
		int counter = 1;
		int mid ;
		if (height % 2 == 0)
			mid = height / 2;
		else
			mid = (height / 2) + 1;
		turnAround();
		while (counter < mid){
			move();
			putbeepers();
			counter++;
		}
	}

}











