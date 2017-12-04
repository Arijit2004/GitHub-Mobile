package com.example.arijit.github_mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.adapter.SearchRepoAdapter;
import com.example.arijit.github_mobile.model.SearchRepoDetails;
import com.example.arijit.github_mobile.model.SearchRepoItems;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiInterface;
import com.example.arijit.github_mobile.rest.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText mSearchText;
    private Button mSearchButton;
    private RecyclerView mMenuList;


    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        // Inflate the layout for this fragment\
        View base = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchButton = base.findViewById(R.id.search_button);
        mSearchText = base.findViewById(R.id.search_edittext);
        mMenuList = base.findViewById(R.id.menu_list_search);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mSearchText.getText())) {
                    makeRepoSearchRequest(mSearchText.getText().toString());
                }
            }
        });
        return base;
    }

    private void makeRepoSearchRequest(String text) {

        if (!TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) ) {
            ApiInterface apiService =
                    Client.getClient().create(ApiInterface.class);
            Call<SearchRepoItems> call = apiService.getSearchRepoDetails("stars","desc", text);
            call.enqueue(new Callback<SearchRepoItems>() {

                @Override
                public void onResponse(Call<SearchRepoItems> call, Response<SearchRepoItems> response) {
                    List<SearchRepoDetails> rs = response.body().getSearchlist();
                    populateView(rs);
                }

                @Override
                public void onFailure(Call<SearchRepoItems> call, Throwable t) {
                    Log.e("aro", "profile request failure " + t.toString());
                }
            });
        }
    }

    private void populateView(List<SearchRepoDetails> rs) {
        mMenuList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mMenuList.setAdapter(new SearchRepoAdapter(getContext(), rs));
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
}
