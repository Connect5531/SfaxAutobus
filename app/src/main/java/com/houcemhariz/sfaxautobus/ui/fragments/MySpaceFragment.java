package com.houcemhariz.sfaxautobus.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.houcemhariz.sfaxautobus.R;

import java.util.Arrays;
import java.util.concurrent.Executor;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MySpaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MySpaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySpaceFragment extends Fragment {

    private final int FACEBOOK_LOG_IN_REQUEST_CODE = 64206;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MySpaceFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MySpaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MySpaceFragment newInstance(String param1, String param2) {
        MySpaceFragment fragment = new MySpaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getContext());

        firebaseAuth =  FirebaseAuth.getInstance();
        Log.d("FIREBASEAUTH",firebaseAuth.toString());
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                String message = null;
                if (firebaseUser != null) {
                    message = "onAuthStateChanged signed in : "+firebaseUser.getDisplayName()+" -- "+firebaseUser.getPhoneNumber();
                }
                else {
                    message = "onAuthStateChanged signed out";
                }

                Log.d("ONAUTHSTATECHANGED",message);

            }
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my_space, container, false);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.facebook_login);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("ONSUCCESS","ONSUCCESS00");
                Toast.makeText(getContext(),loginResult.getAccessToken().getUserId(),Toast.LENGTH_LONG).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.d("ONSUCCESS","ONCANCEL");
                Toast.makeText(getContext(), R.string.cancel_login,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {

                Log.d("ONSUCCESS","ONERROR");
                Toast.makeText(getContext(), R.string.error_login,Toast.LENGTH_LONG).show();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
            }
        });
        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
            }
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Log.d("HANDLEFACEBOOK","HANDLEFACEBOOK");
                    Toast.makeText(getContext(),R.string.firebase_error_login,Toast.LENGTH_LONG).show();
                    LoginManager.getInstance().logOut();
                }

                else {
                    Log.d("HANDLEFACEBOOK","ELSE HANDLEFACEBOOK");
                    Toast.makeText(getContext(),"MRIGUEL",Toast.LENGTH_LONG).show();
                }

            }
        });


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
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fireAuthStateListener != null) {
            firebaseAuth.removeAuthStateListener(fireAuthStateListener);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FACEBOOK_LOG_IN_REQUEST_CODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
