package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Document {

	private Node head;
	private int lineNum;

	public Document() {
		head = new Node();
		lineNum = 0;
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
				cur = getNode(afterLineNum);
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
						System.out.printf("%d:", lineNum + 1);
						lineNum++;
						insertTail(in.nextLine());

					}
					// Otherwise we are adding after a node.
					else {
						System.out.printf("%d:", afterLineNum + 1);
						afterLineNum++;
						lineNum++;
						if (cur != null) {
							insertAfter(cur.line.getData(), in.nextLine());
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
	 * @param num String index of line to remove
	 */
	public void removeLine(String num) {
		if (!isEmpty()) {
			int toDelete = Integer.parseInt(num);
			if (!(toDelete > lineNum)) {
				removeNode(getNode(toDelete).line.getData());
				lineNum--;
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
			if(lineNum!=0){
				head.next=null;
				lineNum=0;
			}
			while (in.hasNextLine()) {
				insertTail(in.nextLine());
			}
			in.close();
			return true;
		} catch (Exception e) {
			System.out.println(filename + " does not exist");
			return false;
		}
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

	@Override
	public String toString(){
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
	 */
	private void insertHead(String data) {
		Node toAdd = new Node(data);
		if (isEmpty()) {
			head.next = new Node(data);
			toAdd.prev = head;
		} else {
			toAdd.next = head.next;
			toAdd.prev = head;
			head.next = toAdd;
		}
	}

	/**
	 * insertHead adds a new node to the end of the list. This represents adding
	 * a new line to the document.
	 * 
	 * @param data
	 *            The line that is to be added to the document.
	 */
	private void insertTail(String data) {
		Node toAdd = new Node(data);
		if (isEmpty()) {
			head.next = toAdd;
			toAdd.prev = head;
		} else {
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
	 * 
	 * @return Boolean flag for success or failure.
	 */
	private boolean insertAfter(String target, String data) {
		if (isEmpty())
			return false;

		Node current = findNode(target);
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
	 * 
	 * @return Boolean flag for success or failure.
	 */
	private boolean removeNode(String target) {
		Node current = findNode(target);

		if (current == null)
			return false;

		Node tmp = current.prev;
		if(current.next != null)
			current.next.prev = tmp;
		tmp.next = current.next;
		return true;

	}

	/**
	 * isEmpty returns true if the document has no lines in it or false if it
	 * does.
	 * 
	 * @return Boolean flag for empty doc. If true the document is empty.
	 */
	private boolean isEmpty() {
		return head.next == null;
	}

	/**
	 * Retrieves the node at the position specified by nodeNum.
	 * 
	 * @param nodeNum
	 *            Index of the node to return.
	 * @return
	 */
	private Node getNode(int nodeNum) {
		if (isEmpty())
			return null;
		if (nodeNum > lineNum)
			return null;

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
	 * @return
	 */
	private Node findNode(String target) {
		if (isEmpty())
			return null;

		Node current = head.next;
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
