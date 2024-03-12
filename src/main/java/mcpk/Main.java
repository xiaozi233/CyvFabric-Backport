package mcpk;

import java.util.Scanner;

/**This class is only for an in-console testing environment.
 * @author Morpheye
 */
final class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input;
		Player player;
		
		while (true) {
			player = new Player();
			System.out.println("Input functions to parse:");
			
			input = scanner.nextLine();
			System.out.println("Parsing: " + input);
			
			try {
				(new Parser()).parse(player, input);
				player.print();
			} catch (Exception e) {
				if (input.equals("end")) break;
				else e.printStackTrace();
			}
			
		}
		
		scanner.close();

	}

}
