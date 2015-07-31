package net.sarazan.statement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.bindView
import io.realm.Realm
import net.sarazan.statement.data.Item
import kotlin.properties

public class MainActivity() : AppCompatActivity() {

    private val fab: FloatingActionButton by bindView(R.id.fab)
    private val recycler: RecyclerView by bindView(R.id.recycler)

    private val adapter: StatementAdapter?
        get() = recycler.getAdapter() as StatementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            show(null)
        }

        recycler.setLayoutManager(LinearLayoutManager(this))
        val realm = Realm.getInstance(this)
        val results = realm.allObjects(javaClass<Item>())
        results.sort("id")
        recycler.setAdapter(StatementAdapter(this, results))
    }

    private fun show(item: Item?) {
        val intent = TextActivity.get(this, item?.getId(), item?.getText())
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val realm = adapter!!.getRealm()
            val id = TextActivity.getIdExtra(data!!)
            val text = TextActivity.getTextExtra(data)
            realm.beginTransaction()
            when {
                id == null      -> realm.copyToRealm(Item.create(text))
                text == null    -> Item.withId(realm, id)?.removeFromRealm()
                else            -> Item.withId(realm, id)?.setText(text)
            }
            realm.commitTransaction()
            recycler.getAdapter().notifyDataSetChanged()
        }
    }
}