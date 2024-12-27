package nayeon.memberapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

    // SharedPreferences : 경량 데이터를 저장하기 위한 내부 객체
    // 데이터는 보통 키-값 형태로 앱의 내부 저장소에 저장
    SharedPreferences sharedPreferences;

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

        // sharedPreferences 초기화
        // MODE_PRIVATE : 특정 앱만 접근 가능하도록 설정
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // 회원가입 이벤트 처리
        buttonJoin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerUser();
                    }
                }
        );

        // 회원 조회 이벤트 처리
        // 로그인돼 있다면, UserListActivity로 이동
        // 로그인돼 있지 않다면, LoginActivity로 이동
        buttonUserlist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 로그인 관련 변수 가져오기
                        // getBoolean(키, 기본값)
                        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);

                        if (isLogin) { // 로그인 했다면 UserListActivity를 view에 표시
                            Intent intent = new Intent(MainActivity.this, UserlistActivity.class);
                            startActivity(intent);
                            //Toast.makeText(MainActivity.this, "로그인 성공 !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //Toast.makeText(MainActivity.this, "로그인 실패 ㅜ", Toast.LENGTH_SHORT).show();
                        }
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
        if (databaseHelper.useridCheck(userid)) {
            Toast.makeText(this, "이미 사용 중인 아이디", Toast.LENGTH_SHORT).show();
            return;
        }

        // 회원 저장
        boolean success = databaseHelper.insertMember(userid, passwd, name, email);
        if(success){
            Toast.makeText(this, "💖 회원가입 성공 💖", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "😓 다시 시도해보세요 😓", Toast.LENGTH_SHORT).show();
        }
    }
}









