package com.example.vegantranslations.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.FirestoreRepository;
import com.example.vegantranslations.viewModel.MainViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    private MainViewModel mainViewModel;
    private SubmitButton login, continueAsGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to populate the local database
        new FirestoreRepository(getApplicationContext());

        login = findViewById(R.id.login);
        continueAsGuest = findViewById(R.id.continue_as_guest);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        login.setOnClickListener(v -> {
            List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        });

        continueAsGuest.setOnClickListener(v -> startActivityForResult(new Intent(this, SearchAsGuestActivity.class), 1));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                continueAsGuest.reset();
            }
        }

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                Toast.makeText(getApplicationContext(), "Authentication is successful. Welcome administrator with email " + user.getEmail() + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AdministratorViewActivity.class);
                startActivity(intent);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                login.reset();
            } else {
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                login.reset();
            }
        }
    }
}
