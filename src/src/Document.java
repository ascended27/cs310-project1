package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Document {

	private Node head;
	private Node bufferHead;
	private int totalLineNum;

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
		// TODO: TEST IT.
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
			afterLineNum = Integer.parseInt(in.nextLine());

			// If the line to insert after is not 0 then retrieve the node at
			// that position.
			if (afterLineNum != 0) {
				cur = getNode(afterLineNum, head);
				if (cur == null)
					break;
				System.out.printf("inserting after:\n%s\n", cur.line.getData());

			}

			// While the user still wants to enter lines to the document keep
			// looping over the prompts.
			while (sent == 1) {
				// Prompt user if they wish to enter a new line.
				System.out.print("type line? (y/n): ");
				res = in.nextLine();

				// Switch based on if the user wants to input a line or not.
				switch (res) {
				case "y":
					// If the line to insert after is 0 then we are adding to
					// the head of the list.
					if (afterLineNum == 0) {
						System.out.printf("%d:", totalLineNum + 1);
						totalLineNum++;
						insertTail(in.nextLine(), head);

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
		if (!isEmpty(head)) {
			if (!(num > totalLineNum)) {
				removeNode(getNode(num, head).line.getData(), head);
				totalLineNum--;
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
		if (start >= end) {
			System.out.println("To must be a less than or equal to From.\n");
		} else if (start >= totalLineNum) {
			System.out.printf("Line number: %d does not exist\n", start);
		} else {
			if (!isEmpty(head)) {
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
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./" + filename), "utf-8"));
			Node currentLine = head.next;
			while (currentLine != null) {
				writer.write(currentLine.line.getData() + "\n");
				currentLine = currentLine.next;
			}
			writer.close();
			return true;
		} catch (Exception e) {
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
			File inFile = new File("./" + filename);
			Scanner in = new Scanner(inFile);
			if (totalLineNum != 0) {
				head.next = null;
				totalLineNum = 0;
			}
			while (in.hasNextLine()) {
				totalLineNum++;
				insertTail(in.nextLine(), head);
			}
			in.close();
			return true;
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
		if (lineNum > totalLineNum) {
			System.out.printf("Line %d does not exist! Total Lines: %d\n\n", lineNum, totalLineNum);
		} else {
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
		if (start > end)
			System.out.println("To must be a larger number than From.\n");
		else {
			if (end > totalLineNum)
				end = totalLineNum;
			if (start == 0)
				start++;

			Node current = getNode(start, head);

			if (current != null) {
				for (int i = start; i <= end; i++) {
					System.out.printf("%d: %s\n", i, current.line.getData());
					current = current.next;
				}
			}
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
	public void copyRange(int start, int end) {
		bufferHead = new Node();
		Node current = getNode(start, head);
		while (current != null && start <= end) {
			insertTail(current.line.getData(), bufferHead);
			current = current.next;
			end--;
		}

	}

	/**
	 * pasteLines pastes the current document buffer after the specified line.
	 * 
	 * @param start
	 *            Index of the line to paste the document buffer after.
	 */
	public void pasteLines(int start) {
		Node current = getNode(start, head);
		if (current != null) {
			Node currentBuffer = bufferHead.next;
			while (currentBuffer != null && current != null) {
				Node toAdd = new Node(currentBuffer.line.getData());

				toAdd.prev = current;
				toAdd.next = current.next;
				current.next = toAdd;
				if (current.next != null)
					current.next.prev = toAdd;

				currentBuffer = currentBuffer.next;
				current = current.next;
			}
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
		Node current = getNode(lineNumber,head);
		current.line.lineMenu(in);
	}

	/**
	 * Displays the entire document to the console.
	 */
	public void printDoc() {
		Node current = head.next;
		int count = 1;

		System.out.println();

		while (current != null) {
			System.out.printf("%d:%s\n", count, current.line.getData());
			current = current.next;
			count++;
		}

		System.out.println();

	}

	/**
	 * toString prints out the string representation of the document.
	 */
	@Override
	public String toString() {
		Node current = head.next;
		String toReturn = "";
		int count = 1;

		while (current != null) {
			toReturn += (count + ":" + current.line.getData() + "\n");
			current = current.next;
			count++;
		}

		return toReturn;
	}

	/**
	 * insertHead adds a new node to the head of the list. This represents
	 * adding a new line to the document.
	 * 
	 * @param data
	 *            The line that is to be added to the document.
	 * @param curHead
	 *            Head node of the document to insert into.
	 */
	private void insertHead(String data, Node curHead) {
		Node toAdd = new Node(data);
		if (isEmpty(curHead)) {
			curHead.next = new Node(data);
			toAdd.prev = curHead;
		} else {
			toAdd.next = curHead.next;
			toAdd.prev = curHead;
			curHead.next = toAdd;
		}
	}

	/**
	 * insertHead adds a new node to the end of the list. This represents adding
	 * a new line to the document.
	 * 
	 * @param data
	 *            The line that is to be added to the document.
	 * @param curHead
	 *            Head node of the document to insert into.
	 */
	private void insertTail(String data, Node curHead) {
		Node toAdd = new Node(data);
		if (isEmpty(curHead)) {
			curHead.next = toAdd;
			toAdd.prev = curHead;
		} else {
			Node current = curHead.next;
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
		if (isEmpty(curHead))
			return false;

		Node current = findNode(target, curHead);
		if (current == null)
			return false;

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
		Node current = findNode(target, curHead);

		if (current == null)
			return false;

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
	private Node getNode(int nodeNum, Node curHead) {
		if (isEmpty(curHead))
			return null;
		if (nodeNum > totalLineNum)
			return null;

		Node cur = curHead.next;
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
		if (isEmpty(curHead))
			return null;

		Node current = curHead.next;
		while (current.next != null && !current.line.getData().equals(target))
			current = current.next;
		if (current.line.getData().equals(target))
			return current;
		else
			return null;
	}

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
}
