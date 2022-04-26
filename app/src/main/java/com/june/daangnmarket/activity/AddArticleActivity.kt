package com.june.daangnmarket.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.june.daangnmarket.databinding.ActivityArticleAddBinding
import com.june.daangnmarket.home.ArticleModel
import com.june.daangnmarket.share.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.share.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.share.FirebaseVar.Companion.auth
import com.june.daangnmarket.share.FirebaseVar.Companion.storage
import com.june.daangnmarket.share.RequestCode.Companion.REQUEST_READ_EXTERNAL_STORAGE

class AddArticleActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArticleAddBinding.inflate(layoutInflater) }
    private var filePath: Uri? = null
    lateinit var resultListener: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initImageAddButton()
        initSubmitButton()
        initResultListener()
    }

    private fun initImageAddButton() {
        binding.imageAddButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultListener.launch(intent)
    }

    private fun initResultListener() {
        resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                return@registerForActivityResult
            }
            val uri = result.data?.data
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.photoImageView)

                filePath = uri
            } else {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSubmitButton() {
        binding.submitButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val price = binding.priceEditText.text.toString()
            val sellerId = auth.currentUser?.uid.orEmpty()
            val imageUri = filePath.toString()
            //content://com.android.providers.media.documents/document/image%3A3487"

            if (title.isEmpty() || price.isEmpty() || imageUri == "null") {
                Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val createAt = System.currentTimeMillis()
            val model = ArticleModel(createAt, imageUri, price, sellerId, title)
            val imgId = sellerId + createAt
            val articleDB = firebaseDBReference.child(DB_ARTICLES)

            Thread {
                articleDB.push().setValue(model)
                uploadImgToStorage(imgId)
            }.start()

            finish()
        }
    }

    private fun uploadImgToStorage(id: String) {
        val storageRef = storage.reference
        val imgRef: StorageReference = storageRef.child("images_daangn/$id.jpg")
        val file_uri: Uri = filePath ?: return

        Thread {
            imgRef.putFile(file_uri)
                .addOnSuccessListener {
                    runOnUiThread {
                        Toast.makeText(this, "업로드 완료", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    runOnUiThread {
                        Toast.makeText(this, "Error : $e", Toast.LENGTH_SHORT).show()
                    }
                }
        }.start()
    }

    private fun showPermissionPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            }
            .create()
            .show()
    }
}