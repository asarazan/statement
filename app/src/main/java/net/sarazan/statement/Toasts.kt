package net.sarazan.statement

import android.content.Context
import android.widget.Toast

public object Toasts {

    public fun show(c: Context, string: Int) {
        Toast.makeText(c, string, Toast.LENGTH_LONG).show()
    }

    public fun unimplemented(c: Context) {
        show(c, R.string.error_unimplemented)
    }
}