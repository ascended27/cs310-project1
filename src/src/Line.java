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
		int to, from;
		int sent = 1;
		showLine();
		System.out.println();
		while (sent == 1) {
			printLineMenu();
			String res = in.nextLine();
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
		tmp = new StringBuilder();
		buffer = new StringBuffer();

		if (!(from > data.length())) {

			if (to > data.length())
				to = data.length();

			buffer.append(data.substring(from, to + 1));
			showCopied(from, to);
			System.out.println();
		} else {
			System.out.println("From must be equal to or less than the length of the line!");
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
		tmp = new StringBuilder();
		buffer = new StringBuffer();
		//TODO: Check for from or to < 0
		if (!(from > data.length())) {
			if (to > data.length())
				to = data.length();
			tmp.append(data);
			buffer.append(tmp.substring(from, to + 1));
			tmp.delete(from, to);
			data = tmp.toString();
			System.out.println();
		} else {
			System.out.println("From must be equal to or less than the length of the line!");
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
		tmp = new StringBuilder();
		if (from > data.length())
			from = data.length();
		tmp.append(data);
		tmp.insert(from, buffer.toString());
		data = tmp.toString();
		System.out.println();
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
		tmp = new StringBuilder();
		if (from > data.length())
			from = data.length();
		tmp.append(data);
		tmp.insert(from, text);
		data = tmp.toString();
		System.out.println();
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
		String input;
		tmp = new StringBuilder();
		if (!(from > data.length())) {
			if (to > data.length())
				to = data.length();
			showCopied(from, to);
			int tmpSent = 1;
			while (tmpSent == 1) {
				System.out.print("y/n: ");
				input = in.nextLine();
				switch (input) {
				case "y":
					tmp.append(data);
					tmp.delete(from, to);
					data = tmp.toString();
					tmpSent = -1;
					break;
				case "n":
					tmpSent = -1;
					break;
				default:
					System.out.println("Please enter a valid option");
				}
			}

		} else {
			System.out.println("From must be equal to or less than the length of the line!");
		}
		System.out.println();
	}

	/**
	 * showCopied prints out the line and what indices have been copied into
	 * the StringBuffer.
	 * 
	 * @param from
	 *            Index of the beginning of the substring to be copied.
	 * @param to
	 *            Index of the end of the substring to be copied.
	 */
	private void showCopied(int from, int to) {
		System.out.println("Copied");
		showLine();
		for (int i = 0; i <= to; i++) {
			if (i >= from)
				System.out.print("^");
			else
				System.out.print(" ");
		}
		System.out.println();
	}

	/**
	 * showLine prints the current line with scale.
	 */
	private void showLine() {
		for (int i = 0; i < data.length(); i++) {
			if (i % 5 == 0)
				System.out.print(i);
			else
				System.out.print(" ");
		}

		System.out.println();

		for (int i = 0; i < data.length(); i++) {
			if (i % 10 == 0)
				System.out.print("|");
			else if (i % 5 == 0)
				System.out.print("+");
			else
				System.out.print("-");
		}
		System.out.printf("\n%s\n", data);
	}

	/**
	 * printLineMenu prints the line menu.
	 */
	private void printLineMenu() {
		System.out.printf(
				"Show line:  s\n" + "Copy to string buffer:  c\n" + "Cut:  t\n" + "Paste from string buffer:  p\n"
						+ "Enter new substring:  e\n" + "Delete substring:  d\n" + "Quit line:  q\n->");
	}

}
