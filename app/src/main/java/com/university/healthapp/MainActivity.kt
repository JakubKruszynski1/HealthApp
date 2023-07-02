package com.university.healthapp

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.university.healthapp.databinding.ActivityMainBinding
import com.university.healthapp.databinding.MeasurementBinding
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var measurementBinding: MeasurementBinding

    private var nav : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        measurementBinding = MeasurementBinding.inflate(layoutInflater)

        println("START ActivityMainBinding")
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        measurementBinding.calculateButton.setOnClickListener { calculateTip() }

        measurementBinding.calculateButton1.setOnClickListener{ changeView()}

        // Set up a key listener on the EditText field to listen for "enter" button presses
        measurementBinding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        setContentView(measurementBinding.root)
    }


    private fun changeView() {
        if(nav === false){
            setContentView(binding.root)
        }
        else{
            setContentView(measurementBinding.root)
        }
    }

    private fun calculateTip() {

        println(measurementBinding.costOfServiceEditText.text.toString())
        println(measurementBinding.costOfServiceEditText1.text.toString())
        println(measurementBinding.tipOptions.checkedRadioButtonId-2131231060)
        println(measurementBinding.roundUpSwitch.isChecked)

        val dietType : DietType

        if(measurementBinding.costOfServiceEditText.text.toString().toInt() > 80
            || measurementBinding.costOfServiceEditText1.text.toString().toInt() > 120)
            dietType = DietType.EXTRA_HEALTHY
        else if (measurementBinding.costOfServiceEditText.text.toString().toInt() < 80 &&
            measurementBinding.costOfServiceEditText1.text.toString().toInt() < 120 &&
            measurementBinding.tipOptions.checkedRadioButtonId-2131231060 == 0)
            dietType = DietType.HEALTHY
        else
            dietType = DietType.NORMAL

        displayTip(dietType)
    }



    private fun displayTip(dietType: DietType) {
        measurementBinding.tipResult.text = "Diet type is: " + dietType.toString()
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}