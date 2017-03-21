package com.yuwubao.zytexpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.utils.ImageLoaderKit;
import com.yuwubao.zytexpress.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一句话描述
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/3/2
 */
public class ShowImgAdapter extends BaseRecycleViewAdapter {
    List<LocalMedia> localMediaList;
    public static final int UPDATE_ADD = 0x001;//在原有的基础上增加
    public static final int UPDATE_REPLACE = 0x002;//替换原有的数据

    int max_image_number = 9;
    boolean isCanAdd;

    public int getMaxCount(int maxContent) {
        return maxContent - localMediaList.size();
    }

    public int getMaxCount() {
        return max_image_number - localMediaList.size();
    }

    public void setMaxImageNumber(int max_image_number) {
        this.max_image_number = max_image_number;
    }

    public ShowImgAdapter(Context c, List<LocalMedia> imgUrls) {
        this(c, imgUrls, false);
    }

    public ShowImgAdapter(Context c, List<LocalMedia> imgUrls, boolean isCanAdd) {
        super(c);
        this.localMediaList = imgUrls;
        this.isCanAdd = isCanAdd;
    }

    public void update(List<LocalMedia> newImages, int type) {
        switch (type) {
            case UPDATE_REPLACE:
                localMediaList.clear();
                break;
        }
        localMediaList.addAll(newImages);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImgViewHolder(resIdToView(parent, R.layout.item_showimg));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
        imgViewHolder.bind();
    }

    @Override
    public int getItemCount() {
        if (isCanAdd) {
            if (localMediaList.size() == max_image_number) {
                return localMediaList.size();
            }
            return localMediaList.size() + 1;
        }
        return localMediaList.size();
    }

    class ImgViewHolder extends RecyclerView.ViewHolder {
        int pos;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.show_img)
        ImageView showImg;

        LocalMedia localMedia;

        public ImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            pos = getLayoutPosition();

            if (pos == localMediaList.size()) {
                showImg.setImageResource(R.drawable.plus);
                imgDelete.setVisibility(View.GONE);
                showImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemAddClickListener != null) {
                            onItemAddClickListener.onItemAddClick(pos);
                        }
                    }
                });
            } else {
                imgDelete.setVisibility(View.VISIBLE);
                localMedia = localMediaList.get(pos);
                int type = localMedia.getType();
                String path = "";
                if (localMedia.isCut() && !localMedia.isCompressed()) {
                    // 裁剪过
                    path = localMedia.getCutPath();
                } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia
                        .isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = localMedia.getCompressPath();
                } else {
                    // 原图
                    path = localMedia.getPath();
                }

                ImageLoader.getInstance().displayImage(StringUtils.sdcardUrlToImageLoader(path),
                        showImg, ImageLoaderKit.normalLoadOption);
                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemImgDeletListener != null) {
                            onItemImgDeletListener.onDeleteImg(pos);
                        }
                    }
                });

                showImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onImgItemClickListener != null) {
                            onImgItemClickListener.onImgClick(pos);
                        }
                    }
                });
            }
        }
    }

    private OnItemImgDeletListener onItemImgDeletListener;

    public interface OnItemImgDeletListener {
        public void onDeleteImg(int pos);
    }

    public void setOnItemImgDeletListener(OnItemImgDeletListener onItemImgDeletListener) {
        this.onItemImgDeletListener = onItemImgDeletListener;
    }

    private OnImgItemClickListener onImgItemClickListener;

    public interface OnImgItemClickListener {
        public void onImgClick(int pos);
    }

    public void setOnImgItemClickListener(OnImgItemClickListener onImgItemClickListener) {
        this.onImgItemClickListener = onImgItemClickListener;
    }

    private OnItemAddClickListener onItemAddClickListener;

    public interface OnItemAddClickListener {
        public void onItemAddClick(int pos);
    }

    public void setOnItemAddClickListener(OnItemAddClickListener onItemAddClickListener) {
        this.onItemAddClickListener = onItemAddClickListener;
    }
}
