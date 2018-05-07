package com.unilab.gmp.utility;

/**
 * Created by c_rcmiguel on 5/2/2018.
 */

public interface AppBarManager {
    void collapseAppBar();
    void expandAppBar();
    int getVisibleHeightForRecyclerViewInPx();
}
