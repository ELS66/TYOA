package com.els.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.els.myapplication.R;
import com.els.myapplication.bean.Data;
import com.els.myapplication.ui.main.project.MapAddActivity;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CitySelectPicker {
    private OptionsPickerView optionsPickerView;
    private ArrayList<Data> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    /**
     * 获取省市区
     */
    public void getInstance(final MapAddActivity context, final Handler handler,
                            int position1, int position2, int position3) {
        initJsonData(context);
        optionsPickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).getPickerViewText() +
                    options2Items.get(options1).get(options2) +
                    options3Items.get(options1).get(options2).get(options3);
            Message message = new Message();
            message.what = 2;
            message.obj = tx;
            handler.sendMessage(message);
            listener.onSelect(options1, options2, options3);
        })
                //.setTitleText("城市选择")
                .setDividerColor(Color.RED)
                .setTextColorCenter(Color.LTGRAY) //设置选中项文字颜色
                .setContentTextSize(18)
                .setLineSpacingMultiplier(2.0f)
                .setSelectOptions(position1, position2, position3)
                //.setSelectOptions(6, 6, 6)
                .setLayoutRes(R.layout.item_picker_options, v -> {
                    TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    TextView tvCancel = v.findViewById(R.id.tv_cancel);
                    //TextView tvUnit = v.findViewById(R.id.tv_unit);
                    //tvUnit.setText(title);
                    tvSubmit.setOnClickListener(v1 -> {
                        optionsPickerView.dismiss();
                        optionsPickerView.returnData();
                        //Tool.openBottomUIMenu((Activity) context);
                    });
                    tvCancel.setOnClickListener(v12 -> {
                        optionsPickerView.dismiss();
                        optionsPickerView.returnData();
                        //Tool.openBottomUIMenu((Activity) context);
                    });
                })
                .setDecorView(context.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        optionsPickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
        optionsPickerView.show();
    }

    private void initJsonData(Context context) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        String JsonData = getJson(context, "province.json");//获取assets目录下的json文件数据

        ArrayList<Data> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null
                        || jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCity().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }


    public static OnPickerListener listener;

    public void setListener(OnPickerListener listener) {
        this.listener = listener;
    }

    public interface OnPickerListener {

        void onSelect(int position1, int position2, int position3);
    }

    /**
     * 读取Json文件
     */
    private String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 解析数据
     */
    private ArrayList<Data> parseData(String result) {//Gson 解析
        ArrayList<Data> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                Data entity = gson.fromJson(data.optJSONObject(i).toString(), Data.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
