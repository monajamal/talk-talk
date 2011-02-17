package rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class MainRMI_copy {
	
	public static void main(String[] args) {
		Thread serveur = new Thread() {
			public void run() {
				System.out.println("EXECUTE : java rmi.Serveur");
				String[] commande = {"java","rmi.Serveur"};
				Runtime runtime = Runtime.getRuntime();
				Process process;
				try {
					process = runtime.exec(commande);
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while((line = reader.readLine()) != null) {
						System.out.println(line);
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String[] cmd3 = {"java","rmi.Serveur"};
				final Runtime runtime = Runtime.getRuntime();
				Thread th3 = new Thread() {
					public void run() {
						System.out.println("Execute : java rmi.Serveur");
						Process process1 = null;
						execute(runtime,process1,cmd3);
					}
				};
				th3.start();
			}
		};
		Timer t = new Timer("plop");
		t.schedule(tt, 3000);
		
		final String[] cmd1 = {"rmic","rmi.MessageImpl"};
		final String[] cmd2 = {"rmiregistry"};
		final String[] cmd3 = {"java","rmi.Serveur"};
		final String[] cmd4 = {"java","rmi.Client","localhost"};
		final String[] cmd5 = {"help"};
		
		System.out.println("DEBUT");
		final Runtime runtime = Runtime.getRuntime();
		/*Process process1 = null;
		Process process2 = null;
		Process process3 = null;*/
		
		
		/*Thread th1 = new Thread() {
			public void run() {
				System.out.println("Execute : rmic rmi.MessageImpl");
				Process process1 = null;
				execute(runtime,process1,cmd1);
			}
		};*/
		Thread th2 = new Thread() {
			public void run() {
				System.out.println("Execute : rmiregistry");
				Process process1 = null;
				execute(runtime,process1,cmd2);
			}
		};
		Thread th3 = new Thread() {
			public void run() {
				System.out.println("Execute : java rmi.Serveur");
				Process process1 = null;
				execute(runtime,process1,cmd3);
			}
		};
		/*Thread th4 = new Thread() {
			public void run() {
				System.out.println("Execute : java rmi.Client localhost");
				Process process1 = null;
				execute(runtime,process1,cmd4);
			}
		};*/
		/*Thread th5 = new Thread() {
			public void run() {
				System.out.println("Execute : help");
				Process process1 = null;
				execute(runtime,process1,cmd5);
			}
		};*/
		th2.start();
		//th3.start();
		//th4.start();
		//th5.start();
		
		//System.out.println("Execute : rmic rmi.MessageImpl");
		//execute(runtime,process1,cmd1);
		//System.out.println("Execute : rmiregistry");
		//execute(runtime,process1,cmd2);
		//System.out.println("Execute : java rmi.Serveur");
		//execute(runtime,process2,cmd3);
		//System.out.println("Execute : java rmi.Client localhost");
		//execute(runtime,process3,cmd4);
		System.out.println("FIN");
	}
	public static void execute(Runtime runtime,Process process,String[] commande) {
		try {
			process = runtime.exec(commande);
			affSortie(process);
			//affErreur(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void affSortie(Process process) throws IOException {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}
	public static void affErreur(Process process) throws IOException {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		while((line = reader.readLine()) != null) {
			System.err.println(line);
		}
		reader.close();
	}
}