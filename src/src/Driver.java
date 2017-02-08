package src;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Document doc = new Document();
		Scanner in = new Scanner(System.in);
		int sent = 1;
		String input;
		
		while (sent == 1) {
			printDocMenu();
			String res = in.nextLine();
			switch (res) {
			case "m":
				break;
			case "dl":
				System.out.print("Line Number: ");
				doc.removeLine(in.nextLine());
				break;
			case "l":
				System.out.print("\nFile Name: ");
				input = in.nextLine();
				doc.loadFile(input);
				break;
			case "dr":
				break;
			case "sa":
				doc.printDoc();
				break;
			case "cr":
				break;
			case "sl":
				break;
			case "pl":
				break;
			case "sr":
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
				break;
			case "wq":
				System.out.print("\nFile Name: ");
				input = in.nextLine();
				doc.writeToFile(input);
				System.out.println("Text written to "+input);
				sent = 0;
				in.close();
				break;
			default:
				System.out.println("Please enter a valid option.\n");
				break;
			}
		}

	}

	public static void printDocMenu() {
		System.out.printf("Menu: m           Delete line:    dl\n" + "Load file:   l    Delete range:   dr\n"
				+ "Show all:   sa    Copy range:     cr\n" + "Show line:  sl    Paste lines:    pl\n"
				+ "Show range: sr    Write to file:   w\n" + "New line:   nl    Quit:            q\n"
				+ "Edit line:  el    Write and quit: wq\n" + "->");
	}

	public static void printLineMenu() {
		System.out.printf(
				"\nShow line:  s\n" + "Copy to string buffer:  c\n" + "Cut:  t\n" + "Paste from string buffer:  p\n"
						+ "Enter new substring:  e\n" + "Delete substring:  d\n" + "Quit line:  q\n" + "->");
	}
}
