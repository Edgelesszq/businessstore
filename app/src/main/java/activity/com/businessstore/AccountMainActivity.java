package activity.com.businessstore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.PermissionUtil;
import com.businessstore.util.PictureCutUtil;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.view.popwindow.CommonPopupWindow;
import com.businessstore.view.popwindow.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.tools.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountMainActivity extends BaseActivity implements View.OnClickListener,CommonPopupWindow.ViewInterface {
    private Context mContext;
    private TextView update_password,update_phonenum,name_tv,user_num;
    private CommonPopupWindow popupWindow;
    private CircleImageView HeadPortrait_update;
    private ToggleButton btn_switch;
    private Button btn_take_photo,btn_select_photo,btn_cancel;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private LoginResult loginResult;
    private final int REQUEST_CODE_CAMERA = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");
        setContentView(R.layout.my_account);

        mContext = this;

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");

        initview();
    }

    public void initview(){
        setTitleView(R.drawable.backimage,R.string.my_account);
        mTitleLefeBackImg.setOnClickListener(this);
        update_password=findViewById(R.id.update_password_textview);
        update_phonenum=findViewById(R.id.update_phoneNum_text);//手机号显示
        update_password.setOnClickListener(this);
        update_phonenum.setOnClickListener(this);

        btn_switch=findViewById(R.id.switch_btn);

        name_tv=findViewById(R.id.name_tv);//昵称显示
        name_tv.setOnClickListener(this);
        user_num = findViewById(R.id.text_num2);//账号显示
//
       // Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
       /* if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            touxiang.setImageDrawable(drawable);
        } else {
            *//**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             *//*
        }*/

        HeadPortrait_update=findViewById(R.id.HeadPortrait_update);//头像
        File headImg=new File(path+"head.jpg");
        if (loginResult.getSellerHead()==null){
            Glide.with(this).load(R.drawable.qidong)
                    .into(HeadPortrait_update);
        }
        else {
            Glide.with(this).load(loginResult.getSellerHead())
                    .into(HeadPortrait_update);
        }





        HeadPortrait_update.setOnClickListener(this);


        //显示昵称
        if (StringUtil.isBlank(loginResult.getSellerName())){
            name_tv.setText(loginResult.getSellerNum());
        }
        if (!StringUtil.isBlank(loginResult.getSellerName())){
            name_tv.setText(loginResult.getSellerName());
        }
        //显示账号
        if (loginResult.getSellerNum()!=null){
            user_num.setText(loginResult.getSellerNum());
        }
        //显示手机号
        if (loginResult.getSellerTel()!=null){
            update_phonenum.setText(loginResult.getSellerTel());
        }
        //是否公开号码
        btn_switch=findViewById(R.id.switch_btn);
        if (loginResult.getTelOpen().equals("0")){
            btn_switch.setChecked(true);
        }else {
            btn_switch.setChecked(false);
        }
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    OkGo.<String>post(Config.URL + "/user/editUserInfo")
                            .tag(this)
                            .params("headImg",loginResult.getSellerHead())
                            .params("sellerName",loginResult.getSellerName())
                            .params("sellerTel",loginResult.getSellerTel())
                            .params("telOpen","1")
                            .params("sellerId",loginResult.getSellerId())
                            .params("appKey",loginResult.getAppKey())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.d("loglog",response.body());
                                    String responedata = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    Json<LoginResult> jsondata = gson.fromJson(responedata, new TypeToken<Json<LoginResult>>() {}.getType());
                                    if (jsondata.getCode()==0){
                                        SharedPreferencesUtil.putObject(mContext,"loginResult",jsondata.getData());
                                        Log.d("loglog",jsondata.getData().getTelOpen());

                                    }else{
                                        Toast.makeText(mContext,jsondata.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    OkGo.<String>post(Config.URL + "/user/editUserInfo")
                            .tag(this)
                            .params("headImg",loginResult.getSellerHead())
                            .params("sellerName",loginResult.getSellerName())
                            .params("sellerTel",loginResult.getSellerTel())
                            .params("telOpen","0")
                            .params("sellerId",loginResult.getSellerId())
                            .params("appKey",loginResult.getAppKey())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.d("loglog",response.body());
                                    String responedata = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    Json<LoginResult> jsondata = gson.fromJson(responedata, new TypeToken<Json<LoginResult>>() {}.getType());
                                    if (jsondata.getCode()==0){
                                        SharedPreferencesUtil.putObject(mContext,"loginResult",jsondata.getData());
                                        Log.d("loglog",jsondata.getData().getTelOpen());
                                    }else{
                                        Toast.makeText(mContext,jsondata.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
    }
    public void requestCameraPermisson() {

       boolean isget= PermissionUtil.requestPerssions(this, REQUEST_CODE_CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
       boolean get= PermissionUtil.getCameraPermissions(this, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.update_password_textview:
                Intent intent=new Intent(AccountMainActivity.this,AccountUpadatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.update_phoneNum_text:
               /* String phonenum=update_phonenum.getText().toString().trim();
                Intent intent2=new Intent(AccountMainActivity.this,AccountUpadatePhoneNumActivity.class);
               intent2.putExtra("phoneNum",phonenum);
                startActivityForResult(intent2,2334);*/
                ActivityUtil.startActivity(AccountMainActivity.this,AccountUpadatePhoneNumActivity.class);

                // startActivity(intent2);
                break;
            case R.id.HeadPortrait_update:
                showPopWindowUpdate_HeadPortrait(view);
                break;
            case R.id.name_tv:
                String namestr=name_tv.getText().toString().trim();
                Intent updateName=new Intent(this,AccountUpadateNameActivity.class);
                updateName.putExtra("Name",namestr);
                startActivityForResult(updateName,2335);
        }

    }
    public void showPopWindowUpdate_HeadPortrait(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popwindow_choose_photo_item, null);
        //测量View的宽高

        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popwindow_choose_photo_item)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
               // .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        btn_select_photo= view.findViewById(R.id.btn_select_photo);
                        btn_take_photo=view.findViewById(R.id.btn_take_photo);
                        btn_cancel=view.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("tag","asaaa");
                                requestCameraPermisson();

                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());

                                startActivityForResult(intent2, 2);

                                popupWindow.dismiss();

                            }
                        });
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                //打开文件
                                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                                startActivityForResult(intent1, 1);
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                .create();



        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.popwindow_choose_photo_item:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode==RESULT_OK){
           switch (requestCode){
               case 1:
                   if (resultCode == RESULT_OK) {
                       cropPhoto(data.getData());// 裁剪图片
                   }

                   break;
               case 2:
                   if (resultCode == RESULT_OK) {
                       String filePath=Environment.getExternalStorageDirectory() + "/head.jpg";
                       File temp = new File(filePath);
                       cropPhoto(Uri.fromFile(temp));// 裁剪图片
                   }

                   break;
               case 3:
                   if (data != null) {

                       Bundle extras = data.getExtras();
                       head = extras.getParcelable("data");
                       PictureCutUtil pictureCutUtil=new PictureCutUtil(this);
                       String filepath=pictureCutUtil.cutPictureQuality(head, "Img_data");

                       Log.d("loglog",filepath);

                       if (head != null) {
                           /**
                            * 上传服务器代码
                            */
                           OkGo.<String>post(Config.URL + "/user/editUserInfo")
                                   .tag(this)
                                   .params("headImg",new File(filepath))
                                   .params("sellerName",loginResult.getSellerName())
                                   .params("sellerTel",loginResult.getSellerTel())
                                   .params("telOpen",loginResult.getTelOpen())
                                   .params("sellerId",loginResult.getSellerId())
                                   .params("appKey",loginResult.getAppKey())
                                   .execute(new StringCallback() {
                                       @Override
                                       public void onSuccess(Response<String> response) {
                                           Log.d("loglog",response.body());
                                           String responedata = response.body().toString().trim();
                                           Gson gson = new Gson();
                                           Json<LoginResult> jsondata = gson.fromJson(responedata, new TypeToken<Json<LoginResult>>() {}.getType());
                                           if (jsondata.getCode()==0){
                                               SharedPreferencesUtil.putObject(mContext,"loginResult",jsondata.getData());
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Glide.with(getApplicationContext()).load(head).into(HeadPortrait_update);

                                                    }
                                                });

                                           }else{
                                               Toast.makeText(mContext,jsondata.getMsg(),Toast.LENGTH_SHORT).show();
                                           }
                                       }

                                       @Override
                                       public void onError(Response<String> response) {
                                           super.onError(response);

                                       }

                                       @Override
                                       public void uploadProgress(Progress progress) {
                                           super.uploadProgress(progress);

                                       }
                                   });
/*
                           setPicToView(head);// 保存在SD卡中
*/
                          /* Glide.with(this).load(head)
                                   .into(HeadPortrait_update);*/
                         //  HeadPortrait_update.setImageBitmap(head);// 用ImageButton显示出来
                       }
                   }
                   break;

           }
       }
    }
    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
