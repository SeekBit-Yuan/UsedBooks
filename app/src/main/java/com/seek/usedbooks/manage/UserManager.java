/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.manage;

import android.text.TextUtils;
import android.util.Log;

import com.maxleap.DeleteCallback;
import com.maxleap.LogInCallback;
import com.maxleap.MLAnonymousUtils;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLRelation;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.SaveCallback;
import com.maxleap.SignUpCallback;
import com.maxleap.ValidateUsernameCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.models.Address;
import com.seek.usedbooks.models.Comment;
import com.seek.usedbooks.models.Order;
import com.seek.usedbooks.models.Product;
import com.seek.usedbooks.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private static User currentUser;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    public void getSmsCode(final String phone, final OperationCallback callback) {
        MLUserManager.requestLoginSmsCodeInBackground(phone, new RequestSmsCodeCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

    public void loginWithSms(final User user, final String phone, final String smsCode, final OperationCallback callback) {
        MLUserManager.loginWithSmsCodeInBackground(phone, smsCode, new LogInCallback() {
            @Override
            public void done(MLUser mlUser, MLException e) {
                System.out.println("===========" + Thread.currentThread().getName());


                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

    /**
     * 使用电话号码登录
     *
     * @param user
     * @param callback
     */

    public void login(final User user, final OperationCallback callback) {
        final MLUser mMLUser = new MLUser();
        mMLUser.setUserName(user.getUsername());
        mMLUser.setPassword(user.getUsername());
        mMLUser.put("tel", user.getUsername());
        mMLUser.put("nickname", user.getUsername());
        MLUserManager.checkUsernameExistInBackground(user.getUsername(), new ValidateUsernameCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    MLUserManager.logInInBackground(user.getUsername(), user.getUsername(), new LogInCallback() {
                        @Override
                        public void done(MLUser MLUser, MLException e) {
                            if (e == null) {
                                callback.success();
                            } else {
                                if (e.getCode() == MLException.OBJECT_NOT_FOUND) {
                                } else {
                                    callback.failed(e.getMessage());
                                }
                            }

                        }
                    });
                } else {
                    MLUserManager.signUpInBackground(mMLUser, new SignUpCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                callback.success();
                            } else {
                                callback.failed(e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public User getCurrentUser() {
        if (currentUser == null) {
            MLUser mLUser = MLUser.getCurrentUser();
            if (mLUser == null || MLAnonymousUtils.isLinked(mLUser)) return null;

            currentUser = new User();
            currentUser.setId(mLUser.getObjectId());
            currentUser.setUsername(mLUser.getUserName());
            currentUser.setIcon(mLUser.getString("icon"));
            currentUser.setNickname(mLUser.getString("nickname"));
            MLRelation relation = mLUser.getRelation("favorites");
            relation.setTargetClass("Product");
            currentUser.setFavorites(relation);
        }

        return currentUser;
    }

    /**
     * 更新用户基本信息, 包括电话, 昵称和头像
     *
     * @param user
     * @param callback
     */
    public void updateUserBasicInfo(User user, final OperationCallback callback) {
        if (TextUtils.isEmpty(user.getId())) {
            callback.failed("User id cannot be empty");
        }

        MLUser mLUser = MLUser.getCurrentUser();
        mLUser.setObjectId(user.getId());

        mLUser.put("tel", user.getUsername());
        if (user.getNickname() != null) {
            mLUser.put("nickname", user.getNickname());
        }
        if (user.getIcon() != null) {
            mLUser.put("icon", user.getIcon());
        }
        MLUserManager.saveInBackground(mLUser, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

    /**
     * 添加地址信息(需要登录)
     *
     * @param address
     * @param callback
     */
    public void addAddress(final Address address, final OperationCallback callback) {
        Log.d("start addAddress","");
        final MLObject obj = new MLObject("Address");
        obj.put("user", MLUser.getCurrentUser());
        obj.put("name", address.getName());
        obj.put("street", address.getStreet());
        obj.put("tel", address.getTel());

        MLDataManager.saveInBackground(obj, new SaveCallback() {
            @Override
            public void done(MLException e) {
//                FFLog.d("addAddress e : " + e);
                if (e == null) {
                    address.setId(obj.getObjectId());
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });

    }

    /**
     * 通过 address id 删除地址信息
     *
     * @param address
     * @param callback
     */
    public void deleteAddress(Address address, final OperationCallback callback) {
        if (TextUtils.isEmpty(address.getId())) {
            callback.failed("address id must be set");
        }

        MLObject obj = new MLObject("Address");
        obj.setObjectId(address.getId());

        MLDataManager.deleteInBackground(obj, new DeleteCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });

    }

    /**
     * 通过 address id 更新地址信息
     *
     * @param address
     * @param callback
     */
    public void updateAddress(Address address, final OperationCallback callback) {
        if (TextUtils.isEmpty(address.getId())) {
            callback.failed("address id must be set");
        }

        MLObject obj = new MLObject("Address");
        obj.setObjectId(address.getId());
        obj.put("user", MLUser.getCurrentUser());
        obj.put("name", address.getName());
        obj.put("street", address.getStreet());
        obj.put("tel", address.getTel());

        MLDataManager.saveInBackground(obj, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

    /**
     * 创建订单
     *
     * @param order
     * @param callback
     */
    public void addOrder(final Order order, final OperationCallback callback) {
        final MLObject obj = new MLObject("Order");
        obj.put("total", order.getTotal());
        obj.put("delivery", order.getDelivery());
        obj.put("receipt_title", order.getReceiptType());
        obj.put("receipt_content", order.getReceiptContent());
        obj.put("receipt_info", order.getReceiptHeading());
        obj.put("remarks", order.getRemarks());
        obj.put("pay_method", order.getPayMethod());
        obj.put("order_status", order.getOrderStatus());

        // pointer: user, address
        obj.put("user", MLUser.getCurrentUser());

        MLObject address = MLObject.createWithoutData("Address", order.getAddress().getId());
        obj.put("address", address);

        // ArrayList<Point>
        final List<MLObject> orderProducts = new ArrayList<>();
        for (int i = 0; i < order.getOrderProducts().size(); i++) {
            final MLObject orderProduct = new MLObject("OrderProduct");
            MLObject product = MLObject.createWithoutData("Product", order.getOrderProducts().get(i)
                    .getProduct().getId());
            orderProduct.put("product", product);
            orderProduct.put("price", order.getOrderProducts().get(i).getPrice());
            orderProduct.put("quantity", order.getOrderProducts().get(i).getQuantity());
            orderProducts.add(orderProduct);
        }
        obj.put("order_products", orderProducts);
        MLDataManager.saveInBackground(obj, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
//                    FFLog.d("add order success");
                    order.setId(obj.getObjectId());
                    callback.success();
                } else {
                    callback.failed("=====order=====" + e.getMessage());
                }
            }
        });
    }

    /**
     * 更新订单状态, 仅支持订单状态更新, 不支持菜品更新
     *
     * @param order    订单状态包括: 1 - 订单处理中（待付款）; 2 - 待发货; 3 - 已发货; 4 - 已收货; 5 - 已评论; 6 - 用户取消订单; 7 - 商户取消订单
     * @param callback
     */
    public void updateOrder(Order order, final OperationCallback callback) {
        if (TextUtils.isEmpty(order.getId())) {
            callback.failed("address id must be set");
        }

        MLObject obj = MLObject.createWithoutData("Order", order.getId());

        obj.put("order_status", order.getOrderStatus());

        MLDataManager.saveInBackground(obj, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

    public void addFavorite(Product product, final OperationCallback callback) {
//        FFLog.d("start addFavorite");
        MLObject object = MLObject.createWithoutData("Product", product.getId());
        MLRelation relation = MLUser.getCurrentUser().getRelation("favorites");
        relation.add(object);
        getCurrentUser().setFavorites(relation);
        MLUserManager.saveInBackground(MLUser.getCurrentUser(), new SaveCallback() {
            @Override
            public void done(MLException e) {
//                FFLog.d("addFavorite e : " + e);
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });

    }

    public void removeFavorite(Product product, final OperationCallback callback) {
//        FFLog.d("start addFavorite");
        MLObject object = MLObject.createWithoutData("Product", product.getId());
        MLRelation relation = MLUser.getCurrentUser().getRelation("favorites");
        relation.remove(object);
        getCurrentUser().setFavorites(relation);
        MLUserManager.saveInBackground(MLUser.getCurrentUser(), new SaveCallback() {
            @Override
            public void done(MLException e) {
//                FFLog.d("addFavorite e : " + e);
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });

    }

    public void addComment(List<Comment> comments, final OperationCallback callback) {
//        FFLog.d("start addComment");
        List<MLObject> objects = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            MLObject comment = new MLObject("Comment");
            comment.put("score", comments.get(i).getScore());
            comment.put("content", comments.get(i).getContent());
            MLObject product = MLObject.createWithoutData("product", comments.get(i).getProduct().getId());
            comment.put("product", product);
            comment.put("user", MLUser.getCurrentUser());
            objects.add(comment);
        }
        MLDataManager.saveAllInBackground(objects, new SaveCallback() {
            @Override
            public void done(MLException e) {
//                FFLog.d("addComment e : " + e);
                if (e == null) {
                    callback.success();
                } else {
                    callback.failed(e.getMessage());
                }
            }
        });
    }

}
