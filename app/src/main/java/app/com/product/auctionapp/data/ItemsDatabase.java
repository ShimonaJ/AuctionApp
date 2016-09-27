package app.com.product.auctionapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.product.auctionapp.R;


public class ItemsDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "auction.db";
    private static final int DATABASE_VERSION = 4;

    public ItemsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsProvider.Tables.USERS + " ("
                + ItemsContract.UsersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsContract.UsersColumns.NAME + " TEXT NOT NULL,"
                + ItemsContract.UsersColumns.PASSWORD + " TEXT  NULL,"
                + ItemsContract.UsersColumns.EMAIL + " TEXT NOT NULL,"
                + ItemsContract.UsersColumns.PHONE + " TEXT  NULL,"
                + ItemsContract.ItemsColumns.CREATEDON + " TEXT NULL "
                + ")" );

        db.execSQL("CREATE TABLE " + ItemsProvider.Tables.ITEMS + " ("
                + ItemsContract.ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsContract.ItemsColumns.CATEGORYID + " INTEGER,"
                + ItemsContract.ItemsColumns.TITLE + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.ESTIMATED_BID + " FLOAT  NULL,"
                + ItemsContract.ItemsColumns.SELLERID + " INTEGER NOT  NULL,"

                + ItemsContract.ItemsColumns.WINNERID + " INTEGER   NULL,"
                + ItemsContract.ItemsColumns.IMAGE + " TEXT  NULL,"

                + ItemsContract.ItemsColumns.CREATEDON + " TEXT NULL ,"
                // Set up the location column as a foreign key to location table.
                +" FOREIGN KEY (" +  ItemsContract.Items.CATEGORYID
                        + ") REFERENCES " +
                        ItemsProvider.Tables.CATEGORY + " (" + ItemsContract.CategoryColumns._ID + "),"+
                " FOREIGN KEY (" +  ItemsContract.Items.SELLERID
                + ") REFERENCES " +
                ItemsProvider.Tables.USERS + " (" + ItemsContract.UsersColumns._ID + "),"
                +" FOREIGN KEY (" +  ItemsContract.Items.WINNERID
                + ") REFERENCES " +
                ItemsProvider.Tables.USERS + " (" + ItemsContract.UsersColumns._ID + ")"
                +"); ") ;
        final String SQL_CREATE_BID_TABLE = "CREATE TABLE " + ItemsProvider.Tables.BIDS + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                ItemsContract.Bids._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +


                ItemsContract.Bids.BID + " FLOAT NOT  NULL, " +
                ItemsContract.Bids.ITEMID + " INTEGER NOT  NULL, " +
                ItemsContract.Bids.BIDDER_ID + " INTEGER NOT  NULL, " +
                ItemsContract.Bids.CREATEDON + " TEXT NULL ," +


                " FOREIGN KEY (" +  ItemsContract.Bids.ITEMID
                + ") REFERENCES " +
                ItemsProvider.Tables.ITEMS + " (" + ItemsContract.ItemsColumns._ID + "),"+
                " FOREIGN KEY (" +  ItemsContract.Bids.BIDDER_ID
                + ") REFERENCES " +
                ItemsProvider.Tables.USERS + " (" + ItemsContract.UsersColumns._ID + ")"
                +"); " ;

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + ItemsProvider.Tables.CATEGORY + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                ItemsContract.Category._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +


                ItemsContract.Category.NAME + " TEXT NOT  NULL, " +
                ItemsContract.Category.IMAGE + " INT NOT  NULL);";

        db.execSQL(SQL_CREATE_BID_TABLE);
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);

        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Watches',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Antique',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Vaults',"+ R.drawable.ic_watch_black_24dp+")");

        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Tech',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Jewellery',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Home Decor',"+ R.drawable.ic_watch_black_24dp+")");

        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Artfacts',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Vehicles',"+ R.drawable.ic_watch_black_24dp+")");
        db.execSQL("INSERT INTO "+ItemsProvider.Tables.CATEGORY+" ("+ItemsContract.Category.NAME+","+ItemsContract.Category.IMAGE+") values ('Live Auctions',"+ R.drawable.ic_watch_black_24dp+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemsProvider.Tables.BIDS);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsProvider.Tables.ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsProvider.Tables.CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsProvider.Tables.USERS);
        onCreate(db);
    }
}
