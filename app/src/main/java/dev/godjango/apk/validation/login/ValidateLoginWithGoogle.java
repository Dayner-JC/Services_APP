package dev.godjango.apk.validation.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.Nullable;

import dev.godjango.apk.R;
import dev.godjango.apk.ui.activities.LoginActivity;
import dev.godjango.apk.ui.activities.NavigationActivity;

public class ValidateLoginWithGoogle {

    private static final int RC_SIGN_IN = 123;
    private final GoogleSignInClient googleSignInClient;
    private final LoginActivity loginActivity;

    public ValidateLoginWithGoogle(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(loginActivity.getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(loginActivity, gso);
    }

    public void onGoogleButtonClicked() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        loginActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleActivityResult(int requestCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(loginActivity, "Unknown error authenticating with Firebase", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(loginActivity, task -> {
                    if (task.isSuccessful()) {
                        handleGoogleSignInSuccess();
                    } else {
                        Toast.makeText(loginActivity, "Unknown error authenticating with Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleGoogleSignInSuccess() {
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", true);
        editor.apply();

        Intent intent = new Intent(loginActivity, NavigationActivity.class);
        loginActivity.startActivity(intent);
        loginActivity.finish();
    }
}
