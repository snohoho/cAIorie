package edu.floridapoly.mobiledeviceapps.fall23.caiorie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Meal.db";
    private static final String TABLE_NAME = "Meal_table";
    private static final String COL_1 = "DAY";
    public static final String COL_2 = "FOOD";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "MEAL";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + "day TEXT,"
                    + "food TEXT, "
                    + "calories INTEGER, "
                    + "meal INTEGER"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String food, int calories, String day, int mealNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, day);
        contentValues.put(COL_2, food);
        contentValues.put(COL_3, calories);
        contentValues.put(COL_4, mealNumber);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public List<String> getMondayFood(){
        ArrayList<String> foods = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM Meal_table WHERE DAY='Monday'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                String city = cursor.getString(cursor.getColumnIndexOrThrow("food"));

                String displayFood = city;
                foods.add(displayFood);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return foods;
    }
}
