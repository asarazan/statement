package net.sarazan.statement

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView

/**
 * Created by Aaron Sarazan on 7/21/15
 * Copyright(c) 2015 Level, Inc.
 */
public class MainActivity() : AppCompatActivity() {

    private val fab: FloatingActionButton by bindView(R.id.fab)
    private val recycler: RecyclerView by bindView(R.id.recycler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startActivity(Intent(this, javaClass<TextActivity>()))
            overridePendingTransition(0, 0)
        }

        recycler.setLayoutManager(LinearLayoutManager(this))
        recycler.setAdapter(object: RecyclerView.Adapter<ViewHolder>() {
            override fun getItemCount(): Int = 100

            override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
                p0.text.setText("Well hello there, I'm just making a little statement.")
            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder? {
                return ViewHolder(LayoutInflater.from(p0.getContext()).inflate(R.layout.item_statement, p0, false))
            }
        })
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val text: TextView by bindView(android.R.id.text1)
    }

}