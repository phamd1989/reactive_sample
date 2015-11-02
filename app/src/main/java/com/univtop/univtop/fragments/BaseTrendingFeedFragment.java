//package com.univtop.univtop.fragments;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.univtop.univtop.API;
//import com.univtop.univtop.R;
//import com.univtop.univtop.UnivtopApplication;
//import com.univtop.univtop.adapters.QuestionRecyclerAdapter;
//import com.univtop.univtop.models.Question;
//import com.univtop.univtop.utils.JsonUtil;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//import retrofit.mime.TypedByteArray;
//
///**
// * Created by duncapham on 8/9/15.
// */
//public class BaseTrendingFeedFragment extends Fragment {
//    private QuestionRecyclerAdapter adapter;
//    private RecyclerView rvQuestions;
//    private API api;
//
//    public static BaseTrendingFeedFragment newInstance() {
//        Bundle args = new Bundle();
//        // put sth in the bundle
//        // args.putInt(ARG_PAGE, page);
//        BaseTrendingFeedFragment fragment = new BaseTrendingFeedFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        api     = UnivtopApplication.getClient();
//        adapter = new QuestionRecyclerAdapter(getActivity());
//    }
//
//    private ArrayList<Question> getFakeData() {
//        ArrayList<Question> items = new ArrayList<>();
//        api.getTrendingFeedData(10, 0, new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//                String str = new String(((TypedByteArray) response.getBody()).getBytes());
//                Log.d(UnivtopApplication.TAG, str);
//                try {
//                    JSONObject obj = new JSONObject(str);
//                    parseData(obj);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(UnivtopApplication.TAG, error.getMessage());
//            }
//        });
//        return items;
//    }
//
//    private void parseData(JSONObject obj) {
//        try {
//            JSONArray array = JsonUtil.getJsonArray("objects", obj);
//            for (int i=0; i<array.length(); i++) {
//                JSONObject questionObj = array.getJSONObject(i);
//                // get list of tags
//                List<String> tags = new ArrayList<>();
//                JSONArray tagsArr = JsonUtil.getJsonArray("tags", questionObj);
//                for (int j=0; j<tagsArr.length(); j++) {
//                    JSONObject tagObj = tagsArr.getJSONObject(j);
//                    String tagName = JsonUtil.getString("value", tagObj);
//                    if (tagName != null) {
//                        tags.add(tagName);
//                    }
//                }
//                // get username
//                String username = JsonUtil.getString("username", JsonUtil.getJsonObject("user_post", questionObj));
//                // get qId
//                String qId      = JsonUtil.getString("unique_id", questionObj);
//                // get questionDesc
//                String questionDesc = JsonUtil.getString("title", questionObj);
//                // get profile image URL
//                String profilePicUrl = JsonUtil.getString("resource_uri", JsonUtil.getJsonObject("user_post", questionObj));
//
//                final Question question = new Question();
//
//                question.setProfilePicUrl(profilePicUrl);
//                question.setqId(qId);
//                question.setQuestionDesc(questionDesc);
//                question.setUsername(username);
//                question.setTags(tags);
//
//                adapter.addQuestion(question);
//                adapter.notifyDataSetChanged();
//            }
//        } catch (JSONException e) {
//            Log.e(UnivtopApplication.TAG, e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recycler_page, container, false);
//        rvQuestions = (RecyclerView) view.findViewById(R.id.rvQuestions);
//        rvQuestions.setAdapter(adapter);
//        rvQuestions.setLayoutManager(new LinearLayoutManager(getActivity()));
//        getFakeData();
//        return view;
//    }
//}
