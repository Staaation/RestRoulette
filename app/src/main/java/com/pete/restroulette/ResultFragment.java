package com.pete.restroulette;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Pete on 4/9/2018.
 */

public class ResultFragment extends Fragment {

    private RecyclerView mRestRecyclerView;
    private List<SearchResults> mItems = new ArrayList<>();
    private SearchResults mSearchResults;

    public static ResultFragment newInstance(){

        return new ResultFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mSearchResults = new SearchResults();

        updateItems();

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_results, container, false);
        mRestRecyclerView = (RecyclerView) v.findViewById(R.id.search_recycler_view);
        mRestRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return v;
    }
    public void updateItems(){
        new FetchItemsTask().execute();
    }
    public void setupAdapter(){
        if(isAdded()) {
            mRestRecyclerView.setAdapter(new RestAdapter(mItems));
        }
    }
    public class RestHolder extends RecyclerView.ViewHolder {
        private SearchResults mSearchResultsItem;
        private TextView mTitleTextView;

        public RestHolder(View itemView) {
            super(itemView);

        }

        public void bindSearchItem(SearchResults searchItem) {
            mSearchResultsItem = searchItem;
        }
    }

    public class FetchItemsTask extends AsyncTask<Void, Void, List<SearchResults>> {

        @Override
        protected List<SearchResults> doInBackground(Void... params) {
            return new GooglePlaces().search();
        }

        @Override
        protected void onPostExecute(List<SearchResults> items) {
            mItems = items;
            double r = Math.random()*100;
            int rand = (int) r;
            Collections.shuffle(mItems, new Random(rand));
            setupAdapter();
        }
    }

    public class RestAdapter extends RecyclerView.Adapter<RestHolder> {
        private List<SearchResults> mSearchResultsItem;
        private TextView mRestTitle;

        public RestAdapter(List<SearchResults> searchItems) {
            mSearchResultsItem = searchItems;
        }

        @Override
        public RestHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_rest, viewGroup, false);
            mRestTitle = (TextView) view.findViewById(R.id.rest_title);
            return new RestHolder(view);
        }

        @Override
        public void onBindViewHolder(RestHolder restHolder, int position) {
            SearchResults searchItem = mSearchResultsItem.get(position);
            restHolder.bindSearchItem(searchItem);
            mRestTitle.setText(searchItem.getName());
        }

        @Override
        public int getItemCount() {
            return mSearchResultsItem.size();
        }
    }








}
