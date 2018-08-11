package Chatt;

/**
 * This class is used for testing the application
 * @author Henrik Olofsson
 *
 */
public class TestChatt {
	public static void main(String[] args) {
		ChattWindow window = new ChattWindow("Chatt");
		ConnectedUI cui = new ConnectedUI();
		StartScreenUI ssui = new StartScreenUI();
		UserListLayout ull = new UserListLayout();
		window.add(ssui);
		window.pack();
	}

}
