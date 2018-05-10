package cmput301f17t12.quirks.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Quirk;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchQuirkController {

    private static JestDroidClient client;
    private static String indexString = "cmput301f17t12_quirks";
    private static String typeString = "quirks";

    // TODO we need a function which adds quirks to elastic search
    public static class AddQuirksTask extends AsyncTask<Quirk, Void, Void> {

        @Override
        protected Void doInBackground(Quirk... quirks) {
            verifySettings();

            for (Quirk quirk : quirks) {
//                GorupName_ProjectName
                Index index = new Index.Builder(quirk).index(indexString).type(typeString).build();
//                Index index = new Index.Builder(quirk).index().type().build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded())
                    {
                        quirk.setId(result.getId());
                        System.out.println("added quirk: " + quirk.getType());
                        System.out.println("id: " + quirk.getId());
                    }
                    else
                    {
                        Log.i("Error", "Elasticsearch was not able to add the quirk");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the quirks");
                }

            }
            return null;
        }
    }


    public static class UpdateQuirkTask extends AsyncTask<Quirk, Void, Void> {

        @Override
        protected Void doInBackground(Quirk ... quirks) {
            verifySettings();
            System.out.println("Trying to update: " + quirks[0].getType());
            Index index = new Index.Builder(quirks[0]).index(indexString).type(typeString).id(quirks[0].getId()).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                if (result.isSucceeded())
                {
//                        users[0].setId(result.getId());
                    System.out.println("updated quirk: " + quirks[0].getType());
//                        System.out.println("id: " + user.getId());
                }
                else
                {
                    Log.i("Error", "Elasticsearch was not able to update the quirk");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the quirks");
            }

            return null;
        }
    }

    // TODO we need a function which gets quirks from elastic search
    public static class GetQuirksTask extends AsyncTask<String, Void, ArrayList<Quirk>> {
        @Override
        protected ArrayList<Quirk> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Quirk> quirks = new ArrayList<Quirk>();

            // TODO Build the query

            System.out.print("search_parameters[0]: ");
            System.out.println(search_parameters[0]);

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex(indexString)
                    .addType(typeString)
                    .build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<Quirk> foundQuirks = result.getSourceAsObjectList(Quirk.class);
                    quirks.addAll(foundQuirks);
                    System.out.println("found size: " + foundQuirks.size());
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }

            return quirks;
        }
    }


    // TODO we need a function which gets users from elastic search
    public static class GetSingleQuirkTask extends AsyncTask<String, Void, Quirk> {
        @Override
        protected Quirk doInBackground(String... quirkname) {
            verifySettings();

//            User user = new User

            // TODO Build the query

            System.out.print("search_parameters[0]: ");
            System.out.println(quirkname[0]);

            Get get = new Get.Builder(indexString, quirkname[0]).type(typeString).build();



            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    Quirk quirk = result.getSourceAsObject(Quirk.class);
                    System.out.println("found single quirk: " + quirk.getType());
                    return quirk;

                }
                else{
                    System.out.println("fail no error");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }
            return new Quirk("errTitle", "errType", new Date(), new ArrayList<Day>(), 10, "user", "for lolz");
        }
    }


    public static class DeleteUserTask extends AsyncTask<Quirk, Void, Void> {

        @Override
        protected Void doInBackground(Quirk... quirks) {
            verifySettings();
            System.out.println("Trying to delete: " + quirks[0].getType());
//            Index index = new Index.Builder(users[0]).index(indexString).type(typeString).id(users[0].getId()).build();
            Delete delete = new Delete.Builder(quirks[0].getId()).index(indexString).type(typeString).build();

            try {
                // where is the client?
//                DocumentResult result = client.execute(index);
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded())
                {
//                        users[0].setId(result.getId());
                    System.out.println("deleted quirk: " + quirks[0].getType());
//                        System.out.println("id: " + user.getId());
                }
                else
                {
                    Log.i("Error", "Elasticsearch was not able to delete the quirk");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the quirks");
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
