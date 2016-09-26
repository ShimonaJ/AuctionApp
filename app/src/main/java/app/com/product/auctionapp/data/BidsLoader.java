package app.com.product.auctionapp.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Helper for loading a list of articles or a single article.
 */
public class BidsLoader extends CursorLoader {
    public static BidsLoader newAllArticlesInstance(Context context) {
        return new BidsLoader(context, ItemsContract.Bids.buildDirUri());
    }

    public static BidsLoader newInstance(Context context) {
        return new BidsLoader(context, ItemsContract.Bids.buildDirUri());
    }

    private BidsLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, ItemsContract.Bids.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                ItemsContract.Bids._ID,
                ItemsContract.Bids.BIDDER_ID,
                ItemsContract.Bids.BID,
                ItemsContract.Bids.ITEMID,

        };

        int _ID = 0;
        int BIDDER_ID = 1;
        int BID = 2;
        int ITEMID = 3;

    }
}
