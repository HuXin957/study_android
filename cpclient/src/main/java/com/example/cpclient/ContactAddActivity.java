package com.example.cpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;

import com.example.cpclient.entity.Contact;
import com.example.cpclient.util.PermissionUtil;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;

    private static final String[] PERMISSION_CONTACTS = new String[]{
            android.Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    };
    private static final int REQUEST_CONTACTS_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 保存联系人
            case R.id.btn_save:
                Boolean hasPermission = PermissionUtil.checkPermission(this, PERMISSION_CONTACTS, REQUEST_CONTACTS_CODE);

                if (!hasPermission) return;

                // 创建一个联系人对象
                Contact contact = new Contact();
                contact.name = et_name.getText().toString().trim();
                contact.phone = et_phone.getText().toString().trim();
                contact.email = et_email.getText().toString().trim();
                // 方式一：使用ContentResolver多次写入，每次写入一个字段
                addContacts(getContentResolver(), contact);
                break;
            case R.id.btn_query:
                break;
        }
    }

    private void addContacts(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        // 往 raw_contacts 添加联系人记录，并获取添加后的联系人编号
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

        /*
         * 姓名
         * */
        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 姓名的数据类型
        name.put(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        name.put(Contacts.Data.DATA2, contact.name);
        // 添加联系人姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, name);

        /*
         * 电话
         * */
        ContentValues phone = new ContentValues();
        // 关联联系人编号
        phone.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 号码的数据类型
        phone.put(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        phone.put(Contacts.Data.DATA1, contact.phone);
        // 联系类型> 1：家庭，2：工作
        phone.put(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE);
        // 添加联系人姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, phone);

        /*
         * 邮箱
         * */
        ContentValues email = new ContentValues();
        // 关联联系人编号
        email.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 邮箱的数据类型
        email.put(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的邮箱
        email.put(Contacts.Data.DATA1, contact.email);
        // 邮箱类型
        email.put(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_WORK);
        // 添加联系人姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }
}