/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

import java.util.List;

public class StringFormatUtil {

    public static String getProductServices(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (list == null || list.size() == 0) {
            return "";
        }
        for (String service : list) {
            stringBuilder.append(service + "\n");
        }
        return stringBuilder.toString();
    }

    public static SpannableString getPrice(int price) {
        SpannableString result = new SpannableString("￥" + price);
        result.setSpan(new ForegroundColorSpan(Color.RED), 0, result.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    public static SpannableString getOriginalPrice(String conteant) {
        SpannableString result = new SpannableString(conteant);
        result.setSpan(new StrikethroughSpan(), 0, result.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }
}