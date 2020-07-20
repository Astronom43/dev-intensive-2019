package ru.skillbranch.devintensive




import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardClosed
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {


    lateinit var benderImage: ImageView
    lateinit var textTxt:TextView
    lateinit var messageEt:EditText
    lateinit var sendBtn:ImageView
    lateinit var benderObj:Bender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("M_MainActivity", "onCreate")
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        messageEt.setOnEditorActionListener(this)
        sendBtn = iv_send
        sendBtn.setOnClickListener(this)
        val status = savedInstanceState?.getString("STATUS")?:Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION")?:Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question))
        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTxt.text=benderObj.askQuestion()
    }



    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send){
            Log.d("M_MainActivity",this.isKeyboardClosed().toString())
            benderWork()
        }

    }

    private fun benderWork() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
        messageEt.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("STATUS",benderObj.status.name)
        outState.putString("QUESTION",benderObj.question.name)
        Log.d("M_MainActivity","onSaveInstanceState ${benderObj.status.name}")
    }

    override fun onEditorAction(tv: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1==EditorInfo.IME_ACTION_DONE){
            this.hideKeyboard()
            benderWork()
         //   workBender()
            return true
        }
        return false
    }
}
