package com.example.study_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.study_android.dao.BookDao;
import com.example.study_android.entity.BookInfo;
import com.example.study_android.util.ToastUtil;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_book_name;
    private EditText et_author;
    private EditText et_price;
    private BookDao bookDao;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);

        et_book_name = findViewById(R.id.et_book_name);
        et_author = findViewById(R.id.et_author);
        et_price = findViewById(R.id.et_price);
        tv_result = findViewById(R.id.tv_result);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        bookDao = MyApplication.getInstance().getBookDB().bookDao();
    }

    @Override
    public void onClick(View view) {
        String name = et_book_name.getText().toString();
        String author = et_author.getText().toString();
        String price = et_price.getText().toString();

        switch (view.getId()) {
            case R.id.btn_save:
                BookInfo book = new BookInfo();
                book.name = name;
                book.author = author;
                book.price = Double.parseDouble(price);
                bookDao.insert(book);
                ToastUtil.show(this, "保存成功");
                break;
            case R.id.btn_del:
                BookInfo b2 = new BookInfo();
                b2.id = 1;
                bookDao.delete(b2);
                break;
            case R.id.btn_edit:
                BookInfo b3 = new BookInfo();
                BookInfo b4=bookDao.queryByName(name);

                b3.id = b4.id;
                b3.name = name;

                bookDao.update(b3);
                break;
            case R.id.btn_query:
                List<BookInfo> list = bookDao.queryAll();
                StringBuilder sb = new StringBuilder();

                for (BookInfo b : list) {
                    sb.append(b.toString());
                }
                tv_result.setText(sb);
                break;
        }
    }
}