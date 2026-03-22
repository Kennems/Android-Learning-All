package com.showguan.chapter07_client;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter07_client.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "Kennem";

    private TextView et_name;
    private TextView et_phone;
    private TextView et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_write).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_write) {
            // 创建一个联系人对象
            Contact contact = new Contact();
            contact.name = et_name.getText().toString();
            contact.phone = et_phone.getText().toString();
            contact.email = et_email.getText().toString();
            //addContacts(getContentResolver(), contact);

            //批处理方式，每一次操作都是一个ContentProviderOperation， 构建一个操作集合，然后一次性执行
            // 好处：要么全部成功，要么全部失败，保证了事务的一致性
            addFullContacts(getContentResolver(), contact);
        } else if (v.getId() == R.id.btn_read) {
            readPhoneContact(getContentResolver());
        }
    }

    @SuppressLint("Range") // 禁用范围警告，因为我们手动处理列索引
    private void readPhoneContact(ContentResolver resolver) {
        // 查询RawContacts表，获取所有联系人的_raw_contact_id
        Cursor cursor = resolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID}, // 只查询_raw_contact_id列
                null, // 无筛选条件
                null, // 无筛选条件参数
                null // 无排序
        );

        // 遍历查询结果
        while (cursor.moveToNext()) {
            // 获取当前记录的raw_contact_id
            int rawContactId = cursor.getInt(0);

            // 构建用于查询联系人的Data URI
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");

            // 查询Data表，获取与当前联系人相关的所有数据
            Cursor dataCursor = resolver.query(
                    uri,
                    new String[]{Contacts.Data.MIMETYPE, Contacts.Data.DATA1, Contacts.Data.DATA2}, // 查询MIME类型和数据列
                    null, // 无筛选条件
                    null, // 无筛选条件参数
                    null // 无排序
            );

            // 创建一个Contact对象用于存储查询结果
            Contact contact = new Contact();

            // 遍历查询结果
            while (dataCursor.moveToNext()) {
                // 获取当前记录的DATA1列的值
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.DATA1));
                // 获取当前记录的MIMETYPE列的值
                String mimeType = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.MIMETYPE));

                // 根据MIME类型，将数据存储到相应的Contact属性中
                switch (mimeType) {
                    case CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        // 如果MIME类型是StructuredName，则将data1赋值给contact的name属性
                        contact.name = data1;
                        break;
                    case CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        // 如果MIME类型是Phone，则将data1赋值给contact的phone属性
                        contact.phone = data1;
                        break;
                    case CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        // 如果MIME类型是Email，则将data1赋值给contact的email属性
                        contact.email = data1;
                        break;
                }
            }

            // 关闭Data表的查询结果游标
            dataCursor.close();

            // 如果contact的name属性不为空，则输出联系人姓名到日志
            if (contact.name != null) {
                Log.d(TAG, contact.name);
            }
        }

        // 关闭RawContacts表的查询结果游标
        cursor.close();
    }


    // 往手机通讯录一次性添加一个联系人信息（包括主记录，姓名，电话号码，电子邮箱）
    // 事务性，要么全部成功，要么全部失败
    private void addFullContacts(ContentResolver contentResolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation op_main = ContentProviderOperation
                // 设置操作类型为插入
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                // 设置插入的ACCOUNT_NAME为null，表示使用默认账户
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                // 构建操作
                .build();

        // 创建一个插入联系人姓名的内容操作器
        ContentProviderOperation op_name = ContentProviderOperation
                // 设置操作类型为插入
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 设置引用的RAW_CONTACT_ID为前一个操作生成的ID
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                // 设置数据的MIME类型为StructuredName（联系人的名字）
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                // 设置联系人的名字
                .withValue(Contacts.Data.DATA1, contact.name)
                // 构建操作
                .build();

        // 创建一个插入联系人电话号码的内容操作器
        ContentProviderOperation op_phone = ContentProviderOperation
                // 设置操作类型为插入
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 设置引用的RAW_CONTACT_ID为前一个操作生成的ID
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                // 设置数据的MIME类型为Phone（联系人的电话）
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                // 设置联系人的电话号码
                .withValue(Contacts.Data.DATA1, contact.phone)
                // 设置电话号码的类型为Mobile（手机）
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE)
                // 构建操作
                .build();

        // 创建一个插入联系人电子邮箱的内容操作器
        ContentProviderOperation op_email = ContentProviderOperation
                // 设置操作类型为插入
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 设置引用的RAW_CONTACT_ID为前一个操作生成的ID
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                // 设置数据的MIME类型为Email（联系人的邮箱）
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                // 设置联系人的电子邮箱地址
                .withValue(Contacts.Data.DATA1, contact.email)
                // 设置电子邮箱的类型为Mobile（手机邮箱）
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_MOBILE)
                // 构建操作
                .build();

        // 创建一个操作列表，用于批量执行所有操作
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        // 将插入主记录的操作添加到操作列表
        operations.add(op_main);
        // 将插入姓名的操作添加到操作列表
        operations.add(op_name);
        // 将插入电话号码的操作添加到操作列表
        operations.add(op_phone);
        // 将插入电子邮箱的操作添加到操作列表
        operations.add(op_email);

        try {
            // 执行批量操作，将所有数据插入联系人数据库
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (OperationApplicationException e) {
            // 捕获OperationApplicationException异常并抛出RuntimeException
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            // 捕获RemoteException异常并抛出RuntimeException
            throw new RuntimeException(e);
        }
    }


    private void addContacts(ContentResolver resolver, Contact contact) {
        // 创建一个ContentValues对象，用于存储新联系人数据
        ContentValues values = new ContentValues();
        // 插入一个空的RawContact记录，返回该记录的URI
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        // 解析出新插入记录的ID
        long rawContactId = ContentUris.parseId(uri);

        // 创建一个ContentValues对象，用于存储联系人的名字数据
        ContentValues name = new ContentValues();
        // 设置RAW_CONTACT_ID，表示该数据属于哪个联系人
        name.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 设置数据的MIME类型为StructuredName（联系人的名字）
        name.put(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 设置联系人的名字
        name.put(Contacts.Data.DATA1, contact.name);

        // 将联系人的名字数据插入到Data表中
        resolver.insert(ContactsContract.Data.CONTENT_URI, name);


        // 创建一个ContentValues对象，用于存储联系人的电话号码数据
        ContentValues phone = new ContentValues();
        // 设置RAW_CONTACT_ID，表示该数据属于哪个联系人
        phone.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 设置数据的MIME类型为Phone（联系人的电话）
        phone.put(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 设置联系人的电话号码
        phone.put(Contacts.Data.DATA1, contact.phone);
        // 设置电话号码的类型为Mobile（手机）
        phone.put(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE);
        // 将联系人的电话号码数据插入到Data表中
        resolver.insert(ContactsContract.Data.CONTENT_URI, phone);

        // 创建一个ContentValues对象，用于存储联系人的邮箱数据
        ContentValues email = new ContentValues();
        // 设置RAW_CONTACT_ID，表示该数据属于哪个联系人
        email.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 设置数据的MIME类型为Email（联系人的邮箱）
        email.put(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 设置联系人的邮箱地址（此处错误，应该是contact.email，而不是contact.phone）
        email.put(Contacts.Data.DATA1, contact.email); // 纠正了原来的错误
        // 设置邮箱地址的类型为Work（工作邮箱）
        email.put(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_WORK);
        // 将联系人的邮箱数据插入到Data表中
        resolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }


    private void addContact(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawDataId = ContentUris.parseId(uri);


    }

}