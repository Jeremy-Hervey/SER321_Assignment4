package performPackage;

import java.net.*;

import java.io.*;

class Performer{

	StringList state;
	Socket sock;
	BufferedReader in = null;
	PrintWriter out = null;

	public Performer(Socket sock, StringList strings) {
		this.sock = sock;
		this.state = strings;
	}

	public void doPerform() {

		try {

			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			out.println("Enter text (. to disconnect):");

			boolean done = false;
			while (!done) {
				String str = in.readLine();

				if (str == null || str.equals("."))
					done = true;
				else {
					state.add(str);
					out.println("Server state is now: " + state.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// add element to list + display list
	void addStringToList(String s) {
		this.state.add(s);
		out.println(state);
	}

	// remove element from specified idx + return null if empty
	StringList removeStringToList(int idx) {
		if (state.strings.isEmpty())
			return null;
		else {
			state.strings.remove(idx);
			return state;
		}
	}

	// display list
	void displayList() {
		out.println(state);
	}

	// returns element string length @ position in idx
	int[] eleCounter() {
		int listSize = state.size();
		int[] arr = new int[listSize];
		for (int idx = 0; idx < listSize; idx++)
			arr[idx] = ((String) state.strings.get(idx)).length();
		return arr;
	}
	
	void reverseString(int idx) {
		StringBuilder sb = new StringBuilder();
		sb.append((String)state.strings.get(idx));
		sb.reverse();
		state.strings.set(idx, sb.toString());
	}
}
