package app.com.product.auctionapp;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.product.auctionapp.data.CategoryLoader;
import app.com.product.auctionapp.data.ItemsContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BidCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BidCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BidCategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int BID_CATEGORY_LOADER=1;
    Interpolator interpolator;
    float offset;
private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BidCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BidCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BidCategoryFragment newInstance(String param1, String param2) {
        BidCategoryFragment fragment = new BidCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getLoaderManager().initLoader(BID_CATEGORY_LOADER,null,this);
        offset = getResources().getDimensionPixelSize(R.dimen.offset_y);
        interpolator =
                AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.linear_out_slow_in);


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_bid_category, container, false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return CategoryLoader.newInstance(getActivity());

    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Adapter adapter = new Adapter(cursor,getActivity());
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        int columnCount =3;
        GridLayoutManager sglm =
                new GridLayoutManager(getContext(),columnCount);
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Cursor mCursor;
        private final int[] COLORS = new int[] { 0xff956689, 0xff80678A, 0xff6A6788,
                0xff546683, 0xff3F657B, 0xff3F657B };
        public Adapter(Cursor cursor,Activity activity) {
            mCursor = cursor;
            host=activity;
            inflater = LayoutInflater.from(host);
        }

        @Override
        public long getItemId(int position) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(CategoryLoader.Query._ID);
        }
        private Activity host;
        private  LayoutInflater inflater;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =inflater.inflate(R.layout.category_item, parent, false);
            final ViewHolder vh = new ViewHolder(view);



            view.setVisibility(View.VISIBLE);
            view.setTranslationY(offset);
            view.setAlpha(0.85f);
            // then animate back to natural position
            view.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            // increase the offset distance for the next view
            offset *= 1.5f;
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder,final int position) {
            final int color = COLORS[position % COLORS.length];
            mCursor.moveToPosition(position);
            holder.titleView.setText(mCursor.getString(CategoryLoader.Query.NAME));
            holder.titleView.setContentDescription(mCursor.getString(CategoryLoader.Query.NAME));
holder.catId.setText(mCursor.getString(CategoryLoader.Query._ID));
//
//            holder.titleView.setTypeface(Config.typefaceMedium);
//            holder.subtitleView.setTypeface(Config.typefaceRegular);
            //   ViewHolder viewHolder = (ViewHolder) view.getTag();

            //String imgUrl = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
//
//            Uri uri = Uri.parse( mCursor.getString(CategoryLoader.Query.IMAGE));
//            Glide.with(ArticleListActivity.this).load(mCursor.getString(ArticleLoader.Query.THUMB_URL)).asBitmap()
//                    .listener(new RequestListener<String, Bitmap>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
//                    .into(holder.thumbnailView);



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //changeSize( view);

                    Intent intent =  new Intent(Intent.ACTION_VIEW,
                            ItemsContract.Category.buildCategoryUri(getItemId(holder.getAdapterPosition())));
                    BidsViewFragment
                            newFragment = BidsViewFragment.newInstance(holder.catId.getText().toString(),"");


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.add( newFragment,"BidsView");
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            ItemsContract.Items.buildItemUri(getItemId(holder.getAdapterPosition()))),bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCursor.getCount();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnailView;
        public TextView titleView;

        public TextView catId;
        public ViewGroup bottombar;


        public ViewHolder(View view) {
            super(view);
            thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            titleView = (TextView) view.findViewById(R.id.article_title);


            catId = (TextView) view.findViewById(R.id.catid);
            bottombar = (ViewGroup) view.findViewById(R.id.bottomBar);


        }

        @Override
        public void onClick(View view) {
        }

    }
    OnFragmentInteractionListener mCallback;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
