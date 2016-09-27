package app.com.product.auctionapp;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.product.auctionapp.data.CategoryLoader;
import app.com.product.auctionapp.data.ItemsContract;
import app.com.product.auctionapp.util.Utility;

public class CreateAuctionActivity extends AppCompatActivity  implements CreateAuctionItemFragment.OnFragmentInteractionListener {
    private static final String TAG = CreateAuctionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      //  Utility.putKeyValInSharedPref(this,Config.TicketId,"0");
//        Utility.putKeyValInSharedPref(this,MainActivity.SELECTED_LIST_POS_KEY,"0");
        setContentView(R.layout.activity_create_auction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
      //  Log.i(TAG, "Setting screen name: Create Ticket" );
        //mTracker.setScreenName("Create Ticket" );
        //mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
