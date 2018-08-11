package Server;

import Client.Message;

public interface ClientListener {

	void MessageReciver(Message m);

	void userConnected(User user);

	void userDisconnected(User user);

}
