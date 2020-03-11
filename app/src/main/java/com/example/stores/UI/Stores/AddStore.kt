package com.example.stores.UI.Stores

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import com.example.myapplication.Services.ApiClient
import com.example.myapplication.Services.StoreApi
import com.example.stores.Model.Stores.Data
import com.example.stores.R
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.add_store.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStore :AppCompatActivity() {

//    companion object {
//        //image pick code
//        private val IMAGE_PICK_CODE = 1000;
//        //Permission code
//        private val PERMISSION_CODE = 1001;
//    }
//    val images: ArrayList<MultipartBody.Part> = ArrayList()
var filePath:String? = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_store)


        val apiInterface: StoreApi = ApiClient.getClient().create(StoreApi::class.java)

        btnPickCover.setOnClickListener {
            //check runtime permission
            ImagePicker.with(this)
                .crop(1f, 1f)               //Crop Square image(Optional)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data
                        ivCoverAdd.setImageURI(fileUri)

                        //You can get File object from intent
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        btnPickLogo.setOnClickListener {
            //check runtime permission
            ImagePicker.with(this)
                .crop(1f, 1f)               //Crop Square image(Optional)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data
                        ivLogoAdd.setImageURI(fileUri)

                        //You can get File object from intent
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }

        form {
            input(etstorename) {
                isNotEmpty()
            }
            input(etstoretagline) {
                isNotEmpty()
            }
            input(etstoredesc) {
                isNotEmpty()
            }
            input(etstorezipcode) {
                isNotEmpty()
            }

            submitWith(btnSubmit) { result ->
                //upload image
                val file: File = File(filePath)
                var mediaType = "image/png".toMediaType()
                val imageFile = RequestBody.create(mediaType, file)

                 // Create MultipartBody.Part using file request-body,file name and part name
                val part : MultipartBody.Part = MultipartBody.Part.createFormData("product[images][]", file.getName(), imageFile)


                val storeName = etstorename.text.toString()
                val storeTagLine = etstoretagline.text.toString()
                val storeDesc = etstoredesc.text.toString()
                val storeStreet = etstorestreet?.text.toString()
                val storeZipcode = etstorezipcode.text.toString()
                val storeCity = etstorecity.text.toString()
                val storeState = etstorestate?.text.toString()
                val storeCountry = etstorecountry.text.toString()
                val storePhone = etstorephone.text.toString()
            //check runtime permission


                    mediaType = "text/plain".toMediaType()
                val Name: RequestBody = RequestBody.create(mediaType,storeName )
                val TagLine: RequestBody = RequestBody.create(mediaType, storeTagLine)
                val Desc: RequestBody = RequestBody.create(mediaType, storeDesc)
                val Street: RequestBody = RequestBody.create(mediaType, storeStreet)
                val Zipcode: RequestBody = RequestBody.create(mediaType, storeZipcode)
                val City: RequestBody = RequestBody.create(mediaType, storeCity)
                val State: RequestBody = RequestBody.create(mediaType, storeState)
                val Country: RequestBody = RequestBody.create(mediaType, storeCountry)
                val Phone: RequestBody = RequestBody.create(mediaType, storePhone)

                apiInterface.addStore(Name,
                                    TagLine,
                                    Desc,
                                    Street,
                                    Zipcode,
                                    City,
                                    State,
                                    Country,
                                    Phone)
                    .enqueue(object : Callback<Data> {
                        override fun onResponse(call: Call<Data>, response: Response<Data>) {
                            println("TAG_: ${response.body()}")
                            val data = response.body()
                            if (response.isSuccessful) {
                                Toast.makeText(this@AddStore, "Product was successfully created", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@AddStore, response.message(), Toast.LENGTH_LONG).show()
                            }

                        }
                        override fun onFailure(call: Call<Data>, t: Throwable) = t.printStackTrace()
                    })
            }
        }

    }
    }
//    private fun pickImageFromGallery() {
//        //Intent to pick image
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }
//
//    //handle requested permission result
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size >0 && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED){
//                    //permission from popup granted
//                    pickImageFromGallery()
//                }
//                else{
//                    //permission from popup denied
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    //handle result of picked image
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            ivCoverAdd.setImageURI(data?.data)
//            }
//    }

}
