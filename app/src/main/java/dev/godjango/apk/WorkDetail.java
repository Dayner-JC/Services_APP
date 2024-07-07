package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/** */
public class WorkDetail extends AppCompatActivity implements PopUpDeleteWork.OnDeleteWorkListener {
    private boolean isExpanded = false;
    private View scrollView;
    private TextView textDescriptionContent;
    private int itemPosition;
    private TextView fieldDates,paymentDates,totalToPay,date,price,time;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_detail);
        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);

        initializeViews();
        setupListeners();
        populateViews();
    }

    private void initializeViews() {
        scrollView = findViewById(R.id.ScrollView);
        textDescriptionContent = findViewById(R.id.text_description_content);
        fieldDates = findViewById(R.id.Field_Dates);
        paymentDates = findViewById(R.id.Payment_Dates);
        totalToPay = findViewById(R.id.Total_To_Pay_Date);
        image = findViewById(R.id.Image);
        date = findViewById(R.id.text_date_time);
        price = findViewById(R.id.Price);
        time = findViewById(R.id.Time);
    }

    private void setupListeners() {
        findViewById(R.id.Work_button_back).setOnClickListener(this::onBackButtonClick);
        findViewById(R.id.text_read_more).setOnClickListener(this::onReadMoreClick);
        findViewById(R.id.Work_button_delete).setOnClickListener(this::onDeleteServiceClick);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void populateViews() {
        itemPosition = getIntent().getIntExtra("itemPosition", -1);
        int progress = getIntent().getIntExtra("Progress", 0);
        String title = getIntent().getStringExtra("Title");
        String category = getIntent().getStringExtra("Description");
        Bundle cardData = getIntent().getBundleExtra("CardData");

        if (cardData != null) {

            String name = cardData.getString("Name");
            String lastName = cardData.getString("Last Name");
            String email = cardData.getString("Email");
            String countryNumber = cardData.getString("Country Number");
            String phoneNumber = cardData.getString("Phone Number");
            String country = cardData.getString("Country");
            String amountPaid = cardData.getString("Amount Paid");
            String priceService = cardData.getString("Price Service");
            String timeService = cardData.getString("Time");
            int imageId = cardData.getInt("Image");

            fieldDates.setText(name + "\n" + lastName + "\n" + email + "\n" + "PayPal" + "\n" + countryNumber + " " + phoneNumber + "\n" + country);
            paymentDates.setText(amountPaid + "0$" + "\n" + priceService + "0$");

            if(priceService != null && amountPaid != null) {
                double total_to_pay = Double.parseDouble(priceService) - Double.parseDouble(amountPaid);
                totalToPay.setText(total_to_pay + "0$");
            }
            image.setImageResource(imageId);
            date.setText(cardData.getString("Request Date"));
            price.setText("$ " + priceService + "0");
            time.setText(timeService);
        }

        ((TextView) findViewById(R.id.Categoria)).setText(category);
        ((TextView) findViewById(R.id.Title)).setText(title);
        ((ProgressBar) findViewById(R.id.seekBar)).setProgress(progress);
        ((TextView) findViewById(R.id.progressText)).setText(String.format("%%%d", progress));
    }

    private void onDeleteServiceClick(View v) {
        showPopUp(itemPosition);
    }

    private void onReadMoreClick(View v) {
        TextView textReadMore = (TextView) v;
        isExpanded = !isExpanded;

        if (isExpanded) {
            expandDescription(textReadMore);
        } else {
            collapseDescription(textReadMore);
        }
        textDescriptionContent.requestLayout();
    }

    private void expandDescription(TextView textReadMore) {
        textDescriptionContent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        textReadMore.setText(R.string.read_less);
        scrollView.setVerticalScrollBarEnabled(true);
    }

    private void collapseDescription(TextView textReadMore) {
        textDescriptionContent.getLayoutParams().height = UnitConverter.dpToPx(this, 74);
        scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        textReadMore.setText(R.string.read_more);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.scrollTo(0, 0);
    }

    private void onBackButtonClick(View v) {
        finish();
    }

    private void showPopUp(int position) {
        PopUpDeleteWork popUp = PopUpDeleteWork.newInstance(position, this);
        popUp.setCancelable(false);
        popUp.show(getSupportFragmentManager(), "popup_window");
    }

    @Override
    public void onDeleteWork(int position) {
        Intent intent = new Intent();
        intent.putExtra("deletePosition", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}
