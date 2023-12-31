package com.vn.utils;

public class ConstValue {

//    at least 8 characters
//- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number
//- Can contain special characters
    public final static String PASSWORD_REG = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
}
