package com.example.cpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;

import com.example.cpclient.entity.Contact;
import com.example.cpclient.util.PermissionUtil;

import java.util.ArrayList;

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
                // addContacts(getContentResolver(), contact);

                // 方式二：
                // 往手机通讯录一次性添加一个联系人信息，（包括主记录、姓名、电话号码、邮箱）
                addFullContacts(getContentResolver(), contact);
                break;
            case R.id.btn_query:
                readPhoneContacts(getContentResolver());
                break;
        }
    }

    @SuppressLint("Range")
    private void readPhoneContacts(ContentResolver resolver) {
        // 先查询 raw_contacts 表，再根据 row_contacts_id 去查询 data 表
        Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null, null, null);

        while (cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = resolver.query(uri, new String[]{Contacts.Data.MIMETYPE, Contacts.Data.DATA1, Contacts.Data.DATA2}, null, null, null);

            Contact contact = new Contact();
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.DATA1));
                String mimeType = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.MIMETYPE));

                switch (mimeType) {
                    // 姓名
                    case CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        contact.name = data1;
                        // 电话
                    case CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        contact.phone = data1;
                        // 邮箱
                    case CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        contact.email = data1;
                }
            }
            dataCursor.close();
            Log.d("AAAA", contact.toString());
        }
        cursor.close();
    }

    // 往手机通讯录一次性添加一个联系人信息（包括姓名、电话号码、邮箱）
    private void addFullContacts(ContentResolver resolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation op_main = ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        // 创建一个插入联系人姓名记录的内容操作器
        ContentProviderOperation op_name = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA2, contact.name)
                .build();

        // 创建一个插入联系人电话号码记录的内容操作器
        ContentProviderOperation op_phone = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1, contact.phone)
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        // 创建一个插入联系人电子邮箱记录的内容操作器
        ContentProviderOperation op_email = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1, contact.email)
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_WORK)
                .build();

        // 声明一个内容操作器的列表，并将上面四个操作器添加到该列表中
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(op_main);
        operations.add(op_name);
        operations.add(op_phone);
        operations.add(op_email);

        try {
            // 批量提交四个操作
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (OperationApplicationException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    // 往手机通讯录添加一个联系人信息（包括姓名、电话号码、邮箱）
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
        // 电话号码的数据类型
        phone.put(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话
        phone.put(Contacts.Data.DATA1, contact.phone);
        // 联系类型> 1：家庭，2：工作
        phone.put(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE);
        // 添加联系人电话记录
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
        // 添加联系人邮箱记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }
}