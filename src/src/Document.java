package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Scanner;

public class Document implements Iterable<String> {

	// Declare variables
	private Node head;
	private int totalLineNum;

	// Constructor
	public Document() {
		head = new Node();
		totalLineNum = 0;
	}

	/**
	 * addLine takes user input and adds new lines based on the input. Uses the
	 * scanner in to retrieve the user input.
	 * 
	 * @param in
	 *            Scanner that retrieves user input.
	 */
	public void addLine(Scanner in) {
		// Declare variables.
		int sent = 1;
		Node cur = null;
		int afterLineNum = 0;

		// Prompt user for input.
		System.out.print("type line? (y/n): ");
		String res = in.nextLine();

		// Switch based on if the user wants to input a line or not.
		switch (res) {
		case "y":
			// Prompt the user for the line they wish to input after.
			System.out.print("insert after line: ");
			try {
				afterLineNum = Integer.parseInt(in.nextLine());
			} catch (Exception e) {
				System.out.printf("Please enter a number!\n\n");
				break;
			}
			if (afterLineNum <= 0 || afterLineNum > totalLineNum) {
				// If the line to insert after is not 0 then retrieve the node
				// at
				// that position.
				if (afterLineNum != 0) {
					cur = getNode(afterLineNum);
					if (cur == null)
						break;
					System.out.printf("inserting after:\n%s\n", cur.line.getData());

				}

				// While the user still wants to enter lines to the document
				// keep
				// looping over the prompts.
				while (sent == 1) {
					// Prompt user if they wish to enter a new line.
					System.out.print("type line? (y/n): ");
					res = in.nextLine();

					// Switch based on if the user wants to input a line or not.
					switch (res) {
					case "y":
						// If the line to insert after is 0 then we are adding
						// to
						// the head of the list.
						if (afterLineNum == 0) {
							System.out.printf("%d:", totalLineNum + 1);
							totalLineNum++;
							insertTail(in.nextLine());

						}
						// Otherwise we are adding after a node.
						else {
							System.out.printf("%d:", afterLineNum + 1);
							afterLineNum++;
							totalLineNum++;
							if (cur != null) {
								insertAfter(cur.line.getData(), in.nextLine(), head);
								cur = cur.next;
							}
						}
						break;
					case "n":
						sent = 0;
						break;
					}
				}
			} else {
				System.out.println(
						"Line Number can't be negative or greater than the total number of lines in the document.");
			}
			break;
		case "n":
			sent = 0;
			break;
		default:
			System.out.println("please enter a valid option.");
			break;
		}

	}

	/**
	 * removeLine removes a specified line number from the document.
	 * 
	 * @param num
	 *            String index of line to remove
	 */
	public void removeLine(int num) {
		// If the document isn't empty then try and remove the line.
		if (!isEmpty(head)) {
			// If the num is within bounds of the document then remove the line.
			if (!(num > totalLineNum) && num < 0) {
				removeNode(getNode(num).line.getData(), head);
				totalLineNum--;
				// Otherwise the line is not in the document
			} else {
				System.out.printf("%d is not a line in the document\n", num);
			}
		}
	}

	/**
	 * removeRange deletes a range of lines starting from start and ending with
	 * end. Start must be less than or equal to end. Start must be less than or
	 * equal to the total number of lines in the document.
	 * 
	 * @param start
	 *            Index of the starting line of the deletion.
	 * @param end
	 *            Index of the ending line of the deletion.
	 */
	public void removeRange(int start, int end) {
		// Validate that the start and end of the range is within bounds.
		if (start >= end) {
			System.out.println("To must be a less than or equal to From.\n");
		} else if (start >= totalLineNum) {
			System.out.printf("Line number: %d does not exist\n", start);
			// If it is then remove the range
		} else {
			// If the document isn't empty start removing
			if (!isEmpty(head)) {
				// Traverse the range removing each line.
				if (start == 0)
					start++;
				if (end > totalLineNum)
					end = totalLineNum;
				while ((start - 1) != end) {
					removeLine(end--);
				}
			}
		}
	}

	/**
	 * writeToFile iterates over each line in the document and outputs it to the
	 * passed filename.
	 * 
	 * @param filename
	 *            Name of the file to write the document to. File is saved to
	 *            the same directory as this class.
	 * 
	 * @return Boolean flag for success or fail.
	 */
	public boolean writeToFile(String filename) {
		try {
			// Create a new Buffered Writer
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./" + filename), "utf-8"));
			// Get a pointer to the first line in the document
			Node currentLine = head.next;
			// Iterate through the document writing each line to the file.
			while (currentLine != null) {
				writer.write(currentLine.line.getData() + "\n");
				currentLine = currentLine.next;
			}
			// Close the Buffered Writer
			writer.close();
			return true;

			// If an exception occurs then notify the user.
		} catch (Exception e) {
			System.out.println(filename + " does not exist");
			return false;
		}

	}

	/**
	 * loadFile adds each line in the passed file as a new Line at the end of
	 * the document.
	 * 
	 * @param filename
	 *            Name of the file to write the document to. File must be in the
	 *            the same directory as this class.
	 * @return
	 */
	public boolean loadFile(String filename) {
		try {
			// Open the file
			File inFile = new File("./" + filename);

			// Create a new scanner for the inFile
			Scanner in = new Scanner(inFile);

			// If the document is not empty then empty it
			if (totalLineNum != 0) {
				head.next = null;
				totalLineNum = 0;
			}

			// Iterate over the file and insert each new line to the document
			while (in.hasNextLine()) {
				totalLineNum++;
				insertTail(in.nextLine());
			}

			// Close the scanner.
			in.close();
			return true;

			// If an error occurs notify that user.
		} catch (Exception e) {
			System.out.println(filename + " does not exist");
			return false;
		}
	}

	/**
	 * showLine displays a specified line within the document.
	 * 
	 * @param lineNum
	 *            Index of the line to start displaying.
	 */
	public void showLine(int lineNum) {
		// If the lineNum doesn't exist in the document then notify the user.
		if (lineNum > totalLineNum || lineNum < 0) {
			System.out.printf("Line %d does not exist! Total Lines: %d\n\n", lineNum, totalLineNum);
		} else {
			// Iterate through the document and print each line along with its
			// line number
			if (lineNum == 0)
				lineNum++;
			Node current = head.next;
			for (int i = 1; i < lineNum; i++) {
				current = current.next;
			}
			System.out.printf("%d: %s\n", lineNum, current.line.getData());
		}
	}

	/**
	 * showRange displays a specified range of lines within the document.
	 * 
	 * @param start
	 *            Index of the line to start displaying.
	 * @param end
	 *            Index of the line to stop displaying.
	 */
	public void showRange(int start, int end) {
		// Validate that start is not less than 0
		if (!(start < 0)) {
			// If start is greater than end then notify the user and end
			if (start > end)
				System.out.println("To must be a larger number than From.\n");
			// If it isn't then proceed to show range
			else {
				// If end is greater than the totalLineNum then set end to
				// totalLineNum, to print to the end
				if (end > totalLineNum)
					end = totalLineNum;
				if (start == 0)
					start++;

				// Traverse the document printing the range
				Node current = getNode(start);

				if (current != null) {
					for (int i = start; i <= end; i++) {
						System.out.printf("%d: %s\n", i, current.line.getData());
						current = current.next;
					}
				}
			}
		} else {
			System.out.printf("To must be equal to or greater than 0");
		}

	}

	/**
	 * copyRange copies the specified range of lines into a document buffer
	 * 
	 * @param start
	 *            Index of the first line to copy
	 * @param end
	 *            Index of the last line to copy
	 */
	public void copyRange(int start, int end, Document buffer) {
		// If start is 0 or more then proceed
		if (start >= 0) {
			// If start is less than or equal to end then proceed
			if (start <= end) {
				// If end is greater than the totalLineNum then set end to
				// totalLineNum to copy to the end
				if (end > totalLineNum)
					end = totalLineNum;
				// Traverse the document inserting each into the Document
				// passed.
				Node current = getNode(start);
				while (current != null && start <= end) {
					buffer.insertTail(current.line.getData());
					current = current.next;
					end--;
				}
			}
			// Otherwise notify the user that the start can be less than the end
			else {
				System.out.printf("From must be less than or equal to To");
			}
		}
		// Otherwise notify the user that the start must be greater than or
		// equal to 0
		else {
			System.out.printf("From must be greater than or equal to 0");
		}
	}

	/**
	 * pasteLines pastes the current document buffer after the specified line.
	 * 
	 * @param start
	 *            Index of the line to paste the document buffer after.
	 */
	public void pasteLines(int start, Document buffer) {
		// If start is greater than totalLineNum then set start to the end to
		// paste the lines at the end of the document
		if (start > totalLineNum)
			start = totalLineNum;
		// If start is greater than or equal to 0 then start pasting lines
		if (start >= 0) {
			// Get the node that corresponds to the line at start
			Node current = getNode(start);

			// Get an iterator for the buffer
			Iterator<String> iter = buffer.iterator();

			// If the Node is in the document then start pasting lines
			if (current != null) {
				// Iterate over the buffer and add each line in the buffer to
				// the current document
				while (iter.hasNext() && current != null) {
					Node toAdd = new Node(iter.next());

					toAdd.prev = current;
					toAdd.next = current.next;
					current.next = toAdd;
					if (current.next != null)
						current.next.prev = toAdd;

					current = current.next;
					totalLineNum++;
				}
			}
		}
		// Otherwise notify that the user that the start must be greater than or
		// equal to 0
		else {
			System.out.printf("From must be greater than or equal 0");
		}
	}

	/**
	 * editLine starts the edit line menu for the specified line.
	 * 
	 * @param in
	 *            Scanner that retrieves user input.
	 * @param lineNumber
	 *            Index of the line to edit
	 */
	public void editLine(Scanner in, int lineNumber) {
		// If the line number is less than 0 then notify the user
		if (lineNumber < 0) {
			System.out.printf("From must be greater than or equal to 0");
		}
		// Otherwise proceed to edit the line.
		else {
			// If the lineNumber is greater than the totalLineNum then set
			// lineNumber to the totalLineNum to edit the last line
			if (lineNumber > totalLineNum)
				lineNumber = totalLineNum;
			// Get the line at lineNumber
			Node current = getNode(lineNumber);
			// Start editing the line
			current.line.lineMenu(in);
		}
	}

	/**
	 * Displays the entire document to the console.
	 */
	public void printDoc() {
		// Get the current node
		Node current = head.next;
		int count = 1;

		// Add a space between user input and document
		System.out.println();

		// Traverse the list printing the doc
		while (current != null) {
			System.out.printf("%d:%s\n", count, current.line.getData());
			current = current.next;
			count++;
		}

		// Add a space between the document and the menu
		System.out.println();

	}

	/**
	 * toString prints out the string representation of the document.
	 */
	@Override
	public String toString() {
		// Get the current node
		Node current = head.next;
		String toReturn = "";
		int count = 1;

		// Traverse the list printing out the document
		while (current != null) {
			toReturn += (count + ":" + current.line.getData() + "\n");
			current = current.next;
			count++;
		}

		// Return the String of the document
		return toReturn;
	}

	/**
	 * Iterator returns an Document iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		// Return a document iterator
		return new docIterator();
	}

	/**
	 * insertHead adds a new node to the end of the list. This represents adding
	 * a new line to the document.
	 * 
	 * @param data
	 *            The line that is to be added to the document.
	 */
	private void insertTail(String data) {
		// Create a node with a line storing data
		Node toAdd = new Node(data);
		// If the document is empty then insert at the head
		if (isEmpty(head)) {
			head.next = toAdd;
			toAdd.prev = head;
		}
		// Otherwise insert the node at the end of the document
		else {
			Node current = head.next;
			while (current.next != null)
				current = current.next;
			current.next = toAdd;
			toAdd.prev = current;
		}
	}

	/**
	 * insertHead adds a new node to after the specified target if it is in the
	 * list. If the target value is not in the list then return false else add
	 * the data value to the list and return true. This represents adding a new
	 * line to the document.
	 * 
	 * @param target
	 *            The line to add the new line after.
	 * 
	 * @param data
	 *            The line that is to be added to the document.
	 * @param curHead
	 *            Head node of the document to insert into.
	 * @return Boolean flag for success or failure.
	 */
	private boolean insertAfter(String target, String data, Node curHead) {
		// If the document is empty then can't insert after anything
		if (isEmpty(curHead))
			return false;

		// Get the target node
		Node current = findNode(target, curHead);

		// If it doesn't exist then insert failed
		if (current == null)
			return false;

		// Add the node after the specified node
		Node toAdd = new Node(data);
		toAdd.prev = current;
		toAdd.next = current.next;
		current.next = toAdd;
		return true;

	}

	/**
	 * Removes the target value from the list. This represents removing a line
	 * from the document.
	 * 
	 * @param target
	 *            The line to remove from the document.
	 * @param curHead
	 *            Head node of the document to remove from.
	 * @return Boolean flag for success or failure.
	 */
	private boolean removeNode(String target, Node curHead) {
		// Find the target node
		Node current = findNode(target, curHead);

		// If it doesn't exist then fail to remove
		if (current == null)
			return false;

		// Remove the node
		Node tmp = current.prev;
		if (current.next != null)
			current.next.prev = tmp;
		tmp.next = current.next;
		return true;

	}

	/**
	 * isEmpty returns true if the document has no lines in it or false if it
	 * does.
	 * 
	 * @param curHead
	 *            Head node of the document to check if empty.
	 * @return Boolean flag for empty doc. If true the document is empty.
	 */
	private boolean isEmpty(Node curHead) {
		return curHead.next == null;
	}

	/**
	 * Retrieves the node at the position specified by nodeNum.
	 * 
	 * @param nodeNum
	 *            Index of the node to return.
	 * @param curHead
	 *            Head node of the document to retrieve the node from.
	 * @return
	 */
	private Node getNode(int nodeNum) {
		// If the document is empty then the node isn't in the list
		if (isEmpty(head))
			return null;
		// If the nodeNum is greater than totalLineNum then the node isn't in
		// the list
		if (nodeNum > totalLineNum)
			return null;

		// Traverse the list and return the node
		Node cur = head.next;
		for (int i = 1; i < nodeNum; i++) {
			cur = cur.next;
		}
		return cur;
	}

	/**
	 * Retrieves the first node with the string specified by target.
	 * 
	 * @param target
	 *            The string value to find.
	 * @param curHead
	 *            Head node of the document to find the node in.
	 * @return
	 */
	private Node findNode(String target, Node curHead) {
		// If the list is empty then the node isn't there.
		if (isEmpty(curHead))
			return null;

		// Get the current node after head
		Node current = curHead.next;

		// Traverse the list and return the node if its in the list
		while (current.next != null && !current.line.getData().equals(target))
			current = current.next;
		if (current.line.getData().equals(target))
			return current;
		else
			return null;
	}

	// Node class to store the line data
	private class Node {
		Node next, prev;
		Line line;

		public Node() {
			next = null;
			prev = null;
		}

		public Node(String data) {
			next = null;
			prev = null;
			line = new Line(data);
		}
	}

	// Document iterator class to allow for iteration of the document
	private class docIterator implements Iterator<String> {

		Node current;

		public docIterator() {
			current = head.next;
		}

		public boolean hasNext() {
			return current != null;
		}

		public String next() {
			String tmp = current.line.getData();
			current = current.next;
			return tmp;
		}
	}
}
