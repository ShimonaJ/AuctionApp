package app.com.product.auctionapp.data;

import android.net.Uri;

public class ItemsContract {
	public static final String CONTENT_AUTHORITY = "com.product.auctionapp";
	public static final Uri BASE_URI = Uri.parse("content://com.product.auctionapp");

	interface UsersColumns {
		/** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
		String _ID = "_id";
		String NAME ="UserName";
		String PASSWORD = "Password";
		String EMAIL = "Email";
		String PHONE = "PhoneNo";
		String CREATEDON ="CreatedOn";
	}
	interface CategoryColumns{
		String _ID = "_id";
		String NAME ="CategoryName";
		String IMAGE ="Image";
	}
	interface ItemsColumns {
		/** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
		String _ID = "_id";
		String TITLE ="Title";
		String CATEGORYID = "CategoryId";
		String SELLERID = "SellerUserId";

		String WINNERID = "WinnerUserId";

		String ESTIMATED_BID = "EstimatedBid";
		String IMAGE = "Image";
		String CREATEDON ="CreatedOn";
	}
	interface BidsColumns {
		/** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
		String _ID = "_id";
		String ITEMID ="ItemId";
		String BID = "Bid";
		String BIDDER_ID = "BidderUserId";
		String CREATEDON ="CreatedOn";
	}
	public static class Users implements UsersColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.product.auctionapp.users";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.product.auctionapp.users";

		public static final String DEFAULT_SORT = NAME + " ASC";

		/** Matches: /items/ */
		public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath("users").build();
		}

		/** Matches: /items/[_id]/ */
		public static Uri buildItemUri(long _id) {
			return BASE_URI.buildUpon().appendPath("users").appendPath(Long.toString(_id)).build();
		}

		/** Read item ID item detail URI. */
		public static long getItemId(Uri itemUri) {
			return Long.parseLong(itemUri.getPathSegments().get(1));
		}
	}

	public static class Items implements ItemsColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.product.auctionapp.items";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.product.auctionapp.items";

        public static final String DEFAULT_SORT = CREATEDON + " DESC";

		/** Matches: /items/ */
		public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath("items").build();
		}

		/** Matches: /items/[_id]/ */
		public static Uri buildItemUri(long _id) {
			return BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(_id)).build();
		}
		/** Matches: /items/category/[_id]/ */
		public static Uri buildItemUriByCategory(long categoryId) {
			return BASE_URI.buildUpon().appendPath("items").appendPath("category").appendPath(Long.toString(categoryId)).build();
		}
        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
	}
	public static class Bids implements BidsColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.product.auctionapp.bids";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.product.auctionapp.bids";

		public static final String DEFAULT_SORT = CREATEDON + " DESC";

		/** Matches: /items/ */
		public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath("bids").build();
		}

		/** Matches: /items/[_id]/ */
		public static Uri buildItemUri(long _id) {
			return BASE_URI.buildUpon().appendPath("bids").appendPath(Long.toString(_id)).build();
		}

		/** Read item ID item detail URI. */
		public static long getItemId(Uri itemUri) {
			return Long.parseLong(itemUri.getPathSegments().get(1));
		}
	}
	public static class Category implements CategoryColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.product.auctionapp.category";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.product.auctionapp.category";

		public static final String DEFAULT_SORT = _ID + " ASC";

		/** Matches: /items/ */
		public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath("category").build();
		}

		/** Matches: /items/[_id]/ */
		public static Uri buildCategoryUri(long _id) {
			return BASE_URI.buildUpon().appendPath("category").appendPath(Long.toString(_id)).build();
		}

		/** Read item ID item detail URI. */
		public static long getItemIdFromUri(Uri itemUri) {
			return Long.parseLong(itemUri.getPathSegments().get(1));
		}
	}
	private ItemsContract() {
	}
}
