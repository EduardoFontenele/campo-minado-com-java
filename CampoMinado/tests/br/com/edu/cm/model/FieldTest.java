package br.com.edu.cm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.edu.cm.exceptions.ExplosionException;

class FieldTest {
	
	private Field field;
	
	@BeforeEach
	void initField() {
		field = new Field(3, 3);
	}

	@Test
	void testNeighborIsReal() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighborField(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborIsReal2() {
		Field neighbor = new Field(3, 4);
		boolean result = field.addNeighborField(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborIsReal3() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighborField(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborIsReal4() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighborField(neighbor);
		
		assertTrue(result);
	}
	
	@Test
	void testNeighborIsNotReal() {
		Field neighbor = new Field(4, 5);
		boolean result = field.addNeighborField(neighbor);
		
		assertFalse(result);
	}
	
	@Test
	void flaggingDefaultValueIsFalse() {
		assertFalse(field.checkIfIsFlagged());
	}
	
	@Test
	void alternateFlaggingReturnsTrue() {
		field.alternateFlagging();
		assertTrue(field.checkIfIsFlagged());
	}
	
	@Test
	void alternateFlaggingReturnsFalse() {
		field.alternateFlagging();
		field.alternateFlagging();
		assertFalse(field.checkIfIsFlagged());
	}
	
	@Test
	void openNotUnderminedAndNotFlagged() {
		assertTrue(field.openField());
	}

	@Test
	void openNotUnderminedAndFlagged() {
		field.alternateFlagging();
		assertFalse(field.openField());
	}
	
	@Test
	void openUnderminedNotFlagged() {
		field.setUndermined();
		
		assertThrows(ExplosionException.class, () -> {
			field.openField();
		});
	}


}
