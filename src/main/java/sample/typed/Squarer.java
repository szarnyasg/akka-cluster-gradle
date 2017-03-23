package sample.typed;

public interface Squarer {

	void squareNow(int i); // blocking send-request-reply

	int getState();

	void doSg();

}
