package app.core.exceptions;

public class TravelBudgetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TravelBudgetException() {
		super();
	}

	public TravelBudgetException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TravelBudgetException(String message, Throwable cause) {
		super(message, cause);
	}

	public TravelBudgetException(String message) {
		super(message);
	}

	public TravelBudgetException(Throwable cause) {
		super(cause);
	}

	public TravelBudgetException(String string, String message) {
		// TODO Auto-generated constructor stub
	}

}
