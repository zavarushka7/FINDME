package com.bignerdranch.android.findme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class SettingsProfile : AppCompatActivity() {

    private lateinit var horseButton: ImageButton
    private lateinit var bearButton: ImageButton
    private lateinit var rabbitButton: ImageButton
    private lateinit var sheepButton: ImageButton
    private lateinit var owlButton: ImageButton
    private lateinit var birdButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_profile)

        horseButton = findViewById(R.id.horse_button)
        bearButton = findViewById(R.id.bear_button)
        rabbitButton = findViewById(R.id.rabbit_button)
        sheepButton = findViewById(R.id.sheep_button)
        owlButton = findViewById(R.id.owl_button)
        birdButton = findViewById(R.id.bird_button)

        horseButton.setOnClickListener { view: View ->

        }
        bearButton.setOnClickListener { view: View ->

        }
        rabbitButton.setOnClickListener { view: View ->

        }
        sheepButton.setOnClickListener { view: View ->

        }
        owlButton.setOnClickListener { view: View ->

        }
        birdButton.setOnClickListener { view: View ->

        }
    }
}