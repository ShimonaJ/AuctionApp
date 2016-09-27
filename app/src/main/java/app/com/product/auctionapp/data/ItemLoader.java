package app.com.product.auctionapp.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.net.Uri;

/**
 * Helper for loading a list of articles or a single article.
 */
public class ItemLoader extends CursorLoader {
    public static ItemLoader newAllArticlesInstance(Context context) {
        return new ItemLoader(context, ItemsContract.Items.buildDirUri());
    }

    public static ItemLoader newInstanceForItemId(Context context, long itemId) {
        return new ItemLoader(context, ItemsContract.Items.buildItemUri(itemId));
    }
    public static ItemLoader newInstanceForCategoryId(Context context, long categoryId) {
        return new ItemLoader(context, ItemsContract.Items.buildItemUriByCategory(categoryId));
    }
    private ItemLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, ItemsContract.Items.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                ItemsContract.Items._ID,
                ItemsContract.Items.SELLERID,
                ItemsContract.Items.WINNERID,
                ItemsContract.Items.ESTIMATED_BID,
                ItemsContract.Items.CREATEDON,
                ItemsContract.Items.TITLE,
                ItemsContract.Items.IMAGE,

        };

        int _ID = 0;
        int SELLERID = 1;
        int WINNERID = 2;
        int ESTIMATED_BID = 3;
        int CREATEDON = 4;
        int TITLE = 5;
        int IMAGE = 6;

    }
}
