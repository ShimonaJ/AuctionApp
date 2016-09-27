
package app.com.product.auctionapp.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.COMMENTS;

public class ItemsProvider extends ContentProvider {
	private SQLiteOpenHelper mOpenHelper;

	interface Tables {
		String USERS = "users";
		String ITEMS = "items";
		String BIDS = "bids";
		String CATEGORY = "categories";

	}

	private static final int ITEMS = 0;
	private static final int ITEMS__ID = 1;
	private static final int ITEMS_WHERE_CAT_ID = 2;
	private static final int ITEMS_WHERE_SELLER_ID = 3;


	private static final int USERS = 4;
	private static final int USERS__ID = 5;
	private static final int BIDS = 6;
	private static final int BIDS_ITEM_ID = 7;
	private static final int CATEGORY = 8;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = ItemsContract.CONTENT_AUTHORITY;
		matcher.addURI(authority, "users", USERS);
		matcher.addURI(authority, "items", ITEMS);
		matcher.addURI(authority, "bids", BIDS);
		matcher.addURI(authority, "category", CATEGORY);
		matcher.addURI(authority, "items/#", ITEMS__ID);
		matcher.addURI(authority, "items/category/#", ITEMS_WHERE_CAT_ID);
		matcher.addURI(authority, "items/user/#", ITEMS_WHERE_SELLER_ID);
		matcher.addURI(authority,"bids/item/#",BIDS);
		return matcher;
	}

	@Override
	public boolean onCreate() {
        mOpenHelper = new ItemsDatabase(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case ITEMS:
				return ItemsContract.Items.CONTENT_TYPE;
			case ITEMS__ID:
				return ItemsContract.Items.CONTENT_ITEM_TYPE;
			case ITEMS_WHERE_CAT_ID:
				return ItemsContract.Items.CONTENT_ITEM_TYPE;
			case ITEMS_WHERE_SELLER_ID:
				return ItemsContract.Items.CONTENT_ITEM_TYPE;
			case BIDS:
				return ItemsContract.Bids.CONTENT_TYPE;
			case BIDS_ITEM_ID:
				return ItemsContract.Bids.CONTENT_ITEM_TYPE;
			case CATEGORY:
				return ItemsContract.Category.CONTENT_TYPE;
			case USERS:
				return ItemsContract.Users.CONTENT_TYPE;
			case USERS__ID:
				return ItemsContract.Users.CONTENT_ITEM_TYPE;

			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		final SelectionBuilder builder = buildSelection(uri);
		Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case ITEMS: {
				final long _id = db.insertOrThrow(Tables.ITEMS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Items.buildItemUri(_id);
			}
			case ITEMS__ID: {
				final long _id = db.insertOrThrow(Tables.ITEMS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Items.buildItemUri(_id);
			}
			case USERS: {
				final long _id = db.insertOrThrow(Tables.USERS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Users.buildItemUri(_id);
			}
			case USERS__ID: {
				final long _id = db.insertOrThrow(Tables.USERS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Users.buildItemUri(_id);
			}
			case BIDS: {
				final long _id = db.insertOrThrow(Tables.BIDS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Bids.buildItemUri(_id);
			}

			case CATEGORY: {
				final long _id = db.insertOrThrow(Tables.CATEGORY, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ItemsContract.Category.buildCategoryUri(_id);
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
		return builder.where(selection, selectionArgs).update(db, values);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
		return builder.where(selection, selectionArgs).delete(db);
	}

	private SelectionBuilder buildSelection(Uri uri) {
		final SelectionBuilder builder = new SelectionBuilder();
		final int match = sUriMatcher.match(uri);
		return buildSelection(uri, match, builder);
	}

	private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
		final List<String> paths = uri.getPathSegments();
		switch (match) {
			case USERS: {
				return builder.table(Tables.USERS);
			}
			case ITEMS: {
				return builder.table(Tables.ITEMS);
			}
			case ITEMS__ID: {
				final String _id = paths.get(1);
				return builder.table(Tables.ITEMS).where(ItemsContract.Items._ID + "=?", _id);
			}
			case ITEMS_WHERE_CAT_ID: {
				final String _id = paths.get(2);
				return builder.table(Tables.ITEMS).where(ItemsContract.Items.CATEGORYID + "=?", _id);
			}
			case ITEMS_WHERE_SELLER_ID: {
				final String _id = paths.get(3);
				return builder.table(Tables.ITEMS).where(ItemsContract.Items.SELLERID + "=?", _id);
			}
			case CATEGORY: {

				return builder.table(Tables.CATEGORY);
			}
			case BIDS_ITEM_ID: {
				final String _id = paths.get(3);
				return builder.table(Tables.BIDS).where(ItemsContract.Bids.ITEMID + "=?", _id);
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
