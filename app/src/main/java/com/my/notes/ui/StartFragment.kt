package com.my.notes.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.my.notes.R;


public class StartFragment extends Fragment{


    private com.google.android.gms.common.SignInButton buttonSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1502;
    private TextView emailView;
    private TextView nickname;
    private ImageView avatar;
    private MaterialButton buttonContinue;
    private MaterialButton buttonSignOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoogleSign();
        initView(view);
        enableSign();
    }

    private void initGoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void initView(View view) {
        emailView = view.findViewById(R.id.email);
        nickname = view.findViewById(R.id.nickname);
        avatar = view.findViewById(R.id.imageViewAvatar);

        buttonSignIn = view.findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(view1 -> signIn());

        buttonSignOut = view.findViewById(R.id.sign_out_button);
        buttonSignOut.setOnClickListener(view13 -> signOut());

        buttonContinue = view.findViewById(R.id.button_continue);
        buttonContinue.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,new RecycleListFragment());
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnSuccessListener(requireActivity(), unused -> {
            updateUI("","");
            setAvatar(null);
            enableSign();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            disableSign();
            updateUI(account.getDisplayName(),account.getEmail());
            setAvatar(account.getPhotoUrl());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(requireContext());
            disableSign();
            assert acct != null;
            updateUI(acct.getDisplayName(),acct.getEmail());
            setAvatar(acct.getPhotoUrl());
        } catch (ApiException e) {
            Log.d("LOG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(String nick, String email) {
        nickname.setText(nick);
        emailView.setText(email);
    }

    private void setAvatar(Uri personAvatar){
        Glide.with(requireContext()).load(String.valueOf(personAvatar)).into(avatar);
    }

    private void enableSign(){
        buttonSignIn.setEnabled(true);
        buttonContinue.setEnabled(false);
        buttonSignOut.setEnabled(false);

        buttonSignIn.animate().alpha(1).setDuration(1000);
        buttonSignOut.animate().alpha(0).setDuration(1000);
        buttonContinue.animate().alpha(0).setDuration(1000);
    }

    private void disableSign(){
        buttonSignIn.setEnabled(false);
        buttonContinue.setEnabled(true);
        buttonSignOut.setEnabled(true);
        buttonSignIn.animate().alpha(0).setDuration(1000);
        buttonSignOut.animate().alpha(1).setDuration(1000);
        buttonContinue.animate().alpha(1).setDuration(1000);
    }
}
