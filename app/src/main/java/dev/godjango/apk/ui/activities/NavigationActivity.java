package dev.godjango.apk.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.godjango.apk.ui.fragments.ContactFragment;
import dev.godjango.apk.ui.fragments.HomeFragment;
import dev.godjango.apk.ui.fragments.LegalDataFragment;
import dev.godjango.apk.R;
import dev.godjango.apk.ui.fragments.WorkFragment;
import dev.godjango.apk.adapters.MessageAdapter;
import dev.godjango.apk.listeners.ActionLayoutListener;
import dev.godjango.apk.listeners.onDeleteMessagesListener;
import dev.godjango.apk.utils.SystemBarsUtil;

public class NavigationActivity extends AppCompatActivity implements ActionLayoutListener, HomeFragment.ContactNavigationListener {
    private ImageButton buttonAll, buttonBack, buttonDelete;
    private ImageView imageView;
    private TextView topText;
    private onDeleteMessagesListener deleteListener;

    @Override
    public void navigateToContact() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_contact);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
            if (contactFragment != null) {
                contactFragment.sendAutoMessage();
            }
        }, 500);
    }

    public void setOnDeleteMessagesListener(onDeleteMessagesListener listener) {
        this.deleteListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);
        initializeViews();
        setupBottomNavigation();
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
    }

    private void initializeViews() {
        buttonBack = findViewById(R.id.button_back);
        buttonAll = findViewById(R.id.button_all);
        buttonDelete = findViewById(R.id.button_delete);
        imageView = findViewById(R.id.logo);
        topText = findViewById(R.id.fragment_title);

        View[] buttons = {buttonBack, buttonAll, buttonDelete};
        for (View button : buttons) {
            button.setVisibility(View.GONE);
        }

        buttonBack.setOnClickListener(this::onBackButtonClick);
        buttonAll.setOnClickListener(this::onAllButtonClick);
        buttonDelete.setOnClickListener(this::onDeleteButtonClick);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem.getItemId());
            return true;
        });
    }

    @SuppressLint("SetTextI18n")
    private void handleNavigationItemSelected(int itemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tag = getFragmentTagByItemId(itemId);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (itemId == R.id.navigation_home) {
            topText.setText("Home");
        } else if (itemId == R.id.navigation_contact) {
            topText.setText("Chat");
        } else if (itemId == R.id.navigation_legalData) {
            topText.setText("Legal Data");
        } else if (itemId == R.id.navigation_work) {
            topText.setText("Work");
        }

        if (fragment == null) {
            fragment = createFragmentByItemId(itemId);
            transaction.add(R.id.fragment_container, fragment, tag);
        } else {
            transaction.show(fragment);
        }

        hideOtherFragments(transaction, tag);
        transaction.commit();
    }

    @SuppressLint("SetTextI18n")
    private Fragment createFragmentByItemId(int itemId) {
        Fragment fragment;
        if (itemId == R.id.navigation_home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.navigation_contact) {
            ContactFragment contactFragment = new ContactFragment();
            setOnDeleteMessagesListener(contactFragment);
            fragment = contactFragment;
        } else if (itemId == R.id.navigation_legalData) {
            fragment = new LegalDataFragment();
        } else if (itemId == R.id.navigation_work) {
            fragment = new WorkFragment();
        } else {
            throw new IllegalArgumentException("Unknown navigation item ID");
        }
        return fragment;
    }

    private String getFragmentTagByItemId(int itemId) {
        if (itemId == R.id.navigation_home) {
            return "HomeFragment";
        } else if (itemId == R.id.navigation_contact) {
            return "ContactFragment";
        } else if (itemId == R.id.navigation_legalData) {
            return "LegalDataFragment";
        } else if (itemId == R.id.navigation_work) {
            return "WorkFragment";
        } else {
            throw new IllegalArgumentException("Unknown navigation item ID");
        }
    }

    private void hideOtherFragments(FragmentTransaction transaction, String visibleTag) {
        String[] allTags = {"HomeFragment", "ContactFragment", "LegalDataFragment", "WorkFragment"};
        for (String tag : allTags) {
            if (!tag.equals(visibleTag)) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment != null) {
                    transaction.hide(fragment);
                }
            }
        }
    }

    private void loadInitialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment(), "HomeFragment")
                .commit();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onBackButtonClick(View v) {
        handleActionButtonClick(() -> {
            ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (contactFragment != null) {
                MessageAdapter adapter = contactFragment.getMessageAdapter();
                if (adapter != null) {
                    adapter.unselectedAll();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        onActionLayoutDismissed();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onAllButtonClick(View v) {
        handleActionButtonClick(() -> {
            ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (contactFragment != null) {
                MessageAdapter adapter = contactFragment.getMessageAdapter();
                if (adapter != null) {
                    adapter.selectedAll();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void onDeleteButtonClick(View v) {
        handleActionButtonClick(() -> {
            if (deleteListener != null) {
                deleteListener.onDeleteMessages();
            }
        });
        onActionLayoutDismissed();
    }

    private void handleActionButtonClick(Runnable action) {
        action.run();
    }

    @Override
    public void onMessageSelected() {
        updateActionLayoutVisibility(View.VISIBLE);
    }

    @Override
    public void onActionLayoutDismissed() {
        updateActionLayoutVisibility(View.GONE);
    }

    private void updateActionLayoutVisibility(int visibility) {
        buttonBack.setVisibility(visibility);
        buttonAll.setVisibility(visibility);
        buttonDelete.setVisibility(visibility);
        imageView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
        topText.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
