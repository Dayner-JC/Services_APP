package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** */
public class CardDetail extends AppCompatActivity {

    private boolean isExpanded = false;
    private ScrollView scrollView;
    private TextView textDescriptionContent, textReadMore;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);

        Intent intent = getIntent();
        int imageId = 0;
        String title = "";
        String category = "";
        String price = "";
        String time = "";
        if (intent != null) {
            title = intent.getStringExtra("Title");
            category = intent.getStringExtra("Category");
            price = intent.getStringExtra("Price");
            time = intent.getStringExtra("Time");
            imageId = intent.getIntExtra("Image", 0);
        }

        assert price != null;
        String priceWithOutSymbol = price.substring(1);
        String priceText = "$ " + priceWithOutSymbol;

        RecyclerView cards = findViewById(R.id.Cards_Container);
        TextView textTitle = findViewById(R.id.Title);
        TextView textCategory = findViewById(R.id.Categoria);
        TextView textPrice = findViewById(R.id.Precio);
        TextView textTime = findViewById(R.id.Tiempo);
        textDescriptionContent = findViewById(R.id.text_description_content);
        textReadMore = findViewById(R.id.text_read_more);
        scrollView = findViewById(R.id.ScrollView);
        ImageView topImage = findViewById(R.id.Image);
        ImageButton backButton = findViewById(R.id.Button_back);
        Button requestButton = findViewById(R.id.Button_request);

        List<CardReferencesData> date = new ArrayList<>();
        date.add(new CardReferencesData("Logo", imageId, true));
        date.add(new CardReferencesData("Manual", imageId, false));
        date.add(new CardReferencesData("Manual", imageId, false));
        CardReferencesAdapter adapter = new CardReferencesAdapter(this, date, category, price, time, imageId);
        cards.setAdapter(adapter);
        cards.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        Resources resources = getResources();
        int endOffset = resources.getDimensionPixelSize(R.dimen.end_offset);
        cards.addItemDecoration(new EndOffsetItemDecoration(endOffset));

        textTitle.setText(title);
        textCategory.setText(category);
        textPrice.setText(priceText);
        textTime.setText(time);
        topImage.setImageResource(imageId);
        scrollView.setVerticalScrollBarEnabled(false);
        backButton.setOnClickListener(this::onBackButtonClicked);
        textReadMore.setOnClickListener(this::onReadMoreClicked);

        requestButton.setOnClickListener(view ->{
            RegisterHelper registerHelper = new RegisterHelper(this, priceText);
            RegisterHelper.showRegister();
                });
    }

    private void onBackButtonClicked(View view) {
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void onReadMoreClicked(View view) {
        isExpanded = !isExpanded;
        if (isExpanded) {
            textDescriptionContent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textReadMore.setText("Read less...");
            scrollView.setVerticalScrollBarEnabled(true);
        } else {
            textDescriptionContent.getLayoutParams().height = UnitConverter.dpToPx(this, 74);
            scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textReadMore.setText("Read more...");
            scrollView.setVerticalScrollBarEnabled(false);
            scrollView.scrollTo(0, 0);
        }
        textDescriptionContent.requestLayout();
    }

}
