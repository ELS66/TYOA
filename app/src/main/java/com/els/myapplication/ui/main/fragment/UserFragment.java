package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.adapter.MyAdapter;
import com.els.myapplication.bean.MyItem;
import com.els.myapplication.bean.User;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.GlideEngine;
import com.els.myapplication.utils.MyOneLineView;
import com.els.myapplication.utils.ShpUtil;
import com.els.myapplication.utils.ToastUtil;
import com.els.myapplication.utils.WebUtil;
import com.els.myapplication.view.RoundImageView;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.els.myapplication.Constant.TAG;

public class UserFragment extends Fragment {

    private TextView textname,textid,textregular;
    private List<MyItem> itemUserList = new ArrayList<>();
    private List<MyItem> itemBossList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView image;
    private View root;
    private User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemUserList.add(new MyItem("请假",R.drawable.item_mess,R.drawable.item_to));
        itemUserList.add(new MyItem("设置",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("设置",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("人事",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("通知",R.drawable.item_set,R.drawable.item_set));
        itemBossList.add(new MyItem("项目",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("设备",R.drawable.item_set,R.drawable.item_to));
        user = MainActivity.user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user,container,false);
        if (MainActivity.user.getRoot() == 0) {
            initBoss(root);
        } else {
            initUser(root);
        }
        image = root.findViewById(R.id.img_header);
        initimage();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + 1111 );
                EasyPhotos.createAlbum(requireActivity(),true,false, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.els.myapplication.fileprovider")
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                Log.e(TAG,"111");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String strUrl = Constant.WEB_ADDRESS + "/load";
                                        String res = WebUtil.formUpload(strUrl,photos.get(0).path,user.getId());
                                        Log.e(TAG,res);
                                        Message message = new Message();
                                        if (res.equals("")){
                                            message.what = 0;
                                        } else {
                                            message.what = 1;
                                            message.obj = res;
                                        }
                                        handler.sendMessage(message);
                                    }
                                }).start();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
        //initimage();
        /*button_ToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShpUtil shpUtil = new ShpUtil(requireContext(),"login");
                shpUtil.clear();
                Intent intent = new Intent();
                intent.setClass(requireContext(), LoginActivity.class);
                startActivity(intent);
                MainActivity.activity.finish();
            }
        });*/

        return root;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0 : {
                    ToastUtil.showToast("上传失败，请重试！");
                    break;
                }
                case 1 : {
                    ShpUtil shpUtil = new ShpUtil(requireContext(),"image");
                    shpUtil.clear();
                    String path = msg.obj.toString().trim();
                    shpUtil.save("path", path);
                    Glide.with(requireContext()).load(Constant.WEB_ADDRESS + "/upload/"+path)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(image);
                    break;
                }
            }
        }
    };

    private  void initUser(View view){
        textname = view.findViewById(R.id.textView_user_name);
        textid = view.findViewById(R.id.textView_user_id);
        textregular = view.findViewById(R.id.textView_user_regular);
        textname.setText(user.getUsername());
        textid.setText(String.valueOf(user.getId()));
        if (user.getRoot() == 1) {
            textregular.setText("正式员工");
        } else {
            textregular.setText("临时员工");
        }
        recyclerView = view.findViewById(R.id.recyclerView_user);
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter.submitList(itemUserList);
    }

    private void initBoss(View view){
        textname = view.findViewById(R.id.textView_user_name);
        textid = view.findViewById(R.id.textView_user_id);
        textregular = view.findViewById(R.id.textView_user_regular);
        textregular.setText("管理员用户");
        recyclerView = view.findViewById(R.id.recyclerView_user);
        textname.setText(user.getUsername());
        textid.setText(String.valueOf(user.getId()));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter.submitList(itemBossList);
    }

    private void initimage() {
        ShpUtil shpUtil = new ShpUtil(requireContext(),"image");
        String res = shpUtil.load("path");
        String path = Constant.WEB_ADDRESS + "/upload/"+res;
        Log.e(TAG, "initimage: " + path );
        if (!res.equals("null")) {
            Glide.with(requireContext()).load(path)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(image);
        }
    }



}