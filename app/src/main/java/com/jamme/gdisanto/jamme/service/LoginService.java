package com.jamme.gdisanto.jamme.service;

import android.location.Location;
import android.util.Log;

import com.jamme.gdisanto.jamme.model.UserModel;

import org.w3c.dom.UserDataHandler;


/**
 * This is a class that implements the logic related to the LoginManagement
 * <p/>
 * Created by Massimo Carli on 05/06/13.
 */
public final class LoginService {

    /**
     * The Tag for the log
     */
    private static final String TAG_LOG = LoginService.class.getName();

    /**
     * The dummy username
     */
    private static final String DUMMY_USERNAME = "pippo";

    /**
     * The dummy password
     */
    private static final String DUMMY_PASSWORD = "daisy";

    /**
     * The Singleton instance
     */
    private static LoginService instance;

    /**
     * @return The singleton instance of the LoginService
     */
    public synchronized static LoginService get() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    /**
     * This login implementation just check if the username and values are valid and
     * return a UserModel implementation with some data.
     *
     * @param username The username
     * @param password The password
     * @return The UserModel with user data if logged and null if not
     */
    public UserModel login(final String username, final String password) {
        // The return value
        UserModel userModel = null;
        // We check with the dummy data
        Log.i(TAG_LOG, "User: "+username+"  pass: "+password);
        if (DUMMY_USERNAME.equalsIgnoreCase(username) && DUMMY_PASSWORD.equalsIgnoreCase(password)) {
            // We return the UserModel with some dummy information
            userModel = UserModel.create(System.currentTimeMillis()).withEmail("dummy@daisy.com");
        } else {
            // In this case we return null because we don't have information about the birthDate of the user
        }
        // We return the model if any
        return userModel;
    }

}
