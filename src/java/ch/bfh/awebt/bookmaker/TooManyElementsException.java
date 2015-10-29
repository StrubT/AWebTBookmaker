package ch.bfh.awebt.bookmaker;

import java.util.NoSuchElementException;

/**
 * Represents a status where there are too many elements in an {@link Iterable}. <br>
 * This exception represents the opposite status of {@link NoSuchElementException}.
 *
 * @author strut1 &amp; touwm1
 */
public class TooManyElementsException extends RuntimeException {

	private static final long serialVersionUID = 3801154629261088655L;
}
