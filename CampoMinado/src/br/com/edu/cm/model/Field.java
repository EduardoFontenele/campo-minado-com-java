package br.com.edu.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.edu.cm.exceptions.ExplosionException;

public class Field {
	
	private boolean isFlagged;
	private boolean isUndermined;
	private boolean isOpen;
	
	private final int LINE;
	private final int COLUMN;
	
	private List<Field> neighborsFields = new ArrayList<>();
	
	public Field(int line, int column) {
		this.LINE = line;
		this.COLUMN = column;
	}
	
	boolean addNeighborField(Field neighbor) {
		boolean differentLine = LINE != neighbor.LINE;
		boolean differentColumn = COLUMN != neighbor.COLUMN;
		boolean diagonal = differentLine && differentColumn;
		
		int deltaLine = Math.abs(LINE - neighbor.LINE);
		int deltaColumn = Math.abs(COLUMN - neighbor.COLUMN);
		int deltaOverall = deltaColumn + deltaLine;
		
		if(deltaOverall == 1 && !diagonal) {
			neighborsFields.add(neighbor);
			return true;
		} else if(deltaOverall == 2 && diagonal) {
			neighborsFields.add(neighbor);
			return true;
		} else {
			return false;
		}
	}
	
	void setUndermined() {
		isUndermined = true;
	}
	
	public void alternateFlagging() {
		if(!isOpen) {
			isFlagged = !isFlagged;
		}
	}
	
	boolean openField() {
		
		if(!isOpen && !isFlagged) {
			isOpen = true;
			
			if(isUndermined) {
				throw new ExplosionException();
			}
			
			if(safeNeighborhood()) {
				neighborsFields.forEach(n -> n.openField());
			}
			
			return true;
		} else {
			return false;	
		}
	}
	
	boolean safeNeighborhood() {
		return neighborsFields.stream().noneMatch(n -> n.isUndermined);
	}
	
	public boolean checkIfIsFlagged() {
		return isFlagged;
	}
	
	public boolean checkIfIsOpen() {
		return isOpen;
	}
	
	public boolean checkIfIsUndermined() {
		return isUndermined;
	}
	
	public boolean checkIfIsClosed() {
		return !isOpen;
	}

	public int getLINE() {
		return LINE;
	}

	public int getCOLUMN() {
		return COLUMN;
	}
	
	boolean objcetiveAchieved() {
		boolean revealedField = !isUndermined && isOpen;
		boolean protectedField = isUndermined && isFlagged;
		return revealedField || protectedField;
	}
	
	long minesAtNeighborhood() {
		return neighborsFields.stream().filter(n -> n.isUndermined).count();
	}
	
	void restart() {
		isOpen = false;
		isUndermined = false;
		isFlagged = false;
	}
	
	public String toString() {
		if(isFlagged) {
			return "x";
		} else if(isOpen && isUndermined) {
			return "*";
		} else if(isOpen && minesAtNeighborhood() > 0) {
			return Long.toString(minesAtNeighborhood());
		} else if (isOpen) {
			return " ";
		} else {
			return "?";
		}
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
