package net.sarazan.statement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import butterknife.bindView

/**
 * Created by Aaron Sarazan on 7/22/15
 * Copyright(c) 2015
 */
public class TextActivity : AppCompatActivity() {

    private val content: View by bindView(R.id.content)
    private val editText: EditText by bindView(android.R.id.edit)
    private val done: View by bindView(R.id.done)

    private var layout = false

    private var index: Int? = null
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        index = getIntent().getIntExtra("index", -1)
        item = getIntent().getSerializableExtra("item") as Item

        content.getViewTreeObserver().addOnGlobalLayoutListener {
            if (!layout) {
                layout = true
                content.setVisibility(View.VISIBLE)
                ViewAnimationUtils.createCircularReveal(content, content.getWidth(), content.getHeight(), 0f, content.getHeight() / 2f).start()
            }
        }

        editText.setText(item!!.text)
        editText.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    done()
                    return true;
                }
                return false;
            }
        })

        done.setOnClickListener {
            done()
        }
    }

    private fun done() {
        val intent = Intent()
        intent.putExtra("index", index!!)
        if (editText.getText().toString().isNotEmpty()) {
            intent.putExtra("item", Item(editText.getText().toString()))
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}