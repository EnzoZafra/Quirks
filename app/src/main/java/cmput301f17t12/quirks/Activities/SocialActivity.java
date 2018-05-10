package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cmput301f17t12.quirks.Helpers.BottomNavigationViewHelper;
import cmput301f17t12.quirks.R;

public class SocialActivity extends BaseActivity {
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationView = (BottomNavigationView) findViewById(R.id.top_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.removeShiftMode(navigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_geomap:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.action_trade:
                startActivity(new Intent(this, TradeActivity.class));
                break;
            case R.id.action_friends:
                startActivity(new Intent(this, FriendActivity.class));
                break;
            case R.id.action_request:
                startActivity(new Intent(this,RequestActivity.class));
                break;
            case R.id.action_findFriends:
                startActivity(new Intent(this,FindFriendActivity.class));
                break;
            case R.id.action_feed:
                startActivity(new Intent(this,FeedActivity.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.action_quirklist:
                startActivity(new Intent(this, QuirksActivity.class));
                break;
            case R.id.action_newevent:
                startActivity(new Intent(this, NewEventActivity.class));
                break;
            case R.id.action_social:
                startActivity(new Intent(this, FriendActivity.class));
                break;
        }
        finish();
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_friends;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_social;
    }
}
