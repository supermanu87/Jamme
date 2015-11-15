package com.jamme.gdisanto.jamme.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Date;

/**
 * This is the class that encaspulate the Model of the user for this application
 * <p/>
 * Created by Massimo Carli on 04/06/13.
 */
public class UserModel implements Parcelable {

    /**
     * Constant that identify an existing data
     */
    private static final byte PRESENT = 1;

    /**
     * Constant that identify a NON existing data
     */
    private static final byte NOT_PRESENT = 0;

    /**
     * Implementation of a CREATOR for the creation of the instance
     */
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    /**
     * The username for this user.
     */
    private String mUsername;

    /**
     * The email of the user.
     */
    private String mEmail;

    /**
     * The birthDate of the user. To calculate the sign
     */
    private long mBirthDate;

    /**
     * The location of the user
     */
    private String mLocation;


    /**
     * Creates the UserModel with the mandatory information
     *
     * @param birthDate The birthDate of the user as a long
     */
    private UserModel(final long birthDate) {
        this.mBirthDate = birthDate;
    }

    /**
     * Creates the UserModel from the Parcel
     *
     * @param in The Parcel to read from
     */
    public UserModel(Parcel in) {
        // Mandatory information
        this.mBirthDate = in.readLong();
        // We have to restore the state from the Parcel
        if (in.readByte() == PRESENT) {
            this.mUsername = in.readString();
        }
        if (in.readByte() == PRESENT) {
            this.mEmail = in.readString();
        }
        if (in.readByte() == PRESENT) {
            this.mLocation = in.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Mandatory information
        dest.writeLong(mBirthDate);
        // Manage optional mUsername information
        if (!TextUtils.isEmpty(mUsername)) {
            dest.writeByte(PRESENT);
            dest.writeString(mUsername);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
        // Manage optional mEmail information
        if (!TextUtils.isEmpty(mEmail)) {
            dest.writeByte(PRESENT);
            dest.writeString(mEmail);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
        // Manage the location information
        if (!TextUtils.isEmpty(mLocation)) {
            dest.writeByte(PRESENT);
            dest.writeString(mLocation);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
    }

    /**
     * Creates a UserModel given its birthDate
     *
     * @param birthDate The birthDate of the user as a long
     * @return The UserModel instance for chaining
     */
    public static UserModel create(final long birthDate) {
        final UserModel userModel = new UserModel(birthDate);
        return userModel;
    }

    /**
     * Adds the username information to the UserModel
     *
     * @param username The username
     * @return The UserModel itself for chaining
     */
    public UserModel withUsername(final String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null here!");
        }
        this.mUsername = username;
        // We return the instance itself for chaining
        return this;
    }

    /**
     * Adds the email information to the UserModel
     *
     * @param email The user email
     * @return The UserModel itself for chaining
     */
    public UserModel withEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null here!");
        }
        this.mEmail = email;
        // We return the instance itself for chaining
        return this;
    }

    /**
     * Adds the location information to the UserModel
     *
     * @param location The user location
     * @return The UserModel itself for chaining
     */
    public UserModel withLocation(final String location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null here!");
        }
        this.mLocation = location;
        // We return the instance itself for chaining
        return this;
    }

    /**
     * @return The username if any
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * @return The birthDate as a long
     */
    public long getBirthDate() {
        return this.mBirthDate;
    }

    /**
     * @return The user email if any
     */
    public String getEmail() {
        return this.mEmail;
    }

    /**
     * @return The location of the user if any
     */
    public String getLocation() {
        return this.mLocation;
    }

    /**
     * @return True if the user is anonymous and false otherwise.
     */
    public boolean isAnonymous() {
        return TextUtils.isEmpty(this.mUsername);
    }

    /**
     * @return True if the user is logged and false otherwise.
     */
    public boolean isLogged() {
        return !TextUtils.isEmpty(this.mUsername);
    }

}
