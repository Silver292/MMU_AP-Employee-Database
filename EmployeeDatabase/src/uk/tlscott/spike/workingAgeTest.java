package uk.tlscott.spike;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class workingAgeTest {

	@Test
	public void cantWorkIfUnderSixteen() throws Exception {
		LocalDate youngDOB = LocalDate.of(2011, 01, 01);
		assertFalse(DateSpike.canWork(youngDOB));
	}

	@Test
	public void canWorkIfYourBirthday() throws Exception {
		LocalDate birthdayDOB = LocalDate.of(2000, 11, 30);
		assertTrue(DateSpike.canWork(birthdayDOB));
	}
	
	@Test
	public void canWorkIfOverSixteen() throws Exception {
		LocalDate oldDOB = LocalDate.of(1988, 05, 30);
		assertTrue(DateSpike.canWork(oldDOB));
	}

}
