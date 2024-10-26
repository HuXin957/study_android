package com.example.study_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study_android.R;
import com.example.study_android.bean.Planet;
import com.example.study_android.util.ToastUtil;

import java.util.List;

public class PlanetListWithButtonAdapter extends BaseAdapter {

    private Context mContext;
    private List<Planet> mPlaneList;

    public PlanetListWithButtonAdapter(Context mContext, List<Planet> mPlaneList) {
        this.mContext = mContext;
        this.mPlaneList = mPlaneList;
    }

    @Override
    public int getCount() {
        return mPlaneList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPlaneList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_with_button, null);

            holder = new ViewHolder();

            holder.ll_item = convertView.findViewById(R.id.ll_item);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.btn_operator = convertView.findViewById(R.id.btn_operator);

            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // 给控件设置好数据
        Planet planet = mPlaneList.get(i);

        holder.ll_item.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        holder.btn_operator.setOnClickListener(view -> {
            ToastUtil.show(mContext, "按钮被点击了" + planet.name);
        });

        return convertView;
    }

    public final class ViewHolder {
        public LinearLayout ll_item;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
        public Button btn_operator;
    }
}
