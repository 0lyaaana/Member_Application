package nayeon.memberapplication;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nayeon.memberapplication.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    // 변수 선언
    private EditText editTextUserid, editTextPasswd, editTextName, editTextEmail;
    private Button buttonJoin, buttonUserlist;
    private DatabaseHelper databaseHelper;

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

        // view 초기화
        editTextUserid = findViewById(R.id.editTextUserid);
        editTextPasswd = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonUserlist = findViewById(R.id.buttonUserlist);

        // DB Helper 초기화
        databaseHelper = new DatabaseHelper(this);

        // 회원가입 이벤트 처리
        buttonJoin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerUser();
                    }
                }
        );

    }

    private void registerUser() {
        // 변수 초기화
        String userid = editTextUserid.getText().toString().trim();
        String passwd = editTextPasswd.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        
        // 입력값 검증
        if(userid.isEmpty() || passwd.isEmpty() || name.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "모든 정보 입력 바람", Toast.LENGTH_SHORT).show();
            return; // 여기서 중지
        }
        
        // 중복 아이디 체크


        // 회원 저장
        boolean success = databaseHelper.insertMember(userid, passwd, name, email);
        if(success){
            Toast.makeText(this, "💖 회원가입 성공 💖", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "😓 다시 시도해보세요 😓", Toast.LENGTH_SHORT).show();
        }
    }
}









