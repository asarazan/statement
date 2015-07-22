package net.sarazan.statement

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.levelmoney.velodrome.Velodrome
import com.levelmoney.velodrome.annotations.OnActivityResult

/**
 * Created by Aaron Sarazan on 7/21/15
 * Copyright(c) 2015 Level, Inc.
 */
public class MainActivity() : AppCompatActivity() {

    private val fab: FloatingActionButton by bindView(R.id.fab)
    private val recycler: RecyclerView by bindView(R.id.recycler)

    private val items: MutableList<Item> = arrayListOf(Item("Testing"), Item("Sup."))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            show(items.size(), Item(null))
        }

        recycler.setLayoutManager(LinearLayoutManager(this))
        recycler.setAdapter(object: RecyclerView.Adapter<ViewHolder>() {
            override fun getItemCount(): Int = items.size()

            override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
                val item = items[p1]
                p0.text.setText(item.text)
                p0.itemView.setOnClickListener {
                    show(p1, item)
                }
            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder? {
                return ViewHolder(LayoutInflater.from(p0.getContext()).inflate(R.layout.item_statement, p0, false))
            }
        })
    }

    private fun show(index: Int, item: Item) {
        val i = Intent(this, javaClass<TextActivity>())
        i.putExtra("index", index)
        i.putExtra("item", item)
        startActivityForResult(i, 0)
        overridePendingTransition(0, 0)
    }

    OnActivityResult(0)
    public fun onResult(data: Intent) {
        val item = data.getSerializableExtra("item") as Item?
        val index = data.getIntExtra("index", -1)
        when {
            item == null -> items.remove(index)
            index == items.size() -> items.add(item)
            else -> items[index] = item
        }
        recycler.getAdapter().notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Velodrome.handleResult(this, requestCode, resultCode, data)
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val text: TextView by bindView(android.R.id.text1)
    }

}