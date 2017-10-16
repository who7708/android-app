package net.oschina.app.improve.tweet.share;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.bean.Tweet;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 动弹分享
 * Created by huanghaibin on 2017/10/16.
 */

public class TweetShareActivity extends BackActivity implements
        EasyPermissions.PermissionCallbacks,
        View.OnClickListener,
        Runnable {

    private static final int TYPE_SHARE = 1;
    private static final int TYPE_SAVE = 2;

    public static void show(Context context, Tweet tweet) {
        Intent intent = new Intent(context, TweetShareActivity.class);
        intent.putExtra("tweet", tweet);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tweet_detail;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void run() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
