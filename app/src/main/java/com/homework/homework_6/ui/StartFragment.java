package com.homework.homework_6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.homework.homework_6.R;


public class StartFragment extends Fragment{
    RecycleListFragment recycleListFragment;



    private com.google.android.gms.common.SignInButton buttonSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1502;
    private TextView emailView;
    private MaterialButton buttonContinue;

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
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void initView(View view) {
        emailView = view.findViewById(R.id.email);

        buttonSignIn = view.findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(view1 -> signIn());

        buttonContinue = view.findViewById(R.id.button_continue);
        buttonContinue.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,recycleListFragment);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            // Пользователь уже входил, сделаем кнопку недоступной
            disableSign();
            // Обновим почтовый адрес этого пользователя и выведем его на экран
            updateUI(account.getEmail());
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
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            disableSign();
            updateUI(account.getEmail());
        } catch (ApiException e) {
            Log.d("LOG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(String email) {
        emailView.setText(email);
    }

    // Разрешить аутентификацию и запретить остальные действия
    private void enableSign(){
        buttonSignIn.setEnabled(true);
        buttonContinue.setEnabled(false);
    }

    // Запретить аутентификацию (уже прошла) и разрешить остальные действия
    private void disableSign(){
        buttonSignIn.setEnabled(false);
        buttonContinue.setEnabled(true);
    }


}
