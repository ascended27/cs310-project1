package src;

import java.util.Scanner;

public class Line {

	private String data;
	private StringBuffer buffer = new StringBuffer();
	private StringBuilder tmp = new StringBuilder();

	/**
	 * Line is a constructor that creates a new line with a specified data.
	 * 
	 * @param data
	 *            String to set data String in this line.
	 */
	public Line(String data) {
		this.data = data;
	}

	/**
	 * getData returns the data currently stored in this line.
	 * 
	 * @return returns the data String.
	 */
	public String getData() {
		return data;
	}

	/**
	 * setData sets the data currently stored in this line to the specified
	 * data.
	 * 
	 * @param data
	 *            String to set data String in this line.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * lineMenu handles the edit line menu.
	 * 
	 * @param in
	 *            Scanner to receive user input while in the menu.
	 */
	public void lineMenu(Scanner in) {
		// Declare variables
		int to, from;
		int sent = 1;

		// Display the line
		showLine();

		// Add a space between the line and the menu
		System.out.println();

		// While sent is 1 loop the menu
		while (sent == 1) {
			// Print the line menu
			printLineMenu();

			// Get the user input
			String res = in.nextLine();

			// Switch to handle line menu
			switch (res) {
			case "s":
				showLine();
				System.out.println();
				break;
			case "c":
				System.out.print("From: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("To: ");
				to = Integer.parseInt(in.nextLine());
				copy(from, to);
				break;
			case "t":
				System.out.print("From: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("To: ");
				to = Integer.parseInt(in.nextLine());
				cut(from, to);
				break;
			case "p":
				System.out.print("Insert at position: ");
				from = Integer.parseInt(in.nextLine());
				paste(from);
				break;
			case "e":
				System.out.print("Insert at position: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("Text: ");
				String text = in.nextLine();
				enter(from, text);
				break;
			case "d":
				System.out.print("From: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("To: ");
				to = Integer.parseInt(in.nextLine());
				delete(from, to, in);
				break;
			case "q":
				sent = -1;
				System.out.println();
				break;
			default:
				System.out.println("Please enter a valid option.\n");
				break;
			}
		}

		System.out.println();
	}

	/**
	 * copy copies the substring starting at index from and ending at index to
	 * to the StringBuffer.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param to
	 *            Index of the end of the substring to be copied.
	 */
	private void copy(int from, int to) {
		// Create a new String Buffer to hold the copied portion of the string
		buffer = new StringBuffer();

		// Validate the to and from values, if they are not valid then notify
		// the user
		if (from < 0 || to < 0 || from > to) {
			System.out.println("Please enter valid values for From and To\n");
		}
		// Otherwise copy the range
		else {
			// If from is within the length of the string then proceed
			if (!(from > data.length())) {
				// If to is greater than the string's length then set to the the
				// length of the string
				if (to > data.length())
					to = data.length();
				// Copy the substring to the buffer.
				buffer.append(data.substring(from, to + 1));

				// Display the copied string
				showCopied(from, to);

				// Add a space between the copied string and the menu
				System.out.println();
			}
			// Notify the user that there is invalid input
			else {
				System.out.println("From must be equal to or less than the length of the line\n");
			}
		}
	}

	/**
	 * cut copies the substring starting at index from and ending at index to to
	 * the StringBuffer and removes it from the String stored in data.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param to
	 *            Index of the end of the substring to be copied.
	 */
	private void cut(int from, int to) {
		// Initialize variables
		tmp = new StringBuilder();
		buffer = new StringBuffer();

		// Validate user input
		if (!(from > data.length()) && (from > -1 && to > -1)) {
			// If to is greater than the length of the current line, then the
			// user wants to copy to the end. Set to to the length of the string
			if (to > data.length())
				to = data.length();

			// Append the current data string to the StringBuilder
			tmp.append(data);
			// Append the substring that was cut to the StringBuffer
			buffer.append(tmp.substring(from, to + 1));
			// Delete the substring from the StringBuilder
			tmp.delete(from, to + 1);
			// Set data to the StringBuilder
			data = tmp.toString();
			System.out.println();
		}
		// User input is invalid so notify the user
		else {
			System.out.println("From must be equal to or less than the length of the line\n");
		}
	}

	/**
	 * paste pastes the the current String stored in the string buffer into the
	 * String stored in data, starting at the index from.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 */
	private void paste(int from) {
		// If from is greater than or equal to 0 then begin paste
		if (from >= 0) {
			// Create a new StringBuilder
			tmp = new StringBuilder();

			// If from is greater than the length of the string then set it to
			// the length of the string to paste the buffer to the end of the
			// line
			if (from > data.length())
				from = data.length();

			// Append the current data string to the StringBuilder
			tmp.append(data);

			// Insert the buffer to the StringBuilder at the specified index
			tmp.insert(from, buffer.toString());

			// Set the data string to the value of the StringBuilder
			data = tmp.toString();
			System.out.println();
		}
		// Otherwise the input isn't valid, notify the user
		else {
			System.out.println("From must be greater than or equal to than 0\n");
		}
	}

	/**
	 * enter inserts the specified text into the String data starting at the
	 * index from in the String data.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param text
	 *            String to be inserted into the String data.
	 */
	private void enter(int from, String text) {
		// If from is greater than or equal to 0 then begin paste
		if (from >= 0) {
			// Create a new StringBuilder
			tmp = new StringBuilder();

			// If from is greater than the length of the string then set it to
			// the length of the string to paste the buffer to the end of the
			// line
			if (from > data.length())
				from = data.length();

			// Append the current data string to the StringBuilder
			tmp.append(data);

			// Insert the new text to the StringBuilder at the specified index
			tmp.insert(from, text);

			// Set the data string to the value of the StringBuilder
			data = tmp.toString();
			System.out.println();
		}
		// Otherwise the input isn't valid, notify the user
		else {
			System.out.println("From must be greater than or equal to than 0\n");
		}
	}

	/**
	 * delete deletes the substring starting at index from and ending at index
	 * to to the StringBuffer.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param to
	 *            Index of the end of the substring to be copied.
	 * @param in
	 *            Scanner to get user confirmation of deletion.
	 */
	private void delete(int from, int to, Scanner in) {
		// Declare Variables
		String input;

		// Initialize Variables
		tmp = new StringBuilder();

		// Validate user input
		if (from <= to) {
			// If from is less than the length of the String and is greater than
			// or equal to 0 continue to delete
			if (!(from > data.length()) && from >= 0) {
				// If to is greater than the length of the string then set to to
				// the length of the string
				if (to > data.length())
					to = data.length();
				// Display the substring to be deleted
				showCopied(from, to);

				// Initialize sentinel value
				int tmpSent = 1;

				// Verify that the user wants to delete the line
				while (tmpSent == 1) {
					// Prompt the user for input
					System.out.print("y/n: ");

					// Get the user input
					input = in.nextLine();

					// Switch to handle user input
					switch (input) {
					case "y":
						// Add the current string to the StringBuilder
						tmp.append(data);

						// Delete the substring from the StringBuilder
						tmp.delete(from, to);

						// Set data equal to the value of the StringBuilder
						data = tmp.toString();

						// Set the sentinel to something other than 1 to break
						// out of the loop
						tmpSent = -1;
						break;
					case "n":
						// Set the sentinel to something other than 1 to break
						// out of the loop
						tmpSent = -1;
						break;
					default:
						// Notify the user they didn't enter a valid option
						System.out.println("Please enter a valid option\n");
					}
				}

			}
			// Notify the user that is incorrect
			else {
				System.out.println("From must be equal to or less than the length of the line!\n");
			}
			System.out.println();
		}
		// Notify the user that is incorrect
		else {
			System.out.printf("From must be less than or equal to To\n");
		}
	}

	/**
	 * showCopied prints out the line and what indices have been copied into the
	 * StringBuffer.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param to
	 *            Index of the end of the substring to be copied.
	 */
	private void showCopied(int from, int to) {
		// Validate user input
		if (from <= to) {
			// Validate user input
			if (from >= 0) {
				// If to is greater than the length of the data string then set
				// it to the length of the data string
				if (to > data.length())
					to = data.length();
				// Notify that the substring displayed is copied
				System.out.println("Copied");
				// Show the line
				showLine();
				// Show the portion copied
				for (int i = 0; i <= to; i++) {
					if (i >= from)
						System.out.print("^");
					else
						System.out.print(" ");
				}
				System.out.println();
			}
			// Invalid input, notify the user
			else {
				System.out.printf("From must be greater than or equal to 0\n");
			}
		}
		// Invalid input, notify the user
		else {
			System.out.printf("From must be less than or equal to To\n");
		}
	}

	/**
	 * showLine prints the current line with scale.
	 */
	private void showLine() {
		// Print the scale indices
		for (int i = 0; i < data.length(); i++) {
			if (i % 5 == 0)
				System.out.print(i);
			else
				System.out.print(" ");
		}

		System.out.println();

		// Print out the scale
		for (int i = 0; i < data.length(); i++) {
			if (i % 10 == 0)
				System.out.print("|");
			else if (i % 5 == 0)
				System.out.print("+");
			else
				System.out.print("-");
		}

		// Print out the line
		System.out.printf("\n%s\n", data);
	}

	/**
	 * printLineMenu prints the line menu.
	 */
	private void printLineMenu() {
		// Print out the menu
		System.out.printf(
				"Show line:  s\n" + "Copy to string buffer:  c\n" + "Cut:  t\n" + "Paste from string buffer:  p\n"
						+ "Enter new substring:  e\n" + "Delete substring:  d\n" + "Quit line:  q\n->");
	}

}
