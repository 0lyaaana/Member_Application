package nayeon.memberapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import nayeon.memberapplication.helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    // 변수 선언
    private EditText editTextUserid, editTextPasswd;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 변수 초기화
        editTextUserid = findViewById(R.id.editTextUserid);
        editTextPasswd = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // DB Helper 초기화
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // 로그인 이벤트 처리
        buttonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 입력한 아이디와 비밀번호 가져오기
                        String userid = editTextUserid.getText().toString().trim();
                        String passwd = editTextPasswd.getText().toString().trim();

                        // 로그인 성공 여부 확인
                        if (databaseHelper.loginUser(userid, passwd)) {

                            // 로그인 성공 시, sharedPreference에 세선 데이터 저장
                            // sharedPreferences 수정을 위한 초기화
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLogin", true);
                            editor.apply(); // 추가한 내용 저장

                            Intent intent = new Intent(LoginActivity.this, UserlistActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "😓 로그인 실패! 다시 시도해보세요 😓", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}