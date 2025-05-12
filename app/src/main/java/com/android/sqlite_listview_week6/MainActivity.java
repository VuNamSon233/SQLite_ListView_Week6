package com.android.sqlite_listview_week6;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView;
import com.android.sqlite_listview_week6.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int[] images = {R.drawable.ic_student_male, R.drawable.ic_student_male, R.drawable.ic_student_male};
    String[] student_name = {"VŨ NAM SƠN", "NGUYỄN VĂN A", "NGUYỄN VĂN B"};
    String[] student_id = {"21200203", "21200XXX", "21207XXX"};

    String[] email = {"abc@email.com", "def@email.com", "ghi@email.com"};
    String[] city = {"Ho Chi Minh", "Hanoi", "Da Nang"};
    String[] school = {"HCMUS", "HCMUS", "HCMUS"};
    ListView lView;
    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getAllStudents();
        if(cursor.getCount() == 0) {
            for(int i = 0; i < student_name.length; i++) {
                db.insertStudent(
                        student_name[i],
                        student_id[i],
                        email[i],
                        city[i],
                        school[i],
                        images[i]
                );
            }
            cursor.close();
            cursor = db.getAllStudents(); // refresh to get newly inserted students
        }
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Integer> avatars = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                ids.add(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                avatars.add(cursor.getInt(cursor.getColumnIndexOrThrow("avatar")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] studentNames = names.toArray(new String[0]);
        String[] studentIds = ids.toArray(new String[0]);
        int[] avatarImages = new int[avatars.size()];
        for (int i = 0; i < avatars.size(); i++) {
            avatarImages[i] = avatars.get(i);
        }
        lView = (ListView) findViewById(R.id.androidList);
        lAdapter = new ListAdapter(MainActivity.this, studentNames, studentIds, avatarImages);
        lView.setAdapter(lAdapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, studentNames[i]+" "+studentIds[i], Toast.LENGTH_SHORT).show();
                Cursor studentCursor = db.getStudentById(studentIds[i]);
                if (studentCursor.moveToFirst()) {
                    String name = studentCursor.getString(studentCursor.getColumnIndexOrThrow("name"));
                    String id = studentCursor.getString(studentCursor.getColumnIndexOrThrow("id"));
                    String emailText = studentCursor.getString(studentCursor.getColumnIndexOrThrow("email"));
                    String cityText = studentCursor.getString(studentCursor.getColumnIndexOrThrow("city"));
                    String schoolText = studentCursor.getString(studentCursor.getColumnIndexOrThrow("school"));
                    int avatarRes = studentCursor.getInt(studentCursor.getColumnIndexOrThrow("avatar"));

                    View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
                    ((ImageView) dialogView.findViewById(R.id.imageView)).setImageResource(avatarRes);
                    ((TextView) dialogView.findViewById(R.id.student_name_dialog)).setText("Họ và tên: " + name);
                    ((TextView) dialogView.findViewById(R.id.student_id_dialog)).setText("MSSV: " + id);
                    ((TextView) dialogView.findViewById(R.id.email_dialog)).setText("Email: " + emailText);
                    ((TextView) dialogView.findViewById(R.id.city_dialog)).setText("Địa chỉ: " + cityText);
                    ((TextView) dialogView.findViewById(R.id.school_dialog)).setText("Trường: " + schoolText);

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Student Information")
                            .setView(dialogView)
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }

}