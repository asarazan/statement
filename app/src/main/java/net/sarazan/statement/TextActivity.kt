package net.sarazan.statement

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import butterknife.bindView
import net.sarazan.statement.data.Item

public class TextActivity : AppCompatActivity() {

    companion object {

        public fun get(c: Context, id: Long?, text: String?): Intent {
            val retval = Intent(c, javaClass<TextActivity>())
            if (id != null) {
                retval.putExtra("id", id)
            }
            if (text != null) {
                retval.putExtra("text", text)
            }
            return retval
        }

        public fun getIdExtra(i: Intent): Long? {
            return if (i.hasExtra("id")) i.getLongExtra("id", -1) else null
        }

        public fun getTextExtra(i: Intent): String? {
            return i.getStringExtra("text")
        }
    }

    private val editText: EditText by bindView(android.R.id.edit)

    private var start: String? = null
    private val delete: View by bindView(R.id.delete)
    private val done: View by bindView(R.id.done)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        start = getIntent().getStringExtra("text")
        editText.setText(start)
        editText.setSelection(editText.length())
        editText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
            true
        }
        editText.setOnKeyListener { view, i, keyEvent ->
            updateVisibility()
            false
        }

        delete.setVisibility(if (getIntent().hasExtra("id")) View.VISIBLE else View.GONE)

        done.setOnClickListener { done() }
        delete.setOnClickListener { delete() }
        updateVisibility()
    }

    private fun updateVisibility() {
        done.setVisibility(if (start?.equals(editText.getText().toString())?:false) View.GONE else View.VISIBLE)
    }

    private fun delete() {
        val result = Intent()
        if (getIntent().hasExtra("id")) {
            result.putExtra("id", getIntent().getLongExtra("id", -1L))
        }
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun done() {
        val result = Intent()
        val text = editText.getText().toString()
        if (getIntent().hasExtra("id")) {
            result.putExtra("id", getIntent().getLongExtra("id", -1L))
        }
        if (text.isNotEmpty()) {
            result.putExtra("text", text)
        }
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}