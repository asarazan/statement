package net.sarazan.statement

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
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
    private var layout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        content.getViewTreeObserver().addOnGlobalLayoutListener {
            if (!layout) {
                layout = true
                content.setVisibility(View.VISIBLE)
                ViewAnimationUtils.createCircularReveal(content, content.getWidth(), content.getHeight(), 0f, content.getHeight() / 2f).start()
            }
        }
    }

}