package talkTalk;

import utils.Debug;

public class TalkTalk {
	public static String pseudo;
	public static void main(String[] args) {
		Debug.start(args);
		Debug.addLog("*** START ***");
		/*if (args.length>0) {
			pseudo=args[0];
		} else */pseudo="namelessTalk";
		System.out.println("Hello "+pseudo+" !");
	}
}