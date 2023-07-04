package com.university.healthapp

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.university.healthapp.databinding.MeasurementBinding
import org.springframework.web.client.RestTemplate



// KLASA REPREZENTUJĄCA POMIAR
class Measurement : AppCompatActivity() {


    private lateinit var binding: MeasurementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        println("START MEASUREMENT")
        binding = MeasurementBinding.inflate(layoutInflater)

        setContentView(binding.root)



        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        setContentView(binding.root)
        // Setup a click listener on the calculate button to calculate the tip

    }


    private fun calculateTip() {

        var weight = binding.costOfServiceEditText.text.toString();
        var pressure = binding.costOfServiceEditText1.text.toString();
        var status = binding.tipOptions.checkedRadioButtonId-2131231060;
        var woman = binding.roundUpSwitch.isChecked;




        val dietType : DietType

        if(binding.costOfServiceEditText.text.toString().toInt() > 80
            || binding.costOfServiceEditText1.text.toString().toInt() > 120)
            dietType = DietType.EXTRA_HEALTHY
        else if (binding.costOfServiceEditText.text.toString().toInt() < 80 &&
            binding.costOfServiceEditText1.text.toString().toInt() < 120 &&
            binding.tipOptions.checkedRadioButtonId-2131231060 == 0)
            dietType = DietType.HEALTHY
        else
            dietType = DietType.NORMAL

        // save data
        displayTip(dietType)

    }



    private fun displayTip(dietType: DietType) {
        binding.tipResult.text = "Diet type is: " + dietType.toString()
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