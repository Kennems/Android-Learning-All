package com.showguan.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter06.dao.BookDao;
import com.showguan.chapter06.enity.BookInfo;
import com.showguan.chapter06.util.ToastUtil;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "Kennem";

    private EditText et_name;
    private EditText et_author;
    private EditText et_publish;
    private EditText et_price;
    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_publish = findViewById(R.id.et_publish);
        et_price = findViewById(R.id.et_price);


        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);
        findViewById(R.id.btn_deleteAll).setOnClickListener(this);

        bookDao = MyApplication.getInstance().getBookDatabase().bookDao();
    }

    @Override
    public void onClick(View v) {
        String name = null;
        String author = null;
        String publish = null;
        Double price = 0.0;
        if (!et_name.getText().toString().equals("")) {
            name = et_name.getText().toString();
        }
        if (!et_author.getText().toString().equals("")) {
            author = et_author.getText().toString();
        }
        if (!et_publish.getText().toString().equals("")) {
            publish = et_publish.getText().toString();
        }
        if (!et_price.getText().toString().equals("")) {
//            Log.d(TAG, et_price.getText().toString());
            price = Double.valueOf(et_price.getText().toString());
        }

        if (v.getId() == R.id.btn_add) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setName(name);
            bookInfo.setAuthor(author);
            bookInfo.setPublish(publish);
            bookInfo.setPrice(price);
            bookDao.insert(bookInfo);
            ToastUtil.show(this, "保存成功！");
        } else if (v.getId() == R.id.btn_delete) {
            BookInfo b = new BookInfo();
            int id = bookDao.queryByName(name).getId();
            b.setId(id);

            bookDao.delete(b);
        } else if (v.getId() == R.id.btn_update) {
            BookInfo b3 = new BookInfo();
            BookInfo b4 = bookDao.queryByName(name);
            b3.setId(b4.getId());
            b3.setName(name);
            b3.setAuthor(author);
            b3.setPublish(publish);
            b3.setPrice(price);
            bookDao.update(b3);
        } else if (v.getId() == R.id.btn_select) {
            List<BookInfo> bookInfos = bookDao.queryAll();
            for (BookInfo bookInfo : bookInfos) {
                Log.d(TAG, bookInfo.toString());
            }
        } else if (v.getId() == R.id.btn_deleteAll) {
            bookDao.deleteAll();
        }
    }
}