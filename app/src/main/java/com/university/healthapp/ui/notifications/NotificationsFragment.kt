package com.university.healthapp.ui.notifications

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.university.healthapp.databinding.FoodBinding
import com.university.healthapp.databinding.ListOfMeasurementsBinding
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class NotificationsFragment : Fragment() {

    private var _binding: ListOfMeasurementsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        // POŁĄCZENIE KLASY Z WIDOKIEM
        _binding = ListOfMeasurementsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // ZAPYTANIE DO API
        print("GET DATA")
        getData();

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    // POBRANIE DANYCH DO API
    private fun getData(){
        // WYŁĄCZENIE ZABEZPIECZEŃ
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val objectMapper = ObjectMapper()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        // POBRANIE DANYCH O POMIARACH
        val restTemplate = RestTemplate()
        val rest: ResponseEntity<String> = restTemplate.exchange<String>(
            "https://app1.takemewith.pl/clients",
            HttpMethod.GET,
            HttpEntity<Any?>(headers),
            String::class.java
        )
        println("DANE ZOSTAŁY POBRANE")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}