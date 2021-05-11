package com.decagon.android.sq007.implementationTwo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.databinding.ActivityImplemeantationTwoBinding

class ImplemeantationTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImplemeantationTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplemeantationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    override fun
}
