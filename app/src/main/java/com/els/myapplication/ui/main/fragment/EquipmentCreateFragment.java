package com.els.myapplication.ui.main.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.ImageSaveUtil;
import com.els.myapplication.utils.QcCodeUtil;
import com.els.myapplication.utils.WebUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentCreateFragment extends Fragment {

    EditText editText_id,editText_name,editText_model;
    TextView textView;
    ImageView imageView;
    Button button;
    String id,name,model;
    Bitmap bitmap;
    Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_equipment_create, container, false);
        init(root);
        MainActivity.tv_title.setText("添加设备");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 id = editText_id.getText().toString().trim();
                 name = editText_name.getText().toString().trim();
                 model = editText_model.getText().toString().trim();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(model)) {
                    Toast.makeText(requireContext(),"请输入内容",Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String strUrl = Constant.WEB_ADDRESS + "equipment";
                            Map<String,String> map = new HashMap<>();
                            map.put("is","0");
                            map.put("id",id);
                            map.put("name",name);
                            map.put("model",model);
                            String res = WebUtil.loginsend(strUrl,map);
                            Message message = new Message();
                            message.what = 0;
                            message.obj = res;
                            handler.sendMessage(message);
                        }
                    }).start();

                }
            }
        });
        return root;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                int res = Integer.parseInt(msg.obj.toString());
                if (res == -1) {
                    Toast.makeText(requireContext(),"添加失败，请重试",Toast.LENGTH_SHORT).show();
                } else if (res == 1) {
                    //bitmap = createBitmap(id,500,500);
                    bitmap = QcCodeUtil.createImage(id,name);
                    if (bitmap != null) {
                        button.setVisibility(View.INVISIBLE);
                        imageView.setImageBitmap(bitmap);
                        textView.setVisibility(View.VISIBLE);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showListDialog(bitmap);
                            }
                        });
                    }
                }
            }
        }
    };

    private void init(View view){
        editText_id = view.findViewById(R.id.editText_equipment_create_id);
        editText_name =view.findViewById(R.id.editText_equipemnt_create_name);
        editText_model = view.findViewById(R.id.editText_equipment_create_model);
        textView = view.findViewById(R.id.textView_equipment_ceate_toast);
        button = view.findViewById(R.id.button_equipment_create);
        imageView = view.findViewById(R.id.imageView_equipment_create);
    }

    public void showListDialog(Bitmap bitmap){
        final String[] items = {"保存至手机","分享"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    saveImg();


                } else if (i==1){
                    share(requireContext(),bitmap,id + "_" +name);
                }
            }
        });
        dialog.show();
    }

    private Bitmap createBitmap(String content,int width,int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, map);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveImg(){
        XXPermissions.with(requireContext())
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            //Toast.makeText(requireContext(),"获取存储权限成功",Toast.LENGTH_SHORT).show();
                            uri = ImageSaveUtil.saveAlbum(requireContext(),bitmap, Bitmap.CompressFormat.JPEG,100,true,id,name);
                            Toast.makeText(requireContext(),"保存成功！",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never){
                            Toast.makeText(requireContext(),"被永久拒绝授权，请手动授予存储权限",Toast.LENGTH_SHORT).show();
                            XXPermissions.startPermissionActivity(requireContext(),permissions);
                        } else {
                            Toast.makeText(requireContext(),"获取存储权限失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static void share (Context context,Bitmap bitmap,String name){
        String storePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File appDir = new File(storePath);
        if (!appDir.exists()){
            appDir.mkdir();
        }
        String filename = name + ".jpg";
        File file = new File(appDir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            if (isSuccess) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", file);
                    intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }else {
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                }
                intent.setType("image/*");
                Intent chooser = Intent.createChooser(intent,"分享" );
                if(intent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(chooser);
                }
            } else {
                Toast.makeText(context, "分享失败，请重试。", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}