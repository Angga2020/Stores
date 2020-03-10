package com.example.stores.UI.Stores

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import com.example.myapplication.Services.ApiClient
import com.example.myapplication.Services.StoreApi
import com.example.myapplication.utils.GlideApp
import com.example.stores.Model.Stores.Data
import com.example.stores.R
import kotlinx.android.synthetic.main.update_store_activity.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateStore : AppCompatActivity() {

    var filePath:String? = String()
    val apiInterface: StoreApi = ApiClient.getClient().create(StoreApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_store_activity)
        val store : Data = intent.getParcelableExtra("store")



        GlideApp.with(this)
            .load(store.attributes.logoUrl!!)
//                    .placeholder(R.drawable.noimage)
//                    .circleCrop()
            .into(ivLogoUpdate)
        GlideApp.with(this)
            .load(store.attributes.coverUrl!!)
//                    .placeholder(R.drawable.noimage)
//                    .circleCrop()
            .into(ivCoverUpdate)

        etstorenameUpdate.setText(store.attributes.name)
        etstoretaglineUpdate.setText(store.attributes.tagline)
        etstoredescUpdate.setText(store.attributes.description)
        etstorestreetUpdate.setText(store.attributes.street)
        etstorezipcodeUpdate.setText(store.attributes.zipcode)
        etstorecityUpdate.setText(store.attributes.city)
        etstorestateUpdate.setText(store.attributes.state)
        etstorecountryUpdate.setText(store.attributes.country)
        etstorephoneUpdate.setText(store.attributes.phone)


        form {
            input(etstorenameUpdate) {
                isNotEmpty()
            }
            input(etstoretaglineUpdate) {
                isNotEmpty()
            }
            input(etstoredescUpdate) {
                isNotEmpty()
            }
            input(etstorezipcodeUpdate) {
                isNotEmpty()
            }

            submitWith(btnSubmitUpdate) { result ->
                //upload image
                val file: File = File(filePath)
                var mediaType = "image/png".toMediaType()
                val imageFile = RequestBody.create(mediaType, file)

                // Create MultipartBody.Part using file request-body,file name and part name
//                val part : MultipartBody.Part = MultipartBody.Part.createFormData("store[images]", file.getName(), imageFile)


                val storeName = etstorenameUpdate.text.toString()
                val storeTagLine = etstoretaglineUpdate.text.toString()
                val storeDesc = etstoredescUpdate.text.toString()
                val storeStreet = etstorestreetUpdate?.text.toString()
                val storeZipcode = etstorezipcodeUpdate.text.toString()
                val storeCity = etstorecityUpdate.text.toString()
                val storeState = etstorestateUpdate?.text.toString()
                val storeCountry = etstorecountryUpdate.text.toString()
                val storePhone = etstorephoneUpdate.text.toString()


                mediaType = "text/plain".toMediaType()
                val Name = RequestBody.create(mediaType,storeName).toString()
                val TagLine  = RequestBody.create(mediaType, storeTagLine).toString()
                val Desc = RequestBody.create(mediaType, storeDesc).toString()
                val Street = RequestBody.create(mediaType, storeStreet).toString()
                val Zipcode = RequestBody.create(mediaType, storeZipcode).toString()
                val City = RequestBody.create(mediaType, storeCity).toString()
                val State = RequestBody.create(mediaType, storeState).toString()
                val Country = RequestBody.create(mediaType, storeCountry).toString()
                val Phone = RequestBody.create(mediaType, storePhone).toString()

                apiInterface.updateStore(store.attributes.id,
                        Name,
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
                                Toast.makeText(this@UpdateStore, "Product was successfully created", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@UpdateStore, response.message(), Toast.LENGTH_LONG).show()
                            }

                        }
                        override fun onFailure(call: Call<Data>, t: Throwable) = t.printStackTrace()
                    })
            }
        }
    }

}

