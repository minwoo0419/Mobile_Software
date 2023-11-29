package com.course.mobilesoftwareproject;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class Food{
    private String name;
    private Double cal;
    public void setName(String name){
        this.name = name;
    }
    public void setCal(Double cal){
        this.cal = cal;
    }
    public String getName(){
        return this.name;
    }
    public Double getCal(){
        return this.cal;
    }
}

public class InputFragment extends Fragment {
    private String[] places = {"상록원", "기숙사 식당", "그루터기"};
    private String[] whens = {"아침", "점심", "저녁", "디저트"};
    private LinearLayout containerLayout;
    private String selectedPlace;
    private String selectedWhen;
    private List<EditText> editTexts = new ArrayList<>();
    private TextView dateText, timeText;
    EditText price, review;
    ImageView imageView;
    SQLiteDatabase sqlDB;
    DBHelper dbHelper = new DBHelper(this.getContext());
    Uri selectedImageUri;
    byte[] img;
    Food fo = new Food();
    private void readData(String name){
        InputStream is = getResources().openRawResource(R.raw.kcal);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line;
        try{
            reader.readLine();

            while ((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                if (tokens[0].equals(name)){
                    fo.setName(tokens[0]);
                    fo.setCal(Double.parseDouble(tokens[1]));
                }
            }

        } catch (IOException e) {
            Log.d("MyActivity", "Error reading data file on line");
            e.printStackTrace();
        }
    }
    private void updateDateAndTime() {
        // Set current date
        Calendar currentDate = Calendar.getInstance();
        String currentDateStr = currentDate.get(Calendar.YEAR) + "-"
                + (currentDate.get(Calendar.MONTH) + 1) + "-"
                + currentDate.get(Calendar.DAY_OF_MONTH);
        dateText.setText(currentDateStr);

        // Set current time
        String currentTimeStr = String.format("%02d:%02d",
                currentDate.get(Calendar.HOUR_OF_DAY),
                currentDate.get(Calendar.MINUTE));
        timeText.setText(currentTimeStr);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        Spinner place = view.findViewById(R.id.place);
        Spinner when = view.findViewById(R.id.when);
        containerLayout = view.findViewById(R.id.inputFoodNameLayout);
        ArrayAdapter placeAdapter, whenAdapter;
        placeAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, places);
        whenAdapter = new ArrayAdapter(getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, whens);
        place.setAdapter(placeAdapter);
        when.setAdapter(whenAdapter);
        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPlace = places[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        when.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedWhen = whens[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        editTexts.add(view.findViewById(R.id.foodName1));

        imageView = view.findViewById(R.id.inputFoodImg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageViewClick(view);
            }
        });
        ImageView addbtn = view.findViewById(R.id.foodNameAdd);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEditText(view);
            }
        });
        Button button = view.findViewById(R.id.inputBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSql(view);
            }
        });
        dateText = view.findViewById(R.id.editDateText);
        timeText = view.findViewById(R.id.editTimeText);
        updateDateAndTime();
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        price = view.findViewById(R.id.price);
        review = view.findViewById(R.id.review);
        return view;
    }
    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog with a custom theme
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireActivity(),
                R.style.DialogDatePicker_Theme, // Set your custom theme here
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the TextView with the selected date
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dateText.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a TimePickerDialog with a custom theme
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireActivity(),
                R.style.DialogDatePicker_Theme, // Set your custom theme here
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the TextView with the selected time
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        timeText.setText(selectedTime);
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }



    @SuppressLint("ResourceAsColor")
    public void addNewEditText(View view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT /* layout_width */,
                        LinearLayout.LayoutParams.WRAP_CONTENT /* layout_height */,
                        1f /* layout_weight */);
        EditText editText = new EditText(getContext());
        editText.setHint("음식 이름을 입력해주세요.");
        editText.setLayoutParams(layoutParams);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        Typeface customTypeface = ResourcesCompat.getFont(getContext(), R.font.lato_regular);
        editText.setTypeface(customTypeface);
        editText.setTextColor(R.color.black);
        containerLayout.addView(editText);
        editTexts.add(editText);
    }

    public void onImageViewClick(View view) {
        Intent intent = new Intent();
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }
    @SuppressLint("Range")
    public void storeSql(View view){
        Log.d("storeSql", dateText.getText().toString());
        ContentValues foodListValues = new ContentValues();
        foodListValues.put(MyContentProvider.DATE, dateText.getText().toString());
        foodListValues.put(MyContentProvider.PLACE, selectedPlace);
        foodListValues.put(MyContentProvider.TYPE, selectedWhen);
        foodListValues.put(MyContentProvider.REVIEW, review.getText().toString());
        foodListValues.put(MyContentProvider.TIME, timeText.getText().toString());
        foodListValues.put(MyContentProvider.PRICE, price.getText().toString());
        foodListValues.put(MyContentProvider.IMAGE_URI,img);
        Uri insertedItemUri = getContext().getContentResolver().insert(MyContentProvider.CONTENT_URI, foodListValues);
        String lastInsertedId = null;
        if (insertedItemUri != null) {
            lastInsertedId = insertedItemUri.getLastPathSegment();
            Log.d("lastInsertedId", lastInsertedId);
        }
        if (!lastInsertedId.equals("-1")){
            for (int i = 0 ; i < editTexts.size() ; i++){
                String foodName = editTexts.get(i).getText().toString();
                readData(foodName);
                Double c = fo.getCal();
                String cal = "0";
                if (c != null)
                    cal = c.toString();
                ContentValues foodValues = new ContentValues();
                foodValues.put(FoodProvider.FOOD_LIST_ID, lastInsertedId);
                foodValues.put(FoodProvider.NAME, foodName);
                foodValues.put(FoodProvider.CALORIE, cal);
                getContext().getContentResolver().insert(FoodProvider.CONTENT_URI, foodValues);
            }
        }
        Toast.makeText(getContext(), "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        selectedImageUri = uri;
                        imageView.setImageURI(selectedImageUri);
                        try{
                            ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), selectedImageUri);
                            Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                            img = getBytesFromBitmap(bitmap);
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}