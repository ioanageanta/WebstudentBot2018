package com.ase.simpre.webstudentbot2018;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private Button enterButton;
    private Button registerButton;
    private String androidId;
    private Utils utils;
    private String email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        utils = new Utils();
        enterButton = findViewById(R.id.enter_button);
        registerButton = findViewById(R.id.register_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo check why alert appears?!?!?!?!
                Thread authorizeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user = utils.authorize(androidId);
                    }
                });
                authorizeThread.start();

                if (user == null && !authorizeThread.isAlive()) {
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
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
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
}
