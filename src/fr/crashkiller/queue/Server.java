package fr.crashkiller.queue;

import java.util.ArrayList;

public class Server {
	
	private int maxPlayer;
	private ArrayList<String> waitlist = new ArrayList<String>();
	private boolean online;

	public Server(int maxPlayer, boolean online) {
		this.maxPlayer = maxPlayer;
		this.online = online;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}
	
	public void setMaxPlayer(int max) {
		this.maxPlayer = max;
	}

	public ArrayList<String> getWaitlist() {
		return waitlist;
	}

	public void setWaitlist(ArrayList<String> waitlist) {
		this.waitlist = waitlist;
	}
	
	public void addWaitList(String name) {
		this.waitlist.add(name);
	}

	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean bool) {
		this.online = bool;
	}
	
}
