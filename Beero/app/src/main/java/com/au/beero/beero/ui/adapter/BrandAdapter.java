package com.au.beero.beero.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.au.beero.beero.R;
import com.au.beero.beero.manager.VolleySingleton;
import com.au.beero.beero.model.Brand;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/15.
 */
public class BrandAdapter extends UltimateViewAdapter {

    private List<Brand> mBrands;
    private Context mContext;

    public BrandAdapter(Context ctx, List<Brand> brands) {
        mBrands = brands;
        mContext = ctx;
    }

    public List<Brand> getBrands() {
        return mBrands;
    }

    public void updateList(List<Brand> lst) {
        mBrands = lst;
        notifyDataSetChanged();
    }

    /**
     * @return: selected id list
     */
    public ArrayList<String> getSelectedBrands() {
        ArrayList<String> selectedIds = null;
        for (Brand br : mBrands) {
            if (br.isSelected()) {
                if (selectedIds == null) {
                    selectedIds = new ArrayList<>();
                }
                selectedIds.add(br.getId());
            }
        }
        return selectedIds;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_item_layout, parent, false);
        BrandHolder vh = new BrandHolder(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        if (mBrands == null) {
            return 0;
        }
        return mBrands.size();
    }

    @Override
    public long generateHeaderId(int i) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof BrandHolder) {
            BrandHolder holder = ((BrandHolder) viewHolder);
            holder.brandName.setText(mBrands.get(i).getName());
            holder.selectedChk.setChecked(mBrands.get(i).isSelected());
            holder.selectedChk.setTag(mBrands.get(i));
            holder.selectedChk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Brand contact = (Brand) cb.getTag();
                    contact.setIsSelected(cb.isChecked());
                    mBrands.get(i).setIsSelected(cb.isChecked());
                }
            });
            ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
            holder.brandImg.setImageUrl(mBrands.get(i).getUrl(), loader);
            holder.brandImg.setDefaultImageResId(R.drawable.brand_0);
            holder.brandImg.setErrorImageResId(R.drawable.brand_0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    class BrandHolder extends UltimateRecyclerviewViewHolder {
        CheckBox selectedChk;
        TextView brandName;
        NetworkImageView brandImg;

        public BrandHolder(View view) {
            super(view);
            selectedChk = (CheckBox) view.findViewById(R.id.brand_select_chk);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            brandImg = (NetworkImageView) view.findViewById(R.id.brand_img);
        }
    }
}
