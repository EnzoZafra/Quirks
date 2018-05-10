package cmput301f17t12.quirks.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class LoginActivity extends AppCompatActivity {
    private String userName;
    private Button btnLogin;
    private TextView textUsername;
    private AlertDialog.Builder builder;

    private Boolean pressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(this);

        btnLogin = (Button) findViewById(R.id.loginBtn);
        textUsername = (TextView) findViewById(R.id.loginUser);

        btnLogin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change color
                    btnLogin.setTextColor(0xFFFFFFFF);



                    if (textUsername.getText().length() <= 0 ){
                        emptyUsernameDialog();
                    }
//                    else if (name not in DATABASE){
//                        usernameTakenDialog();
//                    }

                    else{
                        User newUser = new User(textUsername.getText().toString(),new Inventory(), new ArrayList<User>(), new QuirkList());
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainIntent);
                    }



                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // set to normal color
                    btnLogin.setTextColor(0xFF7B8C94);

                }

                return true;
            }
        });

    }

    private void loginUser(){
    }

//    //Need to check, is signing up new users in the spec??
//    private void signUpUser(){
//    }



    public void emptyUsernameDialog() {

        builder.setMessage("Enter a Username.")
                .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ;
                    }
                })
                .setTitle("Empty Username");

        builder.show();
    }
    public void usernameTakenDialog() {

        builder.setMessage("The username is already taken. Please enter a new username.")
                .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ;
                    }
                })
                .setTitle("Username Taken");

        builder.show();
    }
}

