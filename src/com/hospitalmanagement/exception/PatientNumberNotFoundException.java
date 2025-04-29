package com.hospitalmanagement.exception;

public class PatientNumberNotFoundException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor to pass a custom error message
    public PatientNumberNotFoundException(String message) {
        super(message);
    }
}
