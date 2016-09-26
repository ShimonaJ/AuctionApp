package app.com.product.auctionapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import app.com.product.auctionapp.util.Utility;
import app.com.work.shimonaj.helpdx.data.UpdaterService;
import app.com.work.shimonaj.helpdx.remote.Config;
import app.com.work.shimonaj.helpdx.util.Utility;

public class CreateAuctionActivity extends AppCompatActivity {
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
        Button btn =(Button)this.findViewById(R.id.create_auctionitem_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateAuctionItemClick(view);
            }

        });
//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
      //  Log.i(TAG, "Setting screen name: Create Ticket" );
        //mTracker.setScreenName("Create Ticket" );
        //mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void onCreateAuctionItemClick(View view) {
        Toast.makeText(this, getResources().getString(R.string.notification_auctionitem_post), Toast.LENGTH_LONG).show();
        Log.v(TAG,"Button click");
        int userId = Utility.getUserInfo(getApplicationContext());
        String EmpId="";
        String CompanyId="";
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Ticket Created")
//                .setAction("Share")
//                .build());

        try{
            CompanyId = userObj.getString("CompanyId");
            EmpId = userObj.getString("EmployeeId");
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        JSONObject ticket = new JSONObject();
        try {
            ticket.putOpt("Title",((TextView)this.findViewById(R.id.addTicketTitle)).getText());
            ticket.putOpt("Description",((TextView)this.findViewById(R.id.addTicketDesc)).getText());
            ticket.putOpt("TicketSource","Android App");
            ticket.putOpt("CompanyId",CompanyId);
            ticket.put("RequestorId", EmpId);

            json.put("EmployeeId", EmpId);
            json.putOpt("ticket",ticket);
            json.put("hostname","");
            json.put("tokenKey","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, UpdaterService.class);
        intent.putExtra("ticketData",json.toString());
        intent.setAction(UpdaterService.TICKET_POST);
        startService(intent);

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
    }


}
