package com.nut2014.newtech.compress;

import com.nut2014.baselibrary.base.BaseMvpView;

/**
 * @author feiltel 2020/4/3 0003
 */
public interface CompressView extends BaseMvpView {
    void setLogInfo(String msg);
    void startCompress();
    void endCompress();
    void compressProgress(int progress);
}
