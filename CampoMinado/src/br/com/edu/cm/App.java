package br.com.edu.cm;

import br.com.edu.cm.model.Board;
import br.com.edu.cm.views.BoardConsole;

public class App {

	public static void main(String[] args) {
		
		Board board = new Board(10, 10, 3);	
		new BoardConsole(board);
		
	}

}
