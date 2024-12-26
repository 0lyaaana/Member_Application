package nayeon.memberapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import nayeon.memberapplication.helper.DatabaseHelper;

public class UserlistActivity extends AppCompatActivity {

    // 변수 선언
    private ListView listview;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // view 초기화
        listview = findViewById(R.id.listView);

        // 데이터베이스 헬퍼 초기화
        databaseHelper = new DatabaseHelper(this);

        // 회원 목록 가져오기
        List<String> userlist = getUsersFromDatabase();

        // listview에 회원 목록 표시
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userlist);
        listview.setAdapter(adapter);

    }

    // 데이터베이스에서 회원 목록 가져오기
    private List<String> getUsersFromDatabase() {
        return databaseHelper.getAllUsers();
    }

    // onCreate

} // Activity