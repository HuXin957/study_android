package com.example.study_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.study_android.util.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TakePhotoActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_TAKE = 1;
    private static final int REQUEST_CODE_CHOOSE = 2;
    private Uri imageUri;
    private ImageView ivAvatar;
    private String imageBase64;
    private ActivityResultLauncher<Intent> register;
    private ActivityResultLauncher<Intent> registerAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ivAvatar = findViewById(R.id.ivAvatar);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // 转成Base64字符串 方便上传到服务器
                        imageBase64 = ImageUtil.imageToBase64(bitmap);
                        ivAvatar.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        registerAlbum = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();

                    if (Build.VERSION.SDK_INT < 19) {
                        handleImageBeforeApi19(intent);
                    } else {
                        handleImageOnApi19(intent);
                    }
                }
            }
        });
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /*
     * 因为Android系统从4.4版本开始，选取相册中的图片不再返回图片真实的Uri了，而是一个封装过的Uri
     * 因此如果是4.4版本以上的手机就需要对这个Uri进行解析才行
     * */
    @TargetApi(19)
    private void handleImageOnApi19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri);
                    return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void resolveMSFContent(Uri uri) {
        File file = new File(getCacheDir(), "temp_file" + getContentResolver().getType(uri).split("/")[1]);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivAvatar.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取图片真实路径
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivAvatar.setImageBitmap(bitmap);
        }
    }

    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            doTake();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_TAKE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_TAKE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTake();
            } else {
                Toast.makeText(this, "你没有获得摄像头权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_CHOOSE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "你没有获得相册权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doTake() {
        /*
         * 创建file对象，用于存储拍照后的图片
         * 调用getExternalCacheDir可以得到手机SD卡的应用关联缓存目录
         * 所谓的应用关联缓存目录，就是指SD卡中专门用于存放当前应用缓存数据的位置
         * 具体的路径是/sdcard/Android/data/<package name>/cache
         * 因为从Android6.0系统开始，读写SD卡被列为了危险权限，
         * 如果将图片存放在SD卡的任何其他目录，都要进行运行时权限处理才行，而使用应用关联目录则可以跳过这一步。
         * */
        File imageTemp = new File(getExternalCacheDir(), "imageOut.jpeg");

        // 如果已经存在了图片，则删掉
        if (imageTemp.exists()) {
            imageTemp.delete();
        }

        try {
            // 将图片放入
            imageTemp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 获取Uri对象
         * 这个Uri对象标识着imageOut.jpeg这张图片的本地真实路径
         * */
        if (Build.VERSION.SDK_INT > 24) {
            /*
             * 调用FileProvider 的getUriForFile() 方法将File 对象转换成一个封装过的Uri对象
             * FileProvider则是一种特殊的内容提供器，它使用了和内容提供器类似的机制来对数据进行保护
             * 可以选择性地将封装过的Uri共享给外部，从而提高应用的安全性。
             * 第一个参数要求传入 Context对象
             * 第二个参数可以使任意唯一的字符串（需要在AndroidManifest.xml中声明）
             * 第三个参数则是我们刚刚创建的File 对象
             * */
            imageUri = FileProvider.getUriForFile(this, "com.example.study_android.fileprovider", imageTemp);
        } else {
            // 若系统的版本低于Android7.0，则调用下面的方法将File对象转换为Uri对象
            imageUri = Uri.fromFile(imageTemp);
        }

        // 启动相机程序
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//指定图片的输出地址
        register.launch(intent);
    }

    public void choosePhoto(View view) {
        // 权限WRITE_EXTERNAL_STORAGE表示同时授予程序对SD卡读和写的能力。
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CHOOSE);
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        registerAlbum.launch(intent);
    }
}