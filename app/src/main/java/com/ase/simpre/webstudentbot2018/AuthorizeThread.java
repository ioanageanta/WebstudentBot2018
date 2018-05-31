package com.ase.simpre.webstudentbot2018;

import android.os.AsyncTask;

public class AuthorizeThread extends AsyncTask<String, Void, User> {

    public AsyncResponse delegate = null;

    @Override
    protected User doInBackground(String... strings) {
        Utils utils = new Utils();
        return utils.authorize(strings[0]);
    }

    @Override
    protected void onPostExecute(User result) {
        delegate.processFinish(result);
    }
}
