package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Constantes {
	
	public static final String DOS_SCRIPTS="scripts\\";
	public static final String FICH_SCRIPT_WINDOWS="scripts\\exec.bat";
	public static final String FICH_SCRIPT_LINUX="scripts\\exec.sh";
	
	public static final String DOS_UPDATES="updates\\";
	
	public static final String DOS_TMP="tmp\\";
	
	public static void clean(String version) {
		File f;
		f = new File(Constantes.DOS_SCRIPTS);if (!f.exists()) f.mkdir();
		f = new File(Constantes.FICH_SCRIPT_WINDOWS);
		if (!f.exists()) {
			try {
				f.createNewFile();
				//Ecriture dans le fichier
				PrintWriter fout;
				fout = new PrintWriter(new FileWriter(Constantes.FICH_SCRIPT_WINDOWS,false));
				fout.println("::Version:"+version);
				fout.println();
				fout.println("::Executer des commandes pour Windows");
				fout.println("@echo off");
				fout.println();
				fout.println("if not exist %1 goto fin");
				fout.println("%1");
				fout.println(":fin");
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		f = new File(Constantes.FICH_SCRIPT_LINUX);
		if (!f.exists()) {
			try {
				f.createNewFile();
				//Ecriture dans le fichier
				PrintWriter fout;
				fout = new PrintWriter(new FileWriter(Constantes.FICH_SCRIPT_LINUX,false));
				fout.println("::Version:"+version);
				fout.println();
				fout.println("::Executer des commandes pour Linux");
				fout.println("@echo off");
				fout.println();
				fout.println("if not exist %1 goto fin");
				fout.println("%1");
				fout.println(":fin");
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//f = new File(Constantes.DOS_UPDATES);if (!f.exists()) f.mkdir();
		//f = new File(Constantes.DOS_TMP);if (!f.exists()) f.mkdir();
		//OperationsFichiers.deleteContentsOfDirectory(new File(Constantes.DOS_TMP));
	}
}