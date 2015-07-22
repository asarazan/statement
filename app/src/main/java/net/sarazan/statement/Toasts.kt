package net.sarazan.statement

import android.content.Context
import android.widget.Toast

/**
 * Created by Aaron Sarazan on 7/21/15
 * Copyright(c) 2015 Level, Inc.
 */
public object Toasts {

    public fun show(c: Context, string: Int) {
        Toast.makeText(c, string, Toast.LENGTH_LONG).show()
    }

    public fun unimplemented(c: Context) {
        show(c, R.string.error_unimplemented)
    }
}