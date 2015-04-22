import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This Driver class merely contains the entry point for the JVM and drives the
 * program.
 * 
 * @author Jonathan A. Henly
 * @version 1.00 2015-03-24
 **/
public class Driver {

	public static void main(String[] args) {
		// declare local constants
		final int MAX_ARGUMENTS = 1;

		// declare local variables
		Scanner inFile = null;
		Scanner input;
		String fileName = "";
		boolean ready = false;
		DiGraph topSort = null;

		/**********************************************************************
		 * GET INPUT FILE FROM PROGRAM ARGUMENT PHASE *
		 **********************************************************************/
		/*
		 * If a filename was passed to the program as an argument via
		 * "java 'ProgramName' 'filename'" then attempt to open the file for
		 * reading. If that fails proceed to the fail safe approach below.
		 * 
		 * If too many arguments were supplied to the program then inform the
		 * user and proceed to the fail safe approach below.
		 */
		if (args.length == MAX_ARGUMENTS) {
			try {
				fileName = args[0];
				inFile = new Scanner(new File(fileName));
				ready = true;
			} catch (FileNotFoundException e) {
				System.out.println("Error: The file " + fileName
						+ " was not found.");
				ready = false;
			}
		} else if (args.length > MAX_ARGUMENTS) {
			System.out
					.println("Error: To many arguments passed to this program. Maximum number of arguments is "
							+ MAX_ARGUMENTS + ".");
			ready = false;
		}

		/**********************************************************************
		 * GET INPUT FILE FROM FAIL SAFE APPROACH PHASE *
		 **********************************************************************/
		/*
		 * Fail safe approach for ensuring the correct file will be opened for
		 * reading by the program.
		 */
		while (!ready) {
			input = new Scanner(System.in);
			System.out
					.println("Please enter the name of the input file or enter \"quit\" to exit the program.");
			System.out
					.print("Example: txt/file.txt or file.txt or ../file.txt\nFilename: ");

			fileName = input.next();

			if ("quit".equals(fileName.toLowerCase())) {
				System.out
						.println("Quit was entered, exiting the program. Have a good day!");
				System.exit(0);
			}

			try {
				inFile = new Scanner(new File(fileName));
				ready = true;
			} catch (FileNotFoundException e) {
				System.out.println("Error: The file " + fileName
						+ " was not found.");
				ready = false;
			}
		}

		/**********************************************************************
		 * PROGRAM PHASE *
		 **********************************************************************/
		// instantiate a new Graph instance
		topSort = new DiGraph();

		// parse the input file into a graph
		topSort.parseData(inFile);

		// topologically sort the graph
		topSort.topSort();

		// output the topologically sorted graph
		System.out.println(topSort.topSortToString());

		/**********************************************************************
		 * END PROGRAM PHASE *
		 **********************************************************************/
	}

}
