import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.bignerdranch.android.findme.R
import com.google.firebase.database.DatabaseReference


class Wait : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    val gamecode = intent.getStringExtra("key2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)


    }
}
