package app.com.product.auctionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.product.auctionapp.data.BidsLoader;
import app.com.product.auctionapp.data.ItemsContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BidsViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BidsViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BidsViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int BIDS_LOADER=2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;

    public BidsViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BidsViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BidsViewFragment newInstance(String param1, String param2) {
        BidsViewFragment fragment = new BidsViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.fragment_bids_view, container, false);
        getLoaderManager().initLoader(BIDS_LOADER,null,this);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.bidsListView);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return BidsLoader.newInstance(getActivity(), Integer.parseInt(mParam1));

    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        BidsViewFragment.Adapter adapter = new BidsViewFragment.Adapter(cursor,getActivity());
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutManager sglm =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    private class Adapter extends RecyclerView.Adapter<BidsViewFragment.ViewHolder> {
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
            return mCursor.getLong(BidsLoader.Query._ID);
        }
        private Activity host;
        private  LayoutInflater inflater;
        @Override
        public BidsViewFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =inflater.inflate(R.layout.bid_item, parent, false);
            final BidsViewFragment.ViewHolder vh = new BidsViewFragment.ViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(final BidsViewFragment.ViewHolder holder, final int position) {
            final int color = COLORS[position % COLORS.length];
            mCursor.moveToPosition(position);
            holder.titleView.setText(mCursor.getString(BidsLoader.Query.BID));
            holder.titleView.setContentDescription(mCursor.getString(BidsLoader.Query.BID));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //changeSize( view);

                    Intent intent =  new Intent(Intent.ACTION_VIEW,
                            ItemsContract.Bids.buildItemUri(getItemId(holder.getAdapterPosition())));

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

        public ViewGroup bottombar;


        public ViewHolder(View view) {
            super(view);
            thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            titleView = (TextView) view.findViewById(R.id.article_title);
            bottombar = (ViewGroup) view.findViewById(R.id.bottomBar);


        }

        @Override
        public void onClick(View view) {
        }

    }
}
