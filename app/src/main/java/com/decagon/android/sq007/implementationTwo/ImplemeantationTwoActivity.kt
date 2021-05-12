package com.decagon.android.sq007.implementationTwo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.databinding.ActivityImplemeantationTwoBinding

class ImplemeantationTwoActivity : AppCompatActivity() {

    var config: HashMap<String, String> = HashMap()
    private lateinit var binding: ActivityImplemeantationTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplemeantationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        config.put("cloud_name", "your cloud name")
//        config.put("api_key", "your API Key")
//        config.put("api_secret", "your API secret")
//        MediaManager.init(this, config)
//
//        ImagePicker.with(this)
//            .crop()                    //Crop image(Optional), Check Customization for more option
//            .compress(1024)            //Final image size will be less than 1 MB(Optional)
//            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//            .start()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            //Image Uri will not be null for RESULT_OK
//            val fileUri = data?.data
//
//            imagev.setImageURI(fileUri)
//
//            //You can get File object from intent
//            val file: File = ImagePicker.getFile(data)!!
//
//            //You can also get File Path from intent
//            val filePath: String = ImagePicker.getFilePath(data)!!
//            imgpath = filePath
//            uploadToCloudinary(filePath)
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
//        }
//    }
//    fun uploadToCloudinary(filepath: String) {
//        MediaManager.get().upload(filepath).unsigned("kks8dyht").callback(object : UploadCallback {
//            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
//                Toast.makeText(applicationContext, "Task successful", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
//
//            }
//
//            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
//
//            }
//
//            override fun onError(requestId: String?, error: ErrorInfo?) {
//
//                Toast.makeText(applicationContext, "Task Not successful"+ error, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onStart(requestId: String?) {
//
//                Toast.makeText(applicationContext, "Start", Toast.LENGTH_SHORT).show()
//            }
//        }).dispatch()
//    }
}
