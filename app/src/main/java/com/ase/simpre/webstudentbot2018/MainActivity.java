package com.ase.simpre.webstudentbot2018;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private Button enterButton;
    private Button registerButton;
    private String androidId;
    private Utils utils;
    private String email;
    public  User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
          getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                  // Set the content to appear under the system bars so that the
                  // content doesn't resize when the system bars hide and show.
                  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                  | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                  // Hide the nav bar and status bar
                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                  | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barColor)));

        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        utils = new Utils();
        final AuthorizeThread authorizeThread = new AuthorizeThread();
        authorizeThread.delegate = this;
        enterButton = findViewById(R.id.enter_button);
        registerButton = findViewById(R.id.register_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authorizeThread.execute(androidId);


                if (user == null && authorizeThread.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("You are not registered on this device");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
//                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//
//                    Log.e("USER USER", user.getEmail());
//                    bundle.putSerializable("loggedUser", user);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Register your student e-mail address");

                final EditText editText = dialog.findViewById(R.id.editText);
                Button btnSave = dialog.findViewById(R.id.save);
                Button btnCancel = dialog.findViewById(R.id.cancel);
                dialog.show();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email = editText.getText().toString();
                        //todo maybe confirm by sending email to user
                        if (!email.endsWith("@stud.ase.ro")) {

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setMessage("E-mail is not valid");
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            editText.setText("");
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            Thread saveThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (utils.canBeRegistered(androidId)) {
                                            utils.saveUser(new User(email, androidId));
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(), "Device already registered", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }
                                    } catch (Exception exception) {
                                        Log.e("SAVE EXCEPTION", exception.getMessage());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                                alertDialogBuilder.setMessage("An error has occured");
                                                alertDialogBuilder.setPositiveButton("OK",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                        });
                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                            }
                                        });

                                    }
                                }
                            });
                            saveThread.start();

                            dialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void processFinish(User output) {
        user = output;
        Log.e("USEEEEEEEEEEEEER", user.getEmail());
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);

        intent.putExtra("loggedUser", user);
        startActivity(intent);
    }
}
