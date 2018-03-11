package fr.crashkiller.queue;

import net.cubespace.Yamler.Config.InvalidConfigurationException;

public class SaveTask implements Runnable {

	private Main main;
	
	public SaveTask(Main main) {
        this.main = main;
    }
	@Override
	public void run() {
		try {
			main.getRSConfig().save();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
