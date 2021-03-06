package com.example.arijit.github_mobile.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.adapter.RepoAdapter;
import com.example.arijit.github_mobile.model.UserRepoDetails;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiInterface;
import com.example.arijit.github_mobile.rest.Client;

import java.util.List;

import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mMenuList;
    private ProgressDialog mDialog;

    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RepoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepoFragment newInstance(String param1, String param2) {
        RepoFragment fragment = new RepoFragment();
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
        View base = inflater.inflate(R.layout.fragment_repo, container, false);
        mMenuList = base.findViewById(R.id.menuList);
        mDialog = new ProgressDialog(getActivity());
        makeRepoDetailsRequest();
        return base;
    }

    private void makeRepoDetailsRequest() {

        if (!TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) ) {
            if (mDialog != null) {
                mDialog.setTitle("Loading");
                mDialog.show();
            }
            ApiInterface apiService =
                    Client.getClient().create(ApiInterface.class);
            Call<List<UserRepoDetails>> call = apiService.getUserRepoDetails(AppPreference.getInstance().getAccessToken());
            call.enqueue(new Callback<List<UserRepoDetails>>() {

                @Override
                public void onResponse(Call<List<UserRepoDetails>> call, Response<List<UserRepoDetails>> response) {
                    List<UserRepoDetails> rs = response.body();
                    popuateView(rs);
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<UserRepoDetails>> call, Throwable t) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
        }
    }

    private void popuateView(List<UserRepoDetails> rs) {
        mMenuList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mMenuList.setAdapter(new RepoAdapter(getContext(), rs));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
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
