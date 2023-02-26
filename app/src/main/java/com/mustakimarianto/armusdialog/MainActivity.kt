package com.mustakimarianto.armusdialog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mustakimarianto.armusdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ArmusDialogListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var armusDialog: ArmusDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize dialog
        armusDialog = ArmusDialog(
            ArmusDialog.DefaultDialog(
                context = this,
                layoutInflater = layoutInflater,
                title = "Hello World!",
                message = "Hi, how are you?",
                positiveBtn = "Yess",
                negativeBtn = "Noo",
                width = ArmusDialogParams.MATCH_PARENT,
                listener = this
            )
        )

        // When button clicked show dialog
        binding.btnMainDialog.setOnClickListener {
            armusDialog.show()
        }
    }

    override fun onPositiveButtonClicked() {
        Toast.makeText(this, "YESS!", Toast.LENGTH_SHORT).show()
    }

    override fun onNegativeButtonClicked() {
        Toast.makeText(this, "NOO!", Toast.LENGTH_SHORT).show()
    }
}