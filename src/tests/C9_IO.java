package tests;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class C9_IO {
	public static void main(String[] args) {
		
		// Section 1: IO
		testFile();
		testFileWriter();
		testFileReader();
		testBufferedReader();
		testPrintWriter();
		// testConsole(); // execute only with console, comment the other function's calls

		// Section 2: NIO
		testPath();
		testFiles();
	
	}
	
	// Test File Class
	// Rule: A new instance of class File not create a new file.
	// Constructor: File(Strig filename or dirname)
	// Constructor: File(File dir, String filename)
	// Method: exists()
	// Method: createNewFile() return boolean => IOException
	// Method: mkdir()
	// Method: mkdirs()
	// Method: delete() => files and empty directories
	// Method: renameTo(File newname)
	// Method: isFile()
	// Method: isDirectory()
	// Method: list() => used in directories, return a String[]
	// Method: toPath() return a path
	public static void testFile() {
		System.out.println("Test File Class");
		
		// create root directory
		File dirRoot = new File("test_files/test_File");
		dirRoot.mkdir();
		
		// create files references inside the tmp directory
		File dirDel = new File("test_files/test_File/tmp");
		File fileDel1 = new File("test_files/test_File/tmp/delete1.txt");
		File fileDel2 = new File("test_files/test_File/tmp/delete2.txt");
		
		// delete files
		fileDel1.delete();
		fileDel2.delete();

		// you only can delete a directory if it's empty
		boolean dropDir1 = dirDel.delete();
		System.out.println("is tmp diretory deleted: " + dropDir1);

		// create a reference to a directory
		File dir1 = new File("test_files/test_File/directory1");
		File dir2 = new File(dirRoot, "subdir1/subdir2/subdir3");
		
		System.out.println("is directory1 a directory: " + dir1.isDirectory());
		
		// create a directory
		boolean newDir1 = dir1.mkdir();
		boolean newDir2 = dir2.mkdirs();
		System.out.println("is directory1 created: " + newDir1);
		System.out.println("is subdir1/subdir2/subdir3 created: " + newDir2);
		
		// create references to files 
		File f1 = new File("test_files/test_File/new1.txt");
		File f2 = new File(dirRoot, "new2.txt");
		File f2rm = new File("test_files/test_File/rename_new2.txt");
		
		// check files
		System.out.println("exist new1.txt: " + f1.exists());
		System.out.println("is new1.txt a file: " +f1.isFile());
		
		try {
			// return false if the file already exist
			boolean newf1 = f1.createNewFile();
			boolean newf2 = f2.createNewFile();
			
			System.out.println("is a new file created: " + newf1);
		}
		catch (IOException e) {
			System.out.println("file can't be created");
		}
		
		// rename a old file
		f2.renameTo(f2rm);
		
		String[] filesNames = new String[100];
		filesNames = dirRoot.list();
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
		// System.out.println("\nTest FileWriter Class");
		
		File dirRoot = new File("test_files/test_FileWriter");
		dirRoot.mkdir();
		
		File f1 = new File(dirRoot, "write1.txt");
		
		try {
			FileWriter fw1 = new FileWriter(f1);
			fw1.write("test write several lines\n--line test 1\n--line test 2\n");
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
		
		char[] input = new char[100];
		
		File f1 = new File("test_files/test_FileWriter/write1.txt");

		try {
			FileReader fr1 = new FileReader(f1);
			try {
				int size = fr1.read(input);
				System.out.println("File size: " + size);
				for (char c: input) {
					System.out.print(c);
				}
//				System.out.println();
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
	
	// Test BufferedReader Class
	// Constructor: FileReader(FileReader fr)
	// Method: readLine() return a string
	public static void testBufferedReader() {
		System.out.println("\nTest BufferedReader Class");
		
		File f1 = new File("test_files/test_FileWriter/write1.txt");

		try {
			FileReader fr1 = new FileReader(f1);
			BufferedReader br1 = new BufferedReader(fr1);
			try {
				String s;
				while ((s = br1.readLine()) != null) {
					System.out.println(s);
				}
//				System.out.println();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Test PrintWriter Class
	// Constructor: PrintWrite(File f) => FileNotFoundException
	// Method: println(String line)
	// Method: close()
	public static void testPrintWriter() {
		
		File dirRoot = new File("test_files/test_PrintWriter");
		dirRoot.mkdir();
		
		File f1 = new File(dirRoot, "print.txt");
		
		try {
			PrintWriter pw1 = new PrintWriter(f1);
			pw1.println("test print several lines");
			pw1.println("--print line 1");
			pw1.println("--print line 2");
			// it's necessary close the resource to save the text in the file
			pw1.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	// Test Console Class
	// Constructor: System.console() => it can be null if you aren't use the console to execute the program
	// Method: readLine(String rexexp, String output)
	// Method: readPassword(String rexexp, String output) return char[]
	// Method: format(String output, String[] args)
	public static void testConsole() {
		System.out.println("\nTest Console Class");
		
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
	
	// Test Path Class
	// It is a Interface and implement a Iterable<Path>
	// Constructor: Paths.get(String filepath)
	// Constructor: Paths.get(String dir, String file);
	// Constructor: Paths.get(URI u)
	// Constructor: FileSystems.getDefault().getPath(filepath)
	// Method: toFile() return a file
	
	// Method: toString()
	// Method: getFileName()
	// Method: getNameCount() => count of section of the path
	// Method: getName(int index) => name of the section asociated to the index
	// Method: getParent()
	// Method: getRoot()
	// Method: subpath(int init, int exclude_end)
	// Method: normalize()
	// -- Rule: '/./' is used to identify the actual folder
	// -- Rule: '/../' is used to identify de parent folder
	// -- Rule: '.' not has a special used
	// -- Rule: '../' at the begining of the path can't be normalized
	// Method: resolve(Path p)
	// Method: resolve(String s)
	// -- Rule: if resolve a absolute path it will override the result
	// -- Rule: if resolve a null it will produce a compiler error
	// -- Rule: if resolve a null object (string o path) it will produce a NullPointerException
	// Method: relativizing(Path p)
	// -- Rule: obtain the path to go from path1 to path2
	// -- Rule: only work when both path are relative or absolute, otherwise throw a IllegalArgumentException
	public static void testPath() {
		System.out.println("\nTest Path Class");

		// different forms of instantiated a path
		Path dir1 = Paths.get("/home/patan/workspace/ocp/test_files/test_File");
		Path path1 = Paths.get("test_files/test_File/new1.txt");
		Path path2 = Paths.get("test_files", "test_File", "new1.txt");
		Path path3 = Paths.get(URI.create("file:///localhost/ocp/test_files/test_File/new1.txt"));
		Path path4 = FileSystems.getDefault().getPath("test_files/test_File/new1.txt");
		
		// path to file
		File file1 = path1.toFile();
		Path path5 = file1.toPath();
		
		// information about a path
		System.out.println("path file: " + path1.toString());
		System.out.println("-- file name: " + path1.getFileName());
		System.out.println("-- name count: " + path1.getNameCount());
		System.out.println("-- name pos 0: " + path1.getName(0));
		System.out.println("-- name pos 1: " + path1.getName(1));
		System.out.println("-- parent: " + path1.getParent());
		System.out.println("-- root: " + path1.getRoot());
		System.out.println("-- subpath 0 - 2: " + path1.subpath(0, 2));
		
		System.out.println("path directory: " + dir1.toString());
		System.out.println("-- file name: " + dir1.getFileName());
		System.out.println("-- parent: " + dir1.getParent());
		System.out.println("-- root: " + dir1.getRoot());
		System.out.println("-- subpath 0 - 2: " + dir1.subpath(0, 2));
		
		// path as a Iterable
		System.out.print("-- ");
		for (Path subpath: dir1) {
			System.out.print(subpath + " => ");
		}
		System.out.println();
		
		// normalizing path
		System.out.println("normalizing: '/a/./b/./c': " + Paths.get("/a/./b/./c").normalize());
		System.out.println("normalizing: './classpath': " + Paths.get("./classpath").normalize());
		System.out.println("normalizing: '.classpath': " + Paths.get(".classpath").normalize());
		System.out.println("normalizing: '/a/b/c/..': " + Paths.get("/a/b/c/..").normalize());
		System.out.println("normalizing: '../a/b/c/': " + Paths.get("../a/b/c").normalize());
		
		Path absolute1 = Paths.get("/home/patan");
		Path absolute2 = Paths.get("/home/patan/workspace/ocp");
		Path relative1 = Paths.get("subdir1/subdir2");
		Path relative2 = Paths.get("subdir1/subdir3/subdir4");
		Path relative3 = Paths.get("subdir6/subdir7");
		Path file = Paths.get("test_Path.txt");
		Path pathNull = null;

		// resolving path
		System.out.println("resolving 'absolute/relative': " + absolute1.resolve(relative1));
		System.out.println("resolving 'absolute/file': " + absolute1.resolve(file));
		System.out.println("resolving 'relative/file': " + relative1.resolve(file));
		System.out.println("resolving 'relative/absolute': " + relative1.resolve(absolute1));
		System.out.println("resolving 'file/absolute': " + file.resolve(absolute1));
		System.out.println("resolving 'file/relative': " + file.resolve(relative1));
		System.out.println("resolving 'absolute/string': " + absolute1.resolve("subdirstr1/subsirstr2"));
		//System.out.println("resolving 'absolute/null': " + absolute1.resolve(null)); // compiler error
		//System.out.println("resolving 'absolute/string(null)': " + absolute1.resolve((String) null)); // NullPointerException
		//System.out.println("resolving 'absolute/path(null)': " + absolute1.resolve(pathNull)); // NullPointerException
		
		// relativizing path
		System.out.println("relativizing '" + absolute1 + "' - '" + absolute2 + "': cd " + absolute1.relativize(absolute2));
		System.out.println("relativizing '" + absolute2 + "' - '" + absolute1 + "': cd " + absolute2.relativize(absolute1));
		// System.out.println("relativizing '" + absolute1 + "' - '" + relative1 + "': cd " + absolute1.relativize(relative1)); // IllegalArgumentException
		System.out.println("relativizing '" + relative1 + "' - '" + relative2 + "': cd " + relative1.relativize(relative2));
		System.out.println("relativizing '" + relative1 + "' - '" + relative3 + "': cd " + relative1.relativize(relative3));
		// System.out.println("relativizing '" + relative1 + "' - 'subdirstr1/subdirstr2': cd " + relative1.relativize("subdirstr1/subdirstr2")); // compiler error
		
	}

	// Test Files Class
	// Method: Files.exists(Path p)
	// Method: Files.notExists(Path p)
	// Method: Files.createFile(Path p) => FileAlreadyExistsException or IOException
	// Method: Files.createDirectory(Path p) => FileAlreadyExistsException or IOException
	// Method: Files.createDirectories(Path p) => IOException
	// Method: Files.copy(Path source, Path target) => FileAlreadyExistsException
	// Method: Files.copy(Path source, Path target, StandardCopyOption.REPLACE_EXISTING)
	// Method: Files.move(Path source, Path target) => FileAlreadyExistsException or NoSuchFileException
	// Method: Files.delete(Path p);
	// Method: Files.deleteIfExists(Path p);
	public static void testFiles() {
		System.out.println("\nTest Files Class");
		
		File dirRoot = new File("test_files/test_Files");
		dirRoot.mkdir();
		
		Path path1 = Paths.get("test_files/test_Files", "new1.txt");
		Path path2 = Paths.get("test_files/test_Files", "copy_new1.txt");
		Path path3 = Paths.get("test_files/test_Files", "copyexist_new1.txt");
		Path path4 = Paths.get("test_files/test_Files", "move_new1.txt");
		Path pathDel1 = Paths.get("test_files/test_Files", "delete1.txt");
		Path dir1 = Paths.get("test_files/test_Files", "directory1");
		Path dirs1 = Paths.get("test_files/test_Files", "subdir1/subdir2/subdir3");
		
		try {
			
			System.out.println("exist new1.txt: " + Files.exists(path1));
			if (!Files.exists(path1)) {
				// Only work if the file doesn't exist
				Files.createFile(path1);
			}
			
			System.out.println("exist directory1: " + Files.exists(dir1));
			if (!Files.exists(dir1)) {
				// Only work if the directory doesn't exist
				Files.createDirectory(dir1);
			}
			
			Files.createDirectories(dirs1);
			
			Files.deleteIfExists(path2);
			Files.deleteIfExists(path3);
			Files.deleteIfExists(path4);
			
			System.out.println("NOT exist copy_new1: " + Files.notExists(path2));
			
			Files.copy(path1, path2);
			Files.copy(path1, path3, StandardCopyOption.REPLACE_EXISTING);
			
			Files.move(path1, path4);

			Files.copy(path4, pathDel1);
			Files.delete(pathDel1);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
