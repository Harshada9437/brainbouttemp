package com.heednow.exceptions;

/**
 * Created by System-3 on 2/7/2017.
 */
public class FeedbackNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public FeedbackNotFoundException(String message){
        super(message);
    }

}
