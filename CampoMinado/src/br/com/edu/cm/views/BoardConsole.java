package br.com.edu.cm.views;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.edu.cm.exceptions.ExplosionException;
import br.com.edu.cm.exceptions.QuitGame;
import br.com.edu.cm.model.Board;

public class BoardConsole {
	private Board board;
	private Scanner scanner = new Scanner(System.in);
	
	public BoardConsole(Board board) {
		this.board = board;
		
		executeGame();
	}

	private void executeGame() {
		try {
			boolean continueGame = true;
			
			while(continueGame) {
				gameCycle();
				System.out.println("Outra partida? (S/n)");
				String answer = scanner.nextLine();
				if("n".equalsIgnoreCase(answer)) {
					continueGame = false;
				} else {
					board.restartGame();
				}
			}
		} catch(QuitGame e) {
			System.out.println("Adeus!");
		} finally {
			scanner.close();
		}
	}

	private void gameCycle() {
		try {
			
			while(!board.objectiveAchieved()) {
				System.out.println(board);
				
				String typed = captureTypedValue("Digite (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim()))
					.iterator();
				
				typed = captureTypedValue("1 - Abrir ou 2 - (Des)marcar: ");
				
				if("1".equals(typed)) {
					board.open(xy.next(), xy.next());
				} else if ("2".equals(typed)) {
					board.alternateFlagging(xy.next(), xy.next());
				}
			}
			
			System.out.println(board);
			System.out.println("Você ganhou!");
		} catch(ExplosionException e) {
			System.out.println(board);
			System.out.println("Você perdeu!");
		}
	}
	
	private String captureTypedValue(String text) {
		System.out.print(text);
		String typed = scanner.nextLine();
		
		if("sair".equalsIgnoreCase(typed)) {
			throw new QuitGame();
		}
		
		return typed;
	}
}
