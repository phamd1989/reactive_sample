package com.univtop.univtop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;

import java.util.ArrayList;

/**
 * Created by duncapham on 8/9/15.
 */
public class BaseTrendingFeedFragment extends Fragment {
    private QuestionRecyclerAdapter adapter;
    private RecyclerView rvQuestions;

    public static BaseTrendingFeedFragment newInstance() {
        Bundle args = new Bundle();
        // put sth in the bundle
        // args.putInt(ARG_PAGE, page);
        BaseTrendingFeedFragment fragment = new BaseTrendingFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        adapter = new QuestionRecyclerAdapter(getActivity(), getFakeData());

//        check if the bundle has any fragments
//        if (args != null) {
//            mPage = getArguments().getInt(ARG_PAGE);
//        }
    }

    private ArrayList<Question> getFakeData() {
        ArrayList<Question> items = new ArrayList<>();
        items.add(new Question("Dany Targaryen", "Valyria"));
        items.add(new Question("Rob Stark", "Winterfell"));
        items.add(new Question("Jon Snow", "Castle Black"));
        items.add(new Question("Tyrion Lanister", "King's Landing"));
        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_page, container, false);
        rvQuestions = (RecyclerView) view.findViewById(R.id.rvQuestions);
        rvQuestions.setAdapter(adapter);
        rvQuestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
