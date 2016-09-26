package app.com.product.auctionapp.data;

import android.content.Context;

import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Helper for loading a list of articles or a single article.
 */
public class CategoryLoader extends CursorLoader {
    public static CategoryLoader newAllArticlesInstance(Context context) {
        return new CategoryLoader(context, ItemsContract.Items.buildDirUri());
    }

    public static CategoryLoader newInstance(Context context) {
        return new CategoryLoader(context, ItemsContract.Category.buildDirUri());
    }

    private CategoryLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, ItemsContract.Category.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                ItemsContract.Category._ID,
                ItemsContract.Category.NAME,
                ItemsContract.Category.IMAGE

        };

        int _ID = 0;
        int NAME = 1;
        int IMAGE = 2;

    }
}
