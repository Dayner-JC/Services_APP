package com.example.prueba;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/** @noinspection deprecation, deprecation */
public class NavigationActivity extends AppCompatActivity implements ActionLayoutListener {
    private ImageButton buttonAll;
    private ImageButton buttonBack;
    private ImageButton buttonDelete;
    private onDeleteMessagesListener deleteListener;
    private ImageView imageView;
    private TextView topText;

    public void setOnDeleteMessagesListener(onDeleteMessagesListener listener) {
        this.deleteListener = listener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        buttonBack = findViewById(R.id.button_back);
        buttonAll = findViewById(R.id.button_all);
        buttonDelete = findViewById(R.id.button_delete);
        imageView = findViewById(R.id.logo);

        buttonBack.setVisibility(View.GONE);
        buttonAll.setVisibility(View.GONE);
        buttonDelete.setVisibility(View.GONE);

        buttonBack.setOnClickListener(this::onBackButtonClick);
        buttonAll.setOnClickListener(this::onAllButtonClick);
        buttonDelete.setOnClickListener(this::onDeleteButtonClick);

        topText = findViewById(R.id.fragment_title);

        setSystemBarsColor(R.color.status_bar);
        if (Build.VERSION.SDK_INT >= 0) {
            setTransparentNavigationBarWithDarkIcons();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            int itemId = menuItem.getItemId();
            if (itemId == R.id.navigation_home) {
                topText.setText("Home");
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_container, homeFragment, "HomeFragment");
                }
                transaction.show(homeFragment);

                // Ocultar otros fragmentos si están presentes
                hideFragment(transaction, "DashboardFragment");
                hideFragment(transaction, "NotificationsFragment");
                hideFragment(transaction, "ProfileFragment");

            } else if (itemId == R.id.navigation_dashboard) {
                topText.setText("Chat");
                Fragment dashboardFragment = getSupportFragmentManager().findFragmentByTag("DashboardFragment");
                if (dashboardFragment == null) {
                    dashboardFragment = new DashboardFragment();
                    setOnDeleteMessagesListener((DashboardFragment) dashboardFragment);
                    transaction.add(R.id.fragment_container, dashboardFragment, "DashboardFragment");
                }
                transaction.show(dashboardFragment);

                // Ocultar otros fragmentos si están presentes
                hideFragment(transaction, "HomeFragment");
                hideFragment(transaction, "NotificationsFragment");
                hideFragment(transaction, "ProfileFragment");

            } else if (itemId == R.id.navigation_notifications) {
                topText.setText("Legal Data");
                Fragment notificationsFragment = getSupportFragmentManager().findFragmentByTag("NotificationsFragment");
                if (notificationsFragment == null) {
                    notificationsFragment = new NotificationsFragment();
                    transaction.add(R.id.fragment_container, notificationsFragment, "NotificationsFragment");
                }
                transaction.show(notificationsFragment);

                // Ocultar otros fragmentos si están presentes
                hideFragment(transaction, "HomeFragment");
                hideFragment(transaction, "DashboardFragment");
                hideFragment(transaction, "ProfileFragment");

            } else if (itemId == R.id.navigation_profile) {
                topText.setText("Work");
                Fragment profileFragment = getSupportFragmentManager().findFragmentByTag("ProfileFragment");
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                    transaction.add(R.id.fragment_container, profileFragment, "ProfileFragment");
                }
                transaction.show(profileFragment);

                // Ocultar otros fragmentos si están presentes
                hideFragment(transaction, "HomeFragment");
                hideFragment(transaction, "DashboardFragment");
                hideFragment(transaction, "NotificationsFragment");
            }
            transaction.commit();
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // La actividad se hace visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        // La actividad obtiene el foco y es interactiva
    }

    @Override
    protected void onPause() {
        super.onPause();
        // La actividad pierde el foco, pero aún es visible
    }

    @Override
    protected void onStop() {
        super.onStop();
        // La actividad ya no es visible para el usuario
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // La actividad está siendo destruida
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar el estado de la actividad
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restaurar el estado de la actividad
    }

    private void hideFragment(FragmentTransaction transaction, String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.hide(fragment);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onBackButtonClick(View v) {
        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (dashboardFragment != null) {
            MessageAdapter adapter = dashboardFragment.getMessageAdapter();
            if (adapter != null) {
                adapter.deseleccionarTodos();
                adapter.notifyDataSetChanged();
            }
        }
        onActionLayoutDismissed();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onAllButtonClick(View v) {
        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (dashboardFragment != null) {
            MessageAdapter adapter = dashboardFragment.getMessageAdapter();
            if (adapter != null) {
                adapter.seleccionarTodos();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void onDeleteButtonClick(View v) {
        if (deleteListener != null) {
            deleteListener.onDeleteMessages();
        }
        onActionLayoutDismissed();
    }

    private void setSystemBarsColor(int colorResId) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.status_bar));
    }

    private void setTransparentNavigationBarWithDarkIcons() {
        Window window = getWindow();
        window.setNavigationBarColor(Color.TRANSPARENT);

        View decorView = window.getDecorView();
        int flags = decorView.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    public void onMessageSelected() {
        buttonBack.setVisibility(View.VISIBLE);
        buttonAll.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        topText.setVisibility(View.GONE);
    }

    @Override
    public void onActionLayoutDismissed() {
        buttonBack.setVisibility(View.GONE);
        buttonAll.setVisibility(View.GONE);
        buttonDelete.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        topText.setVisibility(View.VISIBLE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}