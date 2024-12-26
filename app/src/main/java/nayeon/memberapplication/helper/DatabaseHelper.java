package nayeon.memberapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 데이터베이스 초기화를 위한 변수
    private static final String DBNAME = "android.db";
    private static final int DBVERSION = 1;

    // 생성자 - 클래스 호출 시, 자동으로 sqlite 디비 파일 생성(한 번만)
    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    // 디비 파일 생성 시, 자동으로 실행하는 메서드
    // 주로 테이블 생성 시, 사용
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table member(" +
                "mno int primary key autoincrement," +
                "userid varchar(18) unique," +
                "passwd varchar(18) not null," +
                "name varchar(18) not null," +
                "email text not null," +
                "regdate datetime default current_timestamp)";
        db.execSQL(SQL);
    }

    // 테이블 재생성 시, 사용
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists member");
        onCreate(db);
    }
}
