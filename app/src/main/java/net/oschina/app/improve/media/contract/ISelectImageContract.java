package net.oschina.app.improve.media.contract;

/**
 * 图片选择器建立契约关系，将权限操作放在Activity，具体数据放在Fragment
 * Created by huanghaibin_dev
 * on 2016/7/15.
 */
public interface ISelectImageContract {
    interface Operator {
        void requestCamera();

        void requestExternalStorage();
    }

    interface View {
        void onSelectComplete();

        void onOpenCameraSuccess();

        void onReadExternalStorageSuccess();

        void onCameraPermissionDenied();

        void onExternalStorageDenied();
    }
}
