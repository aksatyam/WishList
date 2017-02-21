package com.example.sharma.wishlist;

import android.os.Bundle;

/**
 * Created by Ashish Kumar Satyam on 2/20/2017.
 */

public class UserDetail {
    private static Bundle user;

    public UserDetail(){

    }
    public void putName(Bundle user){
        this.user=user;
    }
    public Bundle getName(){
        return user;
    }
}
