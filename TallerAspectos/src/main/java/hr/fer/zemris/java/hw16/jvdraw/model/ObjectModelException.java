package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the input field that represents a geometrical object's attribute is not in
 * compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class ObjectModelException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new object model exception .
	 */
	public ObjectModelException() {
		super();
	}

	/**
	 * Instantiates a new object model exception with the specified detail message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public ObjectModelException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new object model exception with the provided warning message
	 * and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public ObjectModelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new object model exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public ObjectModelException(Throwable cause) {
		super(cause);
	}

}
