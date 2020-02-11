package com.sample.sydneyzamoranos;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sample.sydneyzamoranos.dummy.DummyContent;
import com.sample.sydneyzamoranos.pojo.Results;
import com.sample.sydneyzamoranos.pojo.SongInfo;
import com.sample.sydneyzamoranos.presenter.CallBackResponse;
import com.sample.sydneyzamoranos.presenter.GetSongInfoImpl;
import com.sample.sydneyzamoranos.view.ItunesSongsView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ItunesSongsView {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.item_list)
    View recyclerView;

    private static LruCache cache = new LruCache(5000);
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private SimpleItemRecyclerViewAdapter adapter;
    private List<Results> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ActivityManager am = (ActivityManager) this.getSystemService(
                Context.ACTIVITY_SERVICE);
        pref = getApplicationContext().getSharedPreferences("preference", 0); // 0 - for private mode
        editor = pref.edit();
        if (pref.getString("data", "") == null || pref.getString("data", "").equals("")) {
            results = new ArrayList<>();

        } else {
            String json = pref.getString("data", "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Results>>() {
            }.getType();
            List<Results> obj = gson.fromJson(json, type);
            results = obj;

        }

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        GetSongInfoImpl getSongInfoImpl = new GetSongInfoImpl(this);
        adapter = new SimpleItemRecyclerViewAdapter(this, results, mTwoPane);
        toolbar.setTitle(getTitle());

        if (results.size() <= 1) {
            Results results1 = new Results();
            results1.setArtistId("1");
            results1.setArtistName("sydney");
            results.add(results1);
            getSongInfoImpl.getAllSongs(results, new CallBackResponse() {
                public void completed(boolean done) {
                    if (done) {
                        ItemListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Gson gson = new Gson();
                                String json = gson.toJson(results);
                                editor.putString("data", json);
                                editor.commit();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                }
            });
        } else {
            setupRecyclerView((RecyclerView) recyclerView);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getSongInfoImpl.processUi();
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<Results> mValues;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Results item = (Results) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getArtistId());
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra("trackName", item.getTrackName());
                    intent.putExtra("artWork", item.getArtworkUrl100());
                    intent.putExtra("price", item.getTrackPrice());
                    intent.putExtra("description", item.getLongDescription());
                    intent.putExtra("genre", item.getPrimaryGenreName());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<Results> results,
                                      boolean twoPane) {
            mValues = results;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getArtistName());
            holder.mContentView.setText("₱" + mValues.get(position).getTrackPrice());
            Picasso.get().load(mValues.get(position).getArtworkUrl100()).resize(500,500).placeholder(R.drawable.images).into(holder.imageView);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final ImageView imageView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                imageView = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }

    @Override
    public void processSong(boolean done, CallBackResponse callBackResponse) {
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    public void setSongsToUI(SongInfo songInfo) {

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
    }


}
