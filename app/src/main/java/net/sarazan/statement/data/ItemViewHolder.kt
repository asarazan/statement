package net.sarazan.statement.data

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.Bindable

/**
 * Created by Aaron Sarazan on 7/23/15
 */
public class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Bindable {
    override val bindView: View? = itemView

    public val textView: TextView by bindView(android.R.id.text1)

}