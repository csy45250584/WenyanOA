package com.haokuo.wenyanoa.util;

import com.bumptech.glide.request.RequestOptions;
import com.haokuo.wenyanoa.R;

/**
 * Created by zjf on 2018/8/22.
 */
public class ImageLoadUtil {
    public static RequestOptions sAvatarOptions = new RequestOptions()
            .placeholder(R.drawable.touxiang)
            .error(R.drawable.touxiang);
}
