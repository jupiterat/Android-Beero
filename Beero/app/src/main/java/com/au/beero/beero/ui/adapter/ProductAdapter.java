package com.au.beero.beero.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.au.beero.beero.model.SearchResult;
import com.au.beero.beero.model.WiningDeal;
import com.au.beero.beero.utility.Constants;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/15.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<SearchResult> mProducts;
    private Context mContext;

    public ProductAdapter(Context ctx, List<SearchResult> products) {
        mProducts = products;
        mContext = ctx;
    }

    public List<SearchResult> getProducts() {
        return mProducts;
    }


    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_layout, parent, false);
        ProductHolder vh = new ProductHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        if (mProducts == null) {
            return 0;
        }
        return mProducts.size();
    }

//    @Override
//    public long generateHeaderId(int i) {
//        return 0;
//    }

    @Override
    public void onBindViewHolder(ProductHolder viewHolder, int i) {
        if (viewHolder instanceof ProductHolder) {
            ProductHolder holder = ((ProductHolder) viewHolder);
            WiningDeal deals = mProducts.get(i).getWiningDeal();

//            ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
//            holder.brandImg.setDefaultImageResId(R.drawable.brand_0);
//            holder.brandImg.setErrorImageResId(R.drawable.brand_0);
//            return resources.getDrawable(resourceId);
            Resources resources = mContext.getResources();
            String fileName = "";
            if (deals != null) {
//                holder.brandImg.setImageUrl("brand_small_" + deals.getImageId(), loader);
                fileName += "brand_small_" + deals.getImageId();
                holder.brandNameTxt.setText(deals.getBrandName());
                String container = String.format(mContext.getString(R.string.container_format), deals.getQty(), deals.getContainerSize(), deals.getContainerType());
                holder.containerTxt.setText(container);
                holder.priceTxt.setVisibility(View.VISIBLE);
                holder.priceTxt.setText(String.format(mContext.getString(R.string.price_format), deals.getPrice()));
                String value = String.format(mContext.getString(R.string.distance_format), deals.getBeautifiedDriveDistance());
                holder.distanceTxt.setVisibility(View.VISIBLE);
                holder.distanceTxt.setText(value);
                int visible = deals.isExclusive() ? View.VISIBLE : View.GONE;
                holder.exlusiveImg.setVisibility(visible);
                holder.nextIcon.setVisibility(View.VISIBLE);
                holder.winingStoreName.setVisibility(View.VISIBLE);
                holder.winingStoreName.setText(deals.getStore().getName());
            } else {
//                holder.brandImg.setImageUrl("brand_small_" + deals.getImageId(), loader);
                String notFound = mProducts.get(i).getNotFound();
                if(notFound.equals(Constants.BRAND_BOTTLE)) {
                    fileName += "brand_bottle_notfound";
                } else if(notFound.equals(Constants.BRAND_CAN)) {
                    fileName += "brand_can_notfound";
                } else {//(notFound.equals(Constants.BRAND_BOTTLE_CAN))
                    fileName += "brand_bottlecan_notfound";
                }
                holder.brandNameTxt.setText(mProducts.get(i).getBrandName());
                holder.nextIcon.setVisibility(View.GONE);
                holder.exlusiveImg.setVisibility(View.GONE);
                holder.distanceTxt.setVisibility(View.GONE);
                holder.priceTxt.setVisibility(View.GONE);
                holder.containerTxt.setText(mContext.getString(R.string.no_deal));
                holder.winingStoreName.setVisibility(View.GONE);
            }


            final int resourceId = resources.getIdentifier(fileName, "drawable",
                    mContext.getPackageName());
            holder.brandImg.setImageResource(resourceId);

        }
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
//        return null;
//    }

//    @Override
//    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
//
//    }


    class ProductHolder extends UltimateRecyclerviewViewHolder {


        ImageView brandImg;
        TextView brandNameTxt;
        TextView containerTxt;
        TextView priceTxt;
        TextView distanceTxt;
        ImageView exlusiveImg;
        ImageView nextIcon;
        TextView winingStoreName;

        public ProductHolder(View view) {
            super(view);
            brandNameTxt = (TextView) view.findViewById(R.id.product_name);
            brandImg = (ImageView) view.findViewById(R.id.product_img);
            containerTxt = (TextView) view.findViewById(R.id.product_container);
            priceTxt = (TextView) view.findViewById(R.id.product_price);
            distanceTxt = (TextView) view.findViewById(R.id.product_time);
            exlusiveImg = (ImageView) view.findViewById(R.id.exclusive_icon);
            nextIcon = (ImageView) view.findViewById(R.id.arrow_right);
            winingStoreName = (TextView) view.findViewById(R.id.winning_deal_store_name);
        }
    }


}
