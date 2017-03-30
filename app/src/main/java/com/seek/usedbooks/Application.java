package com.seek.usedbooks;

import com.maxleap.MLUser;
import com.maxleap.MaxLeap;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class Application extends android.app.Application {

    public static final String APP_ID = "588180f487d4ed1d5def8585";
    public static final String API_KEY = "YnFTb1VJVDdNR3BGeVd5cV9xd0tGQQ";


    @Override
    public void onCreate() {
        super.onCreate();
        MaxLeap.Options options = new MaxLeap.Options();
        options.applicationID = APP_ID;
        options.restAPIKey = API_KEY;
        //匿名用户是指提供用户名和密码，系统为您创建的一类特殊用户，它享有其他用户具备的相同功能。
        // 不过，一旦注销，匿名用户的所有数据都将无法访问。如果您的应用需要使用一个相对弱化的用户系统时，您可以考虑 MaxLeap 提供的匿名用户系统来实现您的功能。
        options.enableAnonymousUser = true;
        options.serverRegion = MaxLeap.REGION_CN;
        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_VERBOSE);//设置log输出的等级
        MLUser.registerSubclass(LoginUser.class);
        MaxLeap.initialize(this, options);

        initImageLoader();
        //检查是否成功连接服务器,正式环境可移除
        MaxLeap.checkSDKConnection();

    }

    private void initImageLoader() {

        ImageLoaderConfiguration mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)

                .diskCache(new UnlimitedDiskCache(getExternalCacheDir()))
                .diskCacheExtraOptions(480, 800, null)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(500)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())

                .memoryCacheExtraOptions(480, 800)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(8 * 1024 * 1024)

                .threadPoolSize(5)

                .threadPriority(Thread.NORM_PRIORITY - 2)

                .denyCacheImageMultipleSizesInMemory()

                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())

                .imageDownloader(new BaseImageDownloader(this, 10 * 1000, 60 * 1000))

                .imageDecoder(new BaseImageDecoder(false))

                .build();

        ImageLoader.getInstance().init(mImageLoaderConfiguration);// 全局初始化此配置

    }
}
