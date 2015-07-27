package butterknife

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.view.View
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

public interface Bindable {
    companion object {
        private val TAG = javaClass<Bindable>().getSimpleName()
    }

    val bindView: View?
    public fun <T: View?> bindView(id: Int): ReadOnlyProperty<Any, T> = _ViewBinding(id, bindView)

}


// Copied from KotterKnife
private class _ViewBinding<T : View>(val id: Int, val view: View?) : ReadOnlyProperty<Any, T> {
    private val lazy = Lazy<T>()
    override fun get(thisRef: Any, desc: PropertyMetadata): T = lazy.get {
        _findView<T>(view, id)
                ?: throw IllegalStateException("View ID $id for '${desc.name}' not found.")
    }
}

private fun _findView<T : View>(thisRef: Any?, id: Int): T? {
    @suppress("UNCHECKED_CAST")
    return when (thisRef) {
        null -> null
        is View -> thisRef.findViewById(id)
        is Activity -> thisRef.findViewById(id)
        is Dialog -> thisRef.findViewById(id)
        is Fragment -> thisRef.getView().findViewById(id)
        is Fragment -> thisRef.getView().findViewById(id)
        else -> throw IllegalStateException("Unable to find views on type.")
    } as T?
}