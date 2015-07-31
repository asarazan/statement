package net.sarazan.statement

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import io.realm.RealmResults
import net.sarazan.statement.data.Item
import net.sarazan.statement.data.ItemViewHolder

public class StatementAdapter(val activity: Activity, results: RealmResults<Item>) : RealmRecyclerViewAdapter<Item, ItemViewHolder>(activity, results, true) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position);
        holder.textView.setText(item.getText())
        holder.itemView.setOnClickListener {
            show(item)
        }
    }

    private fun show(item: Item) {
        val i = TextActivity.get(activity, item.getId(), item.getText())
        activity.startActivityForResult(i, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder? {
        return ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statement, parent, false))
    }
}