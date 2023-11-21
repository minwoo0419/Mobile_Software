package com.course.mobilesoftwareproject;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

public class InputPage extends AppCompatActivity {
    private String[] places = {"상록원", "기숙사 식당", "그루터기"};
    private String[] whens = {"아침", "점심", "저녁", "디저트"};
    private LinearLayout containerLayout;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private String selectedPlace;
    private String selectedWhen;
    private List<EditText> editTexts = new ArrayList<>();
    SQLiteDatabase sqlDB;
    DBHelper dbHelper = new DBHelper(this);
    Uri selectedImageUri;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);
        Spinner place = findViewById(R.id.place);
        Spinner when = findViewById(R.id.when);
        ArrayAdapter placeAdapter, whenAdapter;
        placeAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, places);
        whenAdapter = new ArrayAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, whens);
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

        ImageView imageView = findViewById(R.id.inputFoodImg);
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imageView.setImageURI(selectedImageUri);
                    }
                });
    }

    public void addNewEditText(View view) {
        editTexts.add(findViewById(R.id.foodName1));
        containerLayout = findViewById(R.id.inputFoodNameLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT /* layout_width */,
                                LinearLayout.LayoutParams.WRAP_CONTENT /* layout_height */,
                                1f /* layout_weight */);
        EditText editText = new EditText(this);
        editText.setHint("음식 이름을 입력해주세요.");
        editText.setLayoutParams(layoutParams);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.lato_regular);
        editText.setTypeface(customTypeface);
        containerLayout.addView(editText);
        editTexts.add(editText);
    }

    public void onImageViewClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
    public void backListener(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void storeSql(View view){
        ImageView image = findViewById(R.id.inputFoodImg);
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);
        EditText time = findViewById(R.id.editTextTime);
        EditText price = findViewById(R.id.price);
        EditText review = findViewById(R.id.review);
        String img = null;
        if (selectedImageUri != null)
            img = selectedImageUri.toString();
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO foodlist(place, image, type, review, date, time, price) VALUES('" +
                selectedPlace + "','" + img + "' , '" +
                selectedWhen + "' , '" +
                review.getText().toString() + "' , '" +
                date + "' , '" + time.getText().toString() + "', " +
                price.getText().toString() + ");"
        );
        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT last_insert_rowid()", null);
        String lastInsertedId = "-1"; // Default value if something goes wrong
        if (cursor != null && cursor.moveToFirst()) {
            lastInsertedId = cursor.toString();
            cursor.close();
        }
        sqlDB = dbHelper.getWritableDatabase();
        if (lastInsertedId.equals("-1")){
            for (int i = 0 ; i < editTexts.size() ; i++){
                String foodName = editTexts.get(i).getText().toString();
                readData(foodName);
                String cal = fo.getCal().toString();
                sqlDB.execSQL(
                        "INSERT INTO food(foodlist_id, name, calorie) VALUES('" +
                                lastInsertedId + "' ,'" +
                                foodName + "', " +
                                cal + ");"
                );
            }
        }
        sqlDB.close();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}