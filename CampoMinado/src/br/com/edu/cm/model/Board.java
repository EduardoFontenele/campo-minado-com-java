package br.com.edu.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.edu.cm.exceptions.ExplosionException;

public class Board {
	
	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> FIELDS = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		super();
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		raffleMines();
	}
	
	public void open(int line, int column) {
		try {
			FIELDS.stream()
			.filter(f -> f.getLINE() == line)
			.filter(f -> f.getCOLUMN() == column)
			.findFirst()
			.ifPresent(f -> f.openField());
		} catch(ExplosionException e) {
			FIELDS.forEach(f -> f.setOpen(true));
			
			throw e;
		}
	}
	
	public void alternateFlagging(int line, int column) {
		FIELDS.stream()
		.filter(f -> f.getLINE() == line)
		.filter(f -> f.getCOLUMN() == column)
		.findFirst()
		.ifPresent(f -> f.alternateFlagging());
	}
	
	public void flag(int line, int column) {
		FIELDS.stream()
		.filter(f -> f.getLINE() == line)
		.filter(f -> f.getCOLUMN() == column)
		.findFirst()
		.ifPresent(f -> f.alternateFlagging());
	}

	private void generateFields() {
		for(int line = 0; line < lines; line ++) {
			for(int column = 0; column < columns; column++) {
				FIELDS.add(new Field(line, column));
			}
		}
		
	}
	
	private void associateNeighbors() {
		for(Field f1: FIELDS) {
			for(Field f2: FIELDS) {
				f1.addNeighborField(f2);
			}
		}
		
	}
	
	private void raffleMines() {
		long armedUndermines = 0;
		Predicate<Field> undermined = field -> field.checkIfIsUndermined();
		
		do {
			int randomValue = (int) (Math.random() * FIELDS.size());
			FIELDS.get(randomValue).setUndermined();
			armedUndermines = FIELDS.stream().filter(undermined).count();
		} while(armedUndermines < mines);
	}
	
	public boolean objectiveAchieved() {
		return FIELDS.stream().allMatch(field -> field.objcetiveAchieved());
	}
	
	public void restartGame() {
		FIELDS.stream().forEach(field -> field.restart());
		raffleMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("   ");
		for (int c = 0; c < columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for(int line = 0; line < lines; line++) {
			sb.append(" ");
			sb.append(line);
			sb.append(" ");
			
			for(int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(FIELDS.get(i));
				sb.append(" ");
				i++;
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
