package com.example.gpacalculator

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty


class MainActivity : AppCompatActivity() {

    private lateinit var layout: LinearLayout
    private lateinit var addButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        title = "GPA Calculator"
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.layout_list)
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener { addView() }  //adding a view each time the user clicks "Add" button


        val calculateButton: Button = findViewById(R.id.calculate_button)
        calculateButton.setOnClickListener {
            calculate()
        }

    }

    private fun calculate() {
        val gpa = getGPA()
        val etcs = getETCS()
        val length = gpa.size
        var total: Double = 0.0
        if (layout.isEmpty()) {     //checking if the user added at least one subject. if not, show an error message.
            Toast.makeText(this, "Please add a subject", Toast.LENGTH_SHORT).show()
        } else if (gpa.isNotEmpty() && etcs.isNotEmpty()) {     //  if the user has added the both values, do the calculations.
            for (i in 0 until length) {
                total += gpa[i] * etcs[i]
            }
        } else if (gpa.isEmpty() || etcs.isEmpty()) {   // if user has left at least one of the boxes empty, show an error message.
            Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show()
        }

        var a: Double = (total / etcs.sum())
        val result: TextView = findViewById(R.id.result)
        if (a.isNaN()) {
            a = 0.0
            result.text = a.toString()
        } else {
            var a = (total / etcs.sum()).toString()
            a = String.format("%.5s", a)
            result.text = a
        }
    }
    // getting GPA values from the each EditText in the layout and returning them as a list.
    private fun getGPA(): List<Double> {
        var listGpa = mutableListOf<Double>()
        val size = layout.childCount
        for (i in 0 until size) {
            val view: View? = layout.getChildAt(i)
            val text: EditText? = view?.findViewById(R.id.gpa_points)
            text?.text.toString().toDoubleOrNull()?.let { listGpa.add(it) }
        }
        return listGpa
    }
    // getting credits from the each EditText in the layout and returning them as a list.
    private fun getETCS(): List<Double> {
        var listCredit = mutableListOf<Double>()
        val size = layout.childCount
        for (i in 0 until size) {
            val view: View? = layout.getChildAt(i)
            val text: EditText? = view?.findViewById(R.id.credit_points)
            text?.text.toString().toDoubleOrNull()?.let { listCredit.add(it) }
        }
        return listCredit
    }

    // removing the view on the click of the close icon.
    fun OnDelete(view: View) {
        layout.removeView(view.parent as View)
    }

    //adding a view each time the user clicks "Add" button
    private fun addView() {
        val gpaView: View = layoutInflater.inflate(R.layout.new_layout, null, false)

        if (layout.childCount < 10) { //setting maximum limit for the subject count.
            layout.addView(gpaView)
        } else { // showing a message when the user tries to add more than 10.
            Toast.makeText(this, "You cannot add more than 10 subjects!", Toast.LENGTH_SHORT).show()
        }


    }
}
