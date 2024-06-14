package com.android.trashub

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.android.trashub.databinding.ActivityInputBinding
import com.google.android.material.button.MaterialButton

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLanjut: MaterialButton = binding.btnLanjut
        val editTextName = binding.nameLayout.editText

        // Initially disable the button
        btnLanjut.isEnabled = false

        // Add a TextWatcher to the EditText
        editTextName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Enable the button only if there is text in the EditText
                btnLanjut.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnLanjut.setOnClickListener {
            val name = editTextName?.text.toString()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("user_name", name)
            }
            startActivity(intent)
        }
    }
}
