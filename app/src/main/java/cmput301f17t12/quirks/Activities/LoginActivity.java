package cmput301f17t12.quirks.Activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;
import cmput301f17t12.quirks.R;

public class LoginActivity extends AppCompatActivity {
    private String username;
    private AlertDialog.Builder builder;
    private ImageView loginBG;

    private EditText loginText;
    private Button loginButton;

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(this);

        loginText = findViewById(R.id.loginUser);
        loginButton = findViewById(R.id.loginBtn);
        loginBG = (ImageView) findViewById((R.id.loginBG));

        loginButton.setBackgroundColor(0xFFDCDCDC);
        loginBG.setBackgroundColor(0xFFDCDCDC);
        loginButton.setTextColor(0xFF7B8C94);
        loginText.setTextColor(0xFF7B8C94);

        settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        editor = settings.edit();
        context = getApplicationContext();


        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                username = loginText.getText().toString();

                String query = "{" +
                        "  \"query\": {" +
                        "    \"match\": {" +
                        "      \"username\": \"" + username + "\"" +
                        "    }" +
                        "  }" +
                        "}";

                ElasticSearchUserController.GetUsersTask getUsersTask
                        = new ElasticSearchUserController.GetUsersTask();
                getUsersTask.execute(query);

                try {
                    ArrayList<User> users = getUsersTask.get();
                    if(users != null){
                        System.out.println("size: " + users.size());
                        if (username.length() == 0){
                            emptyUsernameDialog();
                        }
                        else if (users.size() == 1){
                            System.out.println("\n\nvvv\nalready registered\n^^^\n\n");
                            loginUser(users.get(0));
                        }
                        else if (users.size() > 1){
                            Log.i("Error", "Username appears more than once in the database");
                        }
                        else{
                            registerUser(username);
                        }
                    }
                    else{ // offline behaviour: reject logins -> change to last user?
                        String text = "Failed to login: no connection to database";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Failed to get the users from the async object\n" + e.toString() +"\n.");
                }
            }
        });
    }

    private void loginUser(final User user){
        // after elasticsearch, go to main as that user
        System.out.println("Logging in as: " + user.getUsername());
        System.out.println("JestId: " + user.getId());
        editor.putString("jestID", user.getId());
        editor.putInt("offlineChanges", 0);
        editor.commit();
        new Thread(new Runnable() {
            public void run() {
                HelperFunctions.saveCurrentUser(getApplicationContext(), user);
            }
        }).start();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

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

    private void registerUser(String username){
        // create new user
//        User user = new User(username, new Inventory(), new ArrayList<User>(),new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        User user = new User(username, new Inventory(), new ArrayList<String>(),new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());

        ElasticSearchUserController.AddUsersTask addUsersTask
                = new ElasticSearchUserController.AddUsersTask();

        System.out.println("user id before execute: " + user.getId());

        addUsersTask.execute(user);

        try{
            String jestID = addUsersTask.get();
            if (jestID != null){
                editor.putString("jestID", jestID);
                System.out.println("user id after execute: " + user.getId());
                System.out.println("Successfully added new user");
                loginUser(user);
            }
            else{
                String text = "Failed to register new user";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        catch (Exception e){
            Log.i("Error", "Failed to register user from the async object\n" + e.toString() +"\n.");
        }
    }
}