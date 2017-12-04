package com.example.arijit.github_mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.model.UserDetails;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiInterface;
import com.example.arijit.github_mobile.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView mName;
    private TextView mEmails;
    private TextView mFollowers;
    private TextView mFollowing;
    private TextView mLastUpdated;
    private ImageView mAvatar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
//        makeProfileDataRequest(container);
        View base = inflater.inflate(R.layout.fragment_profile, container, false);
//        makeProfileDataRequest();
        mName = base.findViewById(R.id.profile_name);
        mEmails = base.findViewById(R.id.profile_email);
        mFollowers = base.findViewById(R.id.profile_followers);
        mFollowing = base.findViewById(R.id.profile_following);
        mLastUpdated = base.findViewById(R.id.profile_last_updated);
        mAvatar = base.findViewById(R.id.profile_image);
        if (AppPreference.getInstance().getUser().getLogin() == null) {
            makeProfileDataRequest();
        } else {
            populateProfile(AppPreference.getInstance().getUser());
        }
        return base;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

//        makeProfileDataRequest();
    }

    private void makeProfileDataRequest() {

        if (!TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) ) {
            ApiInterface apiService =
                    Client.getClient().create(ApiInterface.class);
            Call<UserDetails> call = apiService.getUserDetails(AppPreference.getInstance().getAccessToken());
            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                    if (response != null ) {
                        AppPreference.getInstance().setUser(response.body());
                        populateProfile(response.body());
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                }
            });
        }
    }

    private void populateProfile(UserDetails response) {
        mName.setText(response.getLogin());
        mEmails.setText( mEmails.getText() + " - " + response.getEmail());
        mFollowers.setText( mFollowers.getText() + " - " + response.getFollowers());
        mFollowing.setText( mFollowing.getText() + " - " + response.getFollowing());
        mLastUpdated.setText( mLastUpdated.getText() + " - " + response.getUpdated_at());

        Glide.with(this).load(response.getAvatarUrl()).into(mAvatar);

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
