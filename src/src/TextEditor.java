package src;

import java.util.Scanner;

public class TextEditor {

	private Document doc,lineBuffer;
	private Scanner in;
	
	public TextEditor() {
		doc = new Document();
		lineBuffer = new Document();
		in = new Scanner(System.in);
	}

	/**
	 * menu handles the text editor menu logic
	 */
	public void menu() {
		//Declare variables
		int to, from, lineNum;
		int sent = 1;
		String input;

		//While the sentinel value is 1 keep looping through the menu
		while (sent == 1) {
			//Print the doc menu
			printDocMenu();
			
			//Get user input
			String res = in.nextLine();
			
			//Switch to handle which menu function the user selected
			switch (res) {
			case "m":
				break;
			case "dl":
				System.out.print("Line Number: ");
				int num = Integer.parseInt(in.nextLine());
				doc.removeLine(num);
				break;
			case "l":
				System.out.print("\nFile Name: ");
				input = in.nextLine();
				doc.loadFile(input);
				break;
			case "dr":
				System.out.print("From: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("To: ");
				to = Integer.parseInt(in.nextLine());
				doc.removeRange(from, to);
				break;
			case "sa":
				doc.printDoc();
				break;
			case "cr":
				System.out.print("From: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("To: ");
				to = Integer.parseInt(in.nextLine());
				doc.copyRange(from, to,lineBuffer);
				break;
			case "sl":
				System.out.print("\nLine Number: ");
				lineNum = Integer.parseInt(in.nextLine());
				doc.showLine(lineNum);
				break;
			case "pl":
				System.out.print("Paste after line: ");
				from = Integer.parseInt(in.nextLine());
				doc.pasteLines(from,lineBuffer);
				break;
			case "sr":
				System.out.print("\nFrom: ");
				from = Integer.parseInt(in.nextLine());
				System.out.print("\nTo: ");
				to = Integer.parseInt(in.nextLine());
				doc.showRange(from, to);
				break;
			case "w":
				System.out.print("\nFile Name: ");
				doc.writeToFile(in.nextLine());
				break;
			case "nl":
				doc.addLine(in);
				break;
			case "q":
				System.out.println("Quiting");
				sent = 0;
				in.close();
				break;
			case "el":
				System.out.print("\nLine Number: ");
				lineNum = Integer.parseInt(in.nextLine());
				doc.editLine(in, lineNum);
				break;
			case "wq":
				System.out.print("\nFile Name: ");
				input = in.nextLine();
				doc.writeToFile(input);
				System.out.println("Text written to " + input);
				sent = 0;
				in.close();
				break;
			default:
				System.out.println("Please enter a valid option.\n");
				break;
			}
		}
	}

	private void printDocMenu() {
		System.out.printf("Menu: m           Delete line:    dl\n" + "Load file:   l    Delete range:   dr\n"
				+ "Show all:   sa    Copy range:     cr\n" + "Show line:  sl    Paste lines:    pl\n"
				+ "Show range: sr    Write to file:   w\n" + "New line:   nl    Quit:            q\n"
				+ "Edit line:  el    Write and quit: wq\n" + "->");
	}

}
