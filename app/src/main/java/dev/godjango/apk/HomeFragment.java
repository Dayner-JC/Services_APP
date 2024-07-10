package dev.godjango.apk;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements CardAdapter.OnItemClickListener {

    private ImageButton closeButton;
    private ImageView imageSearch;
    private PageIndicator pageIndicator;
    private TextView removeFocusTextView, Branding_Text, Services_Text, Interface_Text, Editorial_Text;
    private EditText searchEditText;
    private ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();
    private final Runnable sliderRunnable = () -> viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
    private CardAdapter brandingAdapter, servicesAdapter, interfacesAdapter, editorialAdapter;
    private List<CardServices> brandingServices, servicesServices, interfacesServices, editorialServices;
    private View brandingRecyclerView, servicesRecyclerView, interfacesRecyclerView, ToolBar, buttonRecyclerView, editorialRecyclerView;
    private AppBarLayout appBar;
    private ActivityResultLauncher<Intent> startForResult;
    List<CardServices> services;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle newCardData = data != null ? data.getBundleExtra("newCardData") : null;
                        if (newCardData != null) {
                            WorkViewModel workViewModel = new ViewModelProvider(requireActivity()).get(WorkViewModel.class);
                            workViewModel.newCardData.setValue(newCardData);
                        }
                    }
                }
        );

        brandingRecyclerView = view.findViewById(R.id.branding_recycler_view);
        servicesRecyclerView = view.findViewById(R.id.services_recycler_view);
        interfacesRecyclerView = view.findViewById(R.id.Interfaces_recycler_view);
        editorialRecyclerView = view.findViewById(R.id.Editorial_recycler_view);
        buttonRecyclerView = view.findViewById(R.id.button_recycler_view);
        Branding_Text = view.findViewById(R.id.branding_title);
        Services_Text = view.findViewById(R.id.Services_title);
        Interface_Text = view.findViewById(R.id.Interfaces_title);
        Editorial_Text = view.findViewById(R.id.Editorial_title);

        appBar = view.findViewById(R.id.app_bar);

        setupRecyclerView((RecyclerView) brandingRecyclerView);
        setupRecyclerView((RecyclerView) servicesRecyclerView);
        setupRecyclerView((RecyclerView) interfacesRecyclerView);
        setupRecyclerView((RecyclerView) editorialRecyclerView);
        setupRecyclerView((RecyclerView) buttonRecyclerView);

        services = getAllServices();
        brandingServices = filter(services, "Branding");
        servicesServices = filter(services, "Services");
        interfacesServices = filter(services, "Interface");
        editorialServices = filter(services, "Editorial");

        brandingAdapter = new CardAdapter(brandingServices, this);
        servicesAdapter = new CardAdapter(servicesServices, this);
        interfacesAdapter = new CardAdapter(interfacesServices, this);
        editorialAdapter = new CardAdapter(editorialServices, this);

        ((RecyclerView) brandingRecyclerView).setAdapter(brandingAdapter);
        ((RecyclerView) servicesRecyclerView).setAdapter(servicesAdapter);
        ((RecyclerView) interfacesRecyclerView).setAdapter(interfacesAdapter);
        ((RecyclerView) editorialRecyclerView).setAdapter(editorialAdapter);

        List<String> buttonLabels = Arrays.asList("All", "Branding", "Interface", "Services", "Editorial");
        ButtonAdapter buttonAdapter = new ButtonAdapter(buttonLabels, brandingAdapter, servicesAdapter, interfacesAdapter, (RecyclerView) brandingRecyclerView, (RecyclerView) servicesRecyclerView, (RecyclerView) interfacesRecyclerView, view, editorialAdapter, (RecyclerView) editorialRecyclerView);
        ((RecyclerView) buttonRecyclerView).setAdapter(buttonAdapter);

        setupViewPager(view);

        setupSearch(view);

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        int endOffset = getResources().getDimensionPixelSize(R.dimen.end_offset);
        recyclerView.addItemDecoration(new EndOffsetItemDecoration(endOffset));
    }

    private List<CardServices> getAllServices() {
        return new ArrayList<>(Arrays.asList(
                new CardServices(R.drawable.card_service_image, "Visual Identity", "Branding", "$10.00", "/Month",false),
                new CardServices(R.drawable.card_service_image, "Identity Manual", "Branding", "$80.00", "/Month",true),

                new CardServices(R.drawable.card_service_image, "Website Design", "Interface", "To Quote", "",false),
                new CardServices(R.drawable.card_service_image, "APK Design", "Interface", "To Quote", "",false),
                new CardServices(R.drawable.card_service_image, "Pre-designed Website", "Interface", "$10.00", "/Month",true),
                new CardServices(R.drawable.card_service_image, "Custom Website Development", "Interface", "To Quote", "",false),

                new CardServices(R.drawable.card_service_image, "Subscriber Service", "Services", "$40.00", "/Month",false),
                new CardServices(R.drawable.card_service_image, "VIP Quarks", "Services", "$15.00", "/Month",true),
                new CardServices(R.drawable.card_service_image, "Space in MercoCuba", "Services", "$20.00", "/Month",false),
                new CardServices(R.drawable.card_service_image, "Management Platform for Taxi", "Services", "$30.00", "/Month",false),

                new CardServices(R.drawable.card_service_image, "Creation of Product Catalog", "Editorial", "$5.00", "/Month",false),
                new CardServices(R.drawable.card_service_image, "Advertising Banner Design", "Editorial", "To Quote", "",false),
                new CardServices(R.drawable.card_service_image, "Promotional Posts", "Editorial", "$1.00", "/Month",true),
                new CardServices(R.drawable.card_service_image, "Digital Menu on the Internet", "Editorial", "$5.00", "/Month",false)

        ));

    }


    private List<CardServices> filter(List<CardServices> services, String category) {
        List<CardServices> filteredServices = new ArrayList<>();
        for (CardServices service : services) {
            if (service.getCategory().equalsIgnoreCase(category)) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }

    private void setupViewPager(View view) {
        viewPager2 = view.findViewById(R.id.viewPager);
        List<SliderItem> sliderItems = new ArrayList<>(Arrays.asList(
                new SliderItem(R.drawable.image1),
                new SliderItem(R.drawable.image2),
                new SliderItem(R.drawable.image3),
                new SliderItem(R.drawable.image4)
        ));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setCurrentItem(1, false);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1.0f - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        pageIndicator = view.findViewById(R.id.page_indicator);
        pageIndicator.setCount(sliderItems.size());

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
                int numItems = Objects.requireNonNull(viewPager2.getAdapter()).getItemCount();
                int adjustedPosition = position % numItems;
                pageIndicator.setSelectedPosition(adjustedPosition);
            }
        });

        ToolBar = view.findViewById(R.id.collapsing_toolbar);
    }

    private void setupSearch(View view) {
        searchEditText = view.findViewById(R.id.search);
        imageSearch = view.findViewById(R.id.imageSearch);
        closeButton = view.findViewById(R.id.closeButton);
        removeFocusTextView = view.findViewById(R.id.removeFocusTextView);
        TextView hintText = view.findViewById(R.id.hintText);

        closeButton.setVisibility(View.GONE);
        removeFocusTextView.setVisibility(View.GONE);
        removeFocusTextView.setEnabled(false);

        int maxMarginPixels = getResources().getDimensionPixelSize(R.dimen.max_margin);
        int originalMargin = ((ViewGroup.MarginLayoutParams) searchEditText.getLayoutParams()).rightMargin;

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> handleSearchFocusChange(originalMargin, maxMarginPixels, hintText, hasFocus));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                closeButton.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
                filteredCards(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        removeFocusTextView.setOnClickListener(v -> handleRemoveFocus(originalMargin));
    }

    private void filteredCards(String query) {
        List<CardServices> filteredBrandingServices = filterServices(brandingServices, query);
        List<CardServices> filteredServicesServices = filterServices(servicesServices, query);
        List<CardServices> filteredInterfacesServices = filterServices(interfacesServices, query);
        List<CardServices> filteredEditorialServices = filterServices(editorialServices, query);

        brandingAdapter.updateList(filteredBrandingServices);
        servicesAdapter.updateList(filteredServicesServices);
        interfacesAdapter.updateList(filteredInterfacesServices);
        editorialAdapter.updateList(filteredEditorialServices);

        boolean isSearching = !query.isEmpty();

        int visibility = isSearching ? View.GONE : View.VISIBLE;
        ToolBar.setVisibility(visibility);
        buttonRecyclerView.setVisibility(visibility);
        Branding_Text.setVisibility(visibility);
        Services_Text.setVisibility(visibility);
        Interface_Text.setVisibility(visibility);
        Editorial_Text.setVisibility(visibility);

        ViewGroup.LayoutParams layoutParams = appBar.getLayoutParams();
        if (isSearching) {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.new_app_bar_height);
        } else {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.app_bar_height);
        }
        appBar.setLayoutParams(layoutParams);

        brandingRecyclerView.setVisibility(filteredBrandingServices.isEmpty() ? View.GONE : View.VISIBLE);
        servicesRecyclerView.setVisibility(filteredServicesServices.isEmpty() ? View.GONE : View.VISIBLE);
        interfacesRecyclerView.setVisibility(filteredInterfacesServices.isEmpty() ? View.GONE : View.VISIBLE);
        editorialRecyclerView.setVisibility(filteredEditorialServices.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private List<CardServices> filterServices(List<CardServices> services, String query) {
        List<CardServices> filteredList = new ArrayList<>();
        for (CardServices service : services) {
            if (service.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    service.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                    service.getPrice().toLowerCase().contains(query.toLowerCase()) ||
                    service.getTime().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(service);
            }
        }
        return filteredList;
    }

    private void handleSearchFocusChange(int originalMargin, int maxMarginPixels, TextView hintText, boolean hasFocus) {
        float pixelsIn100dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 71.0f, getResources().getDisplayMetrics());
        int increasedMargin = hasFocus ? ((int) pixelsIn100dp) + originalMargin : originalMargin;
        int newMargin = Math.min(increasedMargin, maxMarginPixels);

        ValueAnimator animator = ValueAnimator.ofInt(((ViewGroup.MarginLayoutParams) searchEditText.getLayoutParams()).rightMargin, newMargin);
        animator.setDuration(150);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            int animatedValue = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) searchEditText.getLayoutParams();
            layoutParams.rightMargin = animatedValue;
            searchEditText.setLayoutParams(layoutParams);
        });
        animator.start();

        removeFocusTextView.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        removeFocusTextView.setEnabled(hasFocus);

        if (hasFocus) {
            hintText.setVisibility(View.GONE);
            imageSearch.setVisibility(View.GONE);
        } else {
            if (searchEditText.getText().toString().isEmpty()) {
                hintText.setVisibility(View.VISIBLE);
                imageSearch.setVisibility(View.VISIBLE);
            }
        }
        closeButton.setOnClickListener(v -> {
            searchEditText.setText("");
            if (!hasFocus) {
                hintText.setVisibility(View.VISIBLE);
                imageSearch.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleRemoveFocus(int originalMargin) {
        ValueAnimator animator = ValueAnimator.ofInt(((ViewGroup.MarginLayoutParams) searchEditText.getLayoutParams()).rightMargin, originalMargin);
        animator.setDuration(150L);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            int animatedValue = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) searchEditText.getLayoutParams();
            layoutParams.rightMargin = animatedValue;
            searchEditText.setLayoutParams(layoutParams);
        });
        animator.start();
        searchEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(CardServices cardServices) {
        Intent intent = new Intent(getContext(), CardDetail.class);
        intent.putExtra("Title", cardServices.getTitle());
        intent.putExtra("Category", cardServices.getCategory());
        intent.putExtra("Image", cardServices.getImage());
        intent.putExtra("Price", cardServices.getPrice());
        intent.putExtra("Time", cardServices.getTime());
        intent.putExtra("ClickedCard", cardServices);

        ArrayList<CardServices> sameCategoryCards = new ArrayList<>();
        for (CardServices card : services) {
            if (card.getCategory().equals(cardServices.getCategory())) {
                sameCategoryCards.add(card);
            }
        }
        intent.putParcelableArrayListExtra("CardsOfSameCategory", sameCategoryCards);

        startForResult.launch(intent);
    }

}
