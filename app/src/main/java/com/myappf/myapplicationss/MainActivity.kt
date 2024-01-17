package com.myappf.myapplicationss


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*
import kotlin.concurrent.thread


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var myts: TextToSpeech? = null


    override fun onBackPressed() {
        val bi = AlertDialog.Builder(this)
        bi.setTitle("close Ask iT!")
        bi.setMessage("Do you really want to close the app?")
        bi.setPositiveButton("YES",
            { dialogInterface: DialogInterface, i: Int -> finishAffinity() })
        bi.setNegativeButton("NO", { dialogInterface: DialogInterface, i: Int -> })
        bi.show()


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val fs = prefs.getBoolean("firstStart", true)
        if (fs) {
            cli()
        }





        myts = TextToSpeech(this, this)

        val ser = findViewById<Button>(R.id.search)
        var txt = findViewById<TextView>(R.id.info)
        val ext = findViewById<EditText>(R.id.enter)
        val cl = findViewById<Button>(R.id.clearall)
        val bt = findViewById<Button>(R.id.read)
        val jb = findViewById<Button>(R.id.stop)
        var po:String = ""
        var sent: String = ""
        var lik:String = ""
        val dk = findViewById<Switch>(R.id.dkmode)
        val sk = findViewById<SeekBar>(R.id.seek)
        val lu = findViewById<ConstraintLayout>(R.id.lay)


        val si = findViewById<ScrollView>(R.id.sview)
        ext.setTextColor(Color.parseColor("#000000"))

        fun inf(uk: Elements, bk: Elements, nn: String): String? {

            var f:String? = ""
            var bj:Boolean=false

            for (i in uk){



                if (i.text().length < 2){
                    continue
                }
                if (bk.text() in i.text()){
                    continue

                }
                else{
                    if(i.text().split(" ").size < 10) {
                        continue

                    }else{
                        f += i.text()
                        break
                    }

                }


            }
            if (f.toString().length<2) {
                for (j in uk){
                    if (j.text().isNotEmpty()){
                        f+=j.text()
                        break

                    }else{
                        continue
                    }

                }

            }

            if (f != null) {
                if(f.split(" ").size<=5) {
                    return "Sorry. We'll try to add the information of that in the upcoming versions."

                }

            }

            return f.toString()


        }





        fun  par(some: String):String?{
            var chk:String=""
            var po:List<String> = some.split(" ")
            println(po)
            for( i in po ){
                if ("[" in i){
                    chk+=i.split("[")[0] + " "
                }else{
                    chk+= "$i "
                }

            }


            return chk


        }

        sk.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (txt.text != null) {
                    txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())


                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                txt.setTextIsSelectable(true)


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                txt.setTextIsSelectable(true)


            }

        })




        bt.setOnClickListener {
            if(txt.text.isEmpty()){
                Toast.makeText(
                    this,
                    "Please search for something and then click this",
                    Toast.LENGTH_LONG
                ).show()
            }else{

                myts!!.setSpeechRate(0.8F)
                myts!!.speak(txt.text.toString(), TextToSpeech.QUEUE_FLUSH, null, " ")
            }
        }

        jb.setOnClickListener {
            myts!!.stop()

        }



        fun sckr(ukl: String): Unit? {

            thread {
                try {
                    val wb = Jsoup.connect(ukl).get()
                    var ino = wb.getElementsByTag("p")
                    var dv = wb.getElementById("mw-content-text")
                    var tjd = dv.getElementsByTag("td")
                    var pp = tjd.select("p")
                    var infor = inf(ino, pp, sent)
                    var ktt = infor?.let { par(it) }





                    this.runOnUiThread {


                        Toast.makeText(this, "Here it is.", Toast.LENGTH_SHORT).show()
                        txt.text = ktt.toString()


                    }


                } catch (e: IOException) {
                    txt.text = "SORRY THERE WAS A PROBLEM WITH THE CONNECTION...TRY CLOSING AND OPENING THE APP AGAIN"

                }


            }
            return null

        }


        fun blackout(jk:String): Unit? {
            txt.text ="Sorry .We are unable to find information about  $jk"
            return null
        }





        fun crape(ent: String): String {

            var mn:String = ""
            thread {
                try {
                    var bik: String = ent
                    val webb = Jsoup.connect("https://www.google.com/search?q=  $bik  wiki").get()
                    var infoi = webb.getElementsByTag("a")

                    for(i in 1.until(infoi.size)) {
                        var ok = infoi[i].absUrl("href")
                        if ("wikipedia" in ok) {
                            mn += "$ok"
                            break
                        }





                    }
                    if(mn.isBlank() || mn.isEmpty()) {
                        blackout(ent)

                    }else{
                        sckr(mn)
                    }





                } catch (e: IOException) {

                    txt.text = "SORRY THERE WAS A PROBLEM WITH THE CONNECTION...TRY CLOSING AND OPENING THE APP AGAIN"

                }



            }

            return mn

        }





        dk.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if(onSwitch){


                lu.setBackgroundResource(R.drawable.zep)
                dk.text = "Lightmode"
                dk.setTextColor(Color.parseColor("#FFFFFF"))
                dk.setBackgroundColor(Color.parseColor("#000000"))
                ext.setTextColor(Color.parseColor("#FFFFFF"))
                ext.setHintTextColor(Color.parseColor("#FFFFFF"))
                ext.setLinkTextColor(Color.parseColor("#FFFFFF"))
                txt.setTextColor(Color.parseColor("#FFFFFF"))





                Toast.makeText(this, "SWITCHED TO DARK MODE", Toast.LENGTH_SHORT).show()
            }else{
                lu.setBackgroundResource(R.drawable.aass)

                dk.text = "Darkmode"
                dk.setBackgroundColor(Color.parseColor("#FFEB3B"))
                dk.setTextColor(Color.parseColor("#0B0A0A"))
                ext.setTextColor(Color.parseColor("#000000"))
                ext.setHintTextColor(Color.parseColor("#000000"))
                ext.setLinkTextColor(Color.parseColor("#000000"))
                txt.setTextColor(Color.parseColor("#000000"))


                Toast.makeText(this, "Switched To Light Mode", Toast.LENGTH_SHORT).show()

            }

        }

        cl.setOnClickListener {
            txt.text=""
            Toast.makeText(this, "Cleared everything", Toast.LENGTH_SHORT).show()
            myts!!.stop()

        }







        ser.setOnClickListener{


            if (ext.text.isEmpty()){
                Toast.makeText(this, "Please Enter A Valid Text", Toast.LENGTH_SHORT).show()
            }
            else {
                try {


                    val inp = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inp.hideSoftInputFromWindow(
                            this.currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                    )



                    Toast.makeText(this, "Loading.Please Wait.", Toast.LENGTH_SHORT).show()
                    txt.text = "Loading...Loading time is based on your internet speed.."
                    sent += ext.text.toString()
                    ext.text.clear()
                    crape(sent.toLowerCase())
                    sent = ""
                    txt.setTextIsSelectable(true)
                }catch (e: Exception){
                    txt.text="if you dont get your results for the first time just close the app and open it again..il'll work fine "
                }


            }



        }






    }




    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val lang = myts!!.setLanguage(Locale.US)
        }else{
            Toast.makeText(this, "Sorry There Was A Problem", Toast.LENGTH_LONG)
        }



    }

    private fun cli(){
        val textView = TextView(this)
        textView.text = "DESCRIPTION"
        textView.setPadding(10, 20, 10, 20)
        textView.textSize = 20F
        textView.setBackgroundColor(Color.GREEN)
        textView.setTextColor(Color.parseColor("#000000"))
        val ci = AlertDialog.Builder(this)
        ci.setCustomTitle(textView)
        ci.setMessage("KNOW QUICK DEFINITIONS OF AN  UNKNOWN WORD IN  SHORT AMOUNT OF TIME. JUST TYPE THE WORD THAT YOU WANT TO KNOW AND YOU WILL GET YOUR RESULTS. KIND ATTENTIONS:IT'S NOT AN ENGLISH DICTIONARY. TRY NOT TO FIND ENGLISH MEANINGS")
        ci.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int -> })
        ci.show()
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()

    }

    public override fun onDestroy() {
        if (myts!=null){

            myts!!.stop()
            myts!!.shutdown()
        }

        super.onDestroy()
    }

}
