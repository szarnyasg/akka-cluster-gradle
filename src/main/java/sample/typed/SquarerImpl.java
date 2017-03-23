package sample.typed;

public class SquarerImpl implements Squarer {

	private String name;
	private Integer myI;

	public SquarerImpl() {
		this.name = "default";
	}

	public SquarerImpl(String name) {
		this.name = name;
	}

	@Override
	public void squareNow(int i) {
		System.out.println("X");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Y");
		myI = i * i;
	}

	@Override
	public int getState() {
		return myI;
	}

	@Override
	public void doSg() {
		System.out.println(myI);
	}

}
