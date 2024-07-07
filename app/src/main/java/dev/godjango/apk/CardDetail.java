// CardDetail.java

package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        int imageId;
        String title;
        String category;
        String price;
        String time;

        if (intent != null) {
            title = intent.getStringExtra("Title");
            category = intent.getStringExtra("Category");
            price = intent.getStringExtra("Price");
            time = intent.getStringExtra("Time");
            imageId = intent.getIntExtra("Image", 0);

        } else {
            imageId = 0;
            time = "";
            price = "";
            category = "";
            title = "";

        }

        List<CardServices> originalCardsList = intent.getParcelableArrayListExtra("CardsOfSameCategory");

        if (originalCardsList == null) {
            originalCardsList = new ArrayList<>();
        }

        List<CardServices> filteredCards = new ArrayList<>(originalCardsList);
        CardServices clickedCard = intent.getParcelableExtra("ClickedCard");
        if (clickedCard != null) {
            filteredCards.removeIf(c -> c.getTitle().equals(clickedCard.getTitle()));
        }

        CardReferencesAdapter adapter = new CardReferencesAdapter(this, filteredCards, originalCardsList);
        RecyclerView cardsRecyclerView = findViewById(R.id.Cards_Container);
        cardsRecyclerView.setAdapter(adapter);

        String priceWithOutSymbol;
        String priceText;
        if(price != null) {
            priceWithOutSymbol = price.substring(1);
            priceText = "$ " + priceWithOutSymbol;
        } else {
            priceText = "";
        }


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

        cardsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        Resources resources = getResources();
        int endOffset = resources.getDimensionPixelSize(R.dimen.end_offset);
        cardsRecyclerView.addItemDecoration(new EndOffsetItemDecoration(endOffset));

        textTitle.setText(title);
        textCategory.setText(category);
        textPrice.setText(priceText);
        textTime.setText(time);
        topImage.setImageResource(imageId);
        scrollView.setVerticalScrollBarEnabled(false);
        backButton.setOnClickListener(this::onBackButtonClicked);
        textReadMore.setOnClickListener(this::onReadMoreClicked);

        requestButton.setOnClickListener(view ->{
            Bundle cardData = new Bundle();
            cardData.putString("Title", title);
            cardData.putString("Category", category);
            cardData.putString("Price", price);
            cardData.putString("Time", time);
            cardData.putInt("Image", imageId);

            RegisterHelper registerHelper = new RegisterHelper(cardData,this, priceText);
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
