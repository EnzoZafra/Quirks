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
import cmput301f17t12.quirks.Models.Event;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchEventController {

    private static JestDroidClient client;
    private static String indexString = "cmput301f17t12_quirks";
    private static String typeString = "events";

    // TODO we need a function which adds events to elastic search
    public static class AddEventsTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            verifySettings();

            for (Event event : events) {
//                GorupName_ProjectName
                Index index = new Index.Builder(event).index(indexString).type(typeString).build();
//                Index index = new Index.Builder(event).index().type().build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded())
                    {
                        event.setId(result.getId());
                        System.out.println("added event: " + event.getComment());
                        System.out.println("id: " + event.getId());
                    }
                    else
                    {
                        Log.i("Error", "Elasticsearch was not able to add the event");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the events");
                }

            }
            return null;
        }
    }

    public static class UpdateEventTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            verifySettings();
            System.out.println("Trying to update: " + events[0].getComment());
            Index index = new Index.Builder(events[0]).index(indexString).type(typeString).id(events[0].getId()).build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                if (result.isSucceeded())
                {
//                        users[0].setId(result.getId());
                    System.out.println("updated event: " + events[0].getComment());
//                        System.out.println("id: " + user.getId());
                }
                else
                {
                    Log.i("Error", "Elasticsearch was not able to update the event");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the events");
            }

            return null;
        }
    }

    // TODO we need a function which gets events from elastic search
    public static class GetEventsTask extends AsyncTask<String, Void, ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Event> events = new ArrayList<Event>();

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
                    List<Event> foundEvents = result.getSourceAsObjectList(Event.class);
                    events.addAll(foundEvents);
                    System.out.println("found size: " + foundEvents.size());
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }

            return events;
        }
    }


    // TODO we need a function which gets users from elastic search
    public static class GetSingleEventTask extends AsyncTask<String, Void, Event> {
        @Override
        protected Event doInBackground(String... events) {
            verifySettings();

//            User user = new User

            // TODO Build the query

            System.out.print("search_parameters[0]: ");
            System.out.println(events[0]);

            Get get = new Get.Builder(indexString, events[0]).type(typeString).build();



            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    Event event = result.getSourceAsObject(Event.class);
                    System.out.println("found single user: " + event.getComment());
                    return event;

                }
                else{
                    System.out.println("fail no error");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }
            return new Event("fakeuser", "comment", new Date(), "Test");
        }
    }

    public static class DeleteEventTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            verifySettings();
            System.out.println("Trying to delete: " + events[0].getComment());
//            Index index = new Index.Builder(users[0]).index(indexString).type(typeString).id(users[0].getId()).build();
            Delete delete = new Delete.Builder(events[0].getId()).index(indexString).type(typeString).build();

            try {
                // where is the client?
//                DocumentResult result = client.execute(index);
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded())
                {
//                        users[0].setId(result.getId());
                    System.out.println("deleted event: " + events[0].getComment());
//                        System.out.println("id: " + user.getId());
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
