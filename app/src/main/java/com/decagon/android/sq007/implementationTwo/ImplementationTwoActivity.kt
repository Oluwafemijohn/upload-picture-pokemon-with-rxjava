package com.decagon.android.sq007.implementationTwo

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.decagon.android.sq007.databinding.ActivityImplementationTwoBinding
import com.decagon.android.sq007.implementationTwo.myApi.PokemonRetrofit
import com.decagon.android.sq007.implementationTwo.myApi.UploadRes
import com.decagon.android.sq007.implementationTwo.myApi.snackBar
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ImplementationTwoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var upload: Button
    private lateinit var layoutContainer: ConstraintLayout
    private var selectedImage: Uri? = null
    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val READ_IMAGE_STORAGE = 102
        private const val NAME = "FemiApp"
    }

    private lateinit var binding: ActivityImplementationTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplementationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageView = binding.imageHolder
        pickImage = binding.selectImageFromGalerry
        upload = binding.uploadThePicture
        layoutContainer = binding.layoutContainer

        binding.progressBar.max = 100
        val currentProgres = 70
        ObjectAnimator.ofInt(binding.progressBar, "progress", currentProgres)
            .setDuration(2000)
            .start()

        pickImage.setOnClickListener {
            READ_EXTERNAL_STORAGE.checkForPermission(NAME, READ_IMAGE_STORAGE)
        }

        upload.setOnClickListener {
            uploadImage()
        }
    }

    private fun String.checkForPermission(name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(this@ImplementationTwoActivity, this) == PackageManager.PERMISSION_GRANTED -> {
                    // call read contact function
                    openImageChooser()
                }
                shouldShowRequestPermissionRationale(this) -> showDialog(this, name, requestCode)
                else -> requestPermissions(this@ImplementationTwoActivity, arrayOf(this), requestCode)
            }
        }
    }

    // check for permission and make call
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
                openImageChooser()
            }
        }
        when (requestCode) {
            READ_IMAGE_STORAGE -> innerCheck(NAME)
        }
    }

    // Show dialog for permission dialog
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        // Alert dialog box
        val builder = AlertDialog.Builder(this)
        builder.apply {
            // setting alert properties
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("Ok") { _, _ ->
                requestPermissions(
                    this@ImplementationTwoActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    // Upload image
    private fun uploadImage() {
        if (selectedImage == null) {
            layoutContainer.snackBar("Select an image")
            return
        }
        // Getting the file name
        val file = File(FileUtil.getPath(selectedImage!!, this)!!)
        // Getting the file part
        val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multiPartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        // Handling
        PokemonRetrofit.getUploadApi().uploadImage(multiPartBody).enqueue(object :
                Callback<UploadRes> {
                // Responses
                override fun onResponse(call: Call<UploadRes>, response: Response<UploadRes>) {
                    if (response.isSuccessful) {
                        response.body()?.let { layoutContainer.snackBar(it.message) }
                    }
                }
                override fun onFailure(call: Call<UploadRes>, t: Throwable) {
                    t.message?.let { layoutContainer.snackBar(it) }
                }
            })
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_CODE_IMAGE_PICKER -> {
                        selectedImage = data?.data
                        imageView.setImageURI(selectedImage)
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
