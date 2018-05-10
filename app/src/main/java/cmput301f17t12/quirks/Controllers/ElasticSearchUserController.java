package cmput301f17t12.quirks.Controllers;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchUserController {

    private static JestDroidClient client;
    private static String indexString = "cmput301f17t12_quirks";
    private static String typeString = "users";

    public static class AddUsersTask extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index(indexString).type(typeString).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded())
                    {
                        user.setId(result.getId());
                        Log.i("Error", "Elasticsearch successful on:" + indexString);
                        return result.getId();
                    }
                    else
                    {
                        // the index doesn't exist (possibly also just down)
                        Log.i("Error", "Index not found or failed to load " + indexString);
                        Log.i("Error", "Error " + Integer.toString(result.getResponseCode()));
                        return null;
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                    return null;
                }
            }
            return null;
        }
    }


    public static class UpdateUserTask extends AsyncTask<User, Void, Integer> {

        @Override
        protected Integer doInBackground(User... users) {
            verifySettings();
            Index index = new Index.Builder(users[0]).index(indexString).type(typeString).id(users[0].getId()).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                if (result.isSucceeded())
                {
                    Log.i("Error", "updated user: " + users[0].getUsername());
                    return 1;
                }
                else
                {
                    // the index doesn't exist (possibly also just down)
                    Log.i("Error", "Index not found or failed to load " + indexString);
                    Log.i("Error", "Error " + Integer.toString(result.getResponseCode()));
                    return -1;
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users");
            }

            return -1;
        }
    }

    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            // Build the query
            Log.i("Error", "Building index on:" + indexString);
            if (search_parameters.length < 1){
                return null;
            }
            Search search = new Search.Builder(search_parameters[0])
                    .addIndex(indexString)
                    .addType(typeString)
                    .build();
            try {
                Log.i("Error", "Executing on:" + indexString);
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<User> foundUsers = result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                }
                else{
                    // the index doesn't exist (possibly also just down)
                    Log.i("Error", "Index not found or failed to load " + indexString);
                    Log.i("Error", "Error " + Integer.toString(result.getResponseCode()));
                    return null;
                }
            }
            catch (Exception e) {
                // no internet
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", "(No internet connection)");
                Log.i("Error", e.toString());
                return null;
            }

            return users;
        }
    }

    // gets users from elastic search
    public static class GetQuirksTask extends AsyncTask<String, Void, ArrayList<Quirk>> {
        @Override
        protected ArrayList<Quirk> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Quirk> quirks = new ArrayList<Quirk>();


            // Build the query

            if (!search_parameters[1].equals("causeFailure")){
                return new ArrayList<Quirk>();

            }
            else{
                Log.i("Error", "avoided failure");
            }

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex(indexString)
                    .addType(typeString)
                    .build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<Quirk> foundQuirks = result.getSourceAsObjectList(Quirk.class);
                    quirks.addAll(foundQuirks);
                }
                else{
                    // the index doesn't exist (possibly also just down)
                    Log.i("Error", "Index not found or failed to load " + indexString);
                    Log.i("Error", "Error " + Integer.toString(result.getResponseCode()));
                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
                return null;
            }

            return quirks;
        }
    }

    public static class GetSingleUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... username) {
            verifySettings();


            // Build the query

            System.out.print("search_parameters[0]: ");
            System.out.println(username[0]);

            Get get = new Get.Builder(indexString, username[0]).type(typeString).build();



            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    User user = result.getSourceAsObject(User.class);
                    return user;

                }
                else{
                    // the index doesn't exist (possibly also just down)
                    Log.i("Error", "Index not found or failed to load " + indexString);
                    Log.i("Error", "Error " + Integer.toString(result.getResponseCode()));
                    return null;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }
            return new User("fake name", new Inventory(), new ArrayList<String>(),new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        }
    }

    public static class DeleteUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            Delete delete = new Delete.Builder(users[0].getId()).index(indexString).type(typeString).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded())
                {
                    Log.i("Error", "deleted user: " + users[0].getUsername());
                }
                else
                {
                    Log.i("Error", "Elasticsearch was not able to delete the user");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users");
            }

            return null;
        }
    }


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
