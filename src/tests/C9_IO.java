package tests;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class C9_IO {
	public static void main(String[] args) {
		testFile();
		testFileWriter();
		testFileReader();
		testPrintWriter();
		testBufferedReader();
		// testConsole(); // execute only with console, comment the other function's calls
	}
	
	// Test File Class
	// Rule: A new instance of class File not create a new file.
	// Constructor: File(Strig filename or dirname)
	// Constructor: File(File dir, String filename)
	// Method: exists()
	// Method: createNewFile() return boolean => IOException
	// Method: mkdir()
	// Method: delete() => files and empty directories
	// Method: renameTo(File newname)
	// Method: isFile()
	// Method: isDirectory()
	// Method: list() => used in directories, return a String[]
	public static void testFile() {
		System.out.println("Test File Class");

		File fileRm1 = new File("tmp/renameFileTest1.txt");
		
		// create files references inside the tmp directory
		File fileDel1 = new File("tmp/fileTest1.txt");
		File fileDel2 = new File("tmp/fileTest2.txt");
		
		// rename a old file 
		fileDel1.renameTo(fileRm1);
		
		// delete files
		fileDel1.delete();
		fileDel2.delete();
		
		// you only can delete a directory if it's empty
		File dirDel1 = new File("tmp");
		boolean dropDir1 = dirDel1.delete();
		System.out.println("is diretory deleted: " + dropDir1);
		
		// create a reference to a directory
		File dir1 = new File("tmp");
		System.out.println("is tmp a directory: " + dir1.isDirectory());
		
		// create a directory
		boolean newDir1 = dir1.mkdir();
		System.out.println("is a new dir created: " + newDir1);
		
		// create references to files 
		File f1 = new File("tmp/fileTest1.txt");	
		File f2 = new File(dir1, "fileTest2.txt");
		
		System.out.println("exist fileTest1.txt: " + f1.exists());
		System.out.println("is fileTest1.txt a file: " +f1.isFile());
		
		try {
			// return false if the file already exist
			boolean newf1 = f1.createNewFile();
			boolean newf2 = f2.createNewFile();
			
			System.out.println("is a new file created: " + newf1);
		}
		catch (IOException e) {
			System.out.println("file can't be created");
		}
		
		String[] filesNames = new String[100];
		filesNames = dir1.list();
		System.out.println("Seach in directory");
		for (String file: filesNames) {
			System.out.println("found: " + file);
		}
	}
	
	// Test FileWriter Class
	// Constructor: FileWriter(File f) => IOException
	// Method: write(String str)
	// Method: flush()
	// Method: close()
	public static void testFileWriter() {
		System.out.println("\nTest FileWriter Class");
		
		File f1 = new File("fileWriterTest1.txt");
		
		try {
			FileWriter fw1 = new FileWriter(f1);
			fw1.write("howdy\nfolks\n");
			fw1.flush();
			fw1.close();
		}
		catch (IOException e) {
		}
	}
	
	// Test FileReader Class
	// Constructor: FileReader(File f) => FileNotFoundException
	// Method: read(char[] input) return int size => IOException
	// Method: close()
	public static void testFileReader() {
		System.out.println("\nTest FileReader Class");
		
		char[] input = new char[50];
		
		File f1 = new File("fileWriterTest1.txt");

		try {
			FileReader fr1 = new FileReader(f1);
			try {
				int size = fr1.read(input);
				System.out.println("File size: " + size);
				for (char c: input) {
					System.out.print(c);
				}
			}
			catch (IOException e) {
			}
			
			try {
				fr1.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
		}
	}
	
	public static void testPrintWriter() {
		File f1 = new File("filePrintWriterTest1.txt");
		try {
			PrintWriter pw1 = new PrintWriter(f1);
			pw1.println("howdy");
			pw1.println("folks");
			// it's necessary close the resource to save the text in the file
			pw1.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void testBufferedReader() {
		System.out.println("\nTest BufferedReader Class");
		
		File f1 = new File("fileWriterTest1.txt");
		try {
			FileReader fr1 = new FileReader(f1);
			BufferedReader br1 = new BufferedReader(fr1);
			try {
				String s;
				while ((s = br1.readLine()) != null) {
					System.out.println(s);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
	}
	
	// Test Console Class
	// Constructor: System.console() => it can be null if you aren't use the console to execute the program
	// Method: readLine(String rexexp, String output)
	// Method: readPassword(String rexexp, String output) => return char[]
	// Method: format(String output, String[] args)
	public static void testConsole() {
		System.out.println("Test Console Class");
		
		// create console object, it can be null
		Console cs = System.console();

		// read password and line
		char[] pw = cs.readPassword("%s", "pw: ");
		String in = cs.readLine("%s", "input: ");

		// print password and line
		for (char ch: pw) {
			cs.format("%c ", ch);
		}
		cs.format("\n");
		cs.format("output: %s %s \n", in, in);
	}
	
	
}
