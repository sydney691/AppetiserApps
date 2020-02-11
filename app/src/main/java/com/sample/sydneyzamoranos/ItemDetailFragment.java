package com.sample.sydneyzamoranos;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.sydneyzamoranos.dummy.DummyContent;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String trackName = getArguments().get("trackName").toString();
        if (getArguments().containsKey(trackName)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(trackName);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);



        ((TextView) rootView.findViewById(R.id.trackName)).setText(getArguments().get("trackName").toString());
        ((TextView) rootView.findViewById(R.id.price)).setText(getArguments().get("price").toString());
        ((TextView) rootView.findViewById(R.id.genre)).setText(getArguments().get("genre").toString());
        ((TextView) rootView.findViewById(R.id.description)).setText((String) getArguments().get("description"));
         Picasso.get().load(getArguments().get("artWork").toString()).resize(500,500).placeholder(R.drawable.images).into((ImageView) rootView.findViewById(R.id.imageView));

        return rootView;
    }
}
