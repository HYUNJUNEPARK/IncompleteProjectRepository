package com.june.daangnmarket.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.StorageReference
import com.june.daangnmarket.databinding.ActivityArticleAddBinding
import com.june.daangnmarket.dialog.PermissionDialog
import com.june.daangnmarket.model.ChatListItemModel
import com.june.daangnmarket.key.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.key.FirebaseVar.Companion.storage
import com.june.daangnmarket.key.RequestCode.Companion.REQUEST_READ_EXTERNAL_STORAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    val myDialog = PermissionDialog()
                    myDialog.showPermissionPopup(this)
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE
                    )
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
                    .transform(CenterCrop(), RoundedCorners(18))
                    .into(binding.photoImageView)

                filePath = uri
            } else {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSubmitButton() {
        binding.submitButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            val title = binding.titleEditText.text.toString()
            val price = binding.priceEditText.text.toString()
            val sellerId = auth?.currentUser?.uid.orEmpty()
            val createAt = System.currentTimeMillis()
            val imageUri = sellerId + createAt
            val itemDescription = binding.itemDescriptionEditText.text.toString()
            //content://com.android.providers.media.documents/document/image%3A3487"

            if (title.isEmpty() || price.isEmpty() || filePath == null) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val model = ChatListItemModel(createAt, imageUri, price, sellerId, title, itemDescription)
            val imgId = sellerId + createAt
            val articleDB = firebaseDBReference.child(DB_ARTICLES)

            CoroutineScope(Dispatchers.IO).launch {
                articleDB.push().setValue(model)
            }
            uploadImgToStorage(imgId)
        }
    }

    private fun uploadImgToStorage(id: String) {
        val storageRef = storage.reference
        val imgRef: StorageReference = storageRef.child("images_daangn/$id.jpg")
        val file_uri: Uri = filePath ?: return

        CoroutineScope(Dispatchers.IO).launch {
            imgRef.putFile(file_uri)
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@AddArticleActivity, "업로드 완료", Toast.LENGTH_SHORT).show()
                    finish()

                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@AddArticleActivity, "Error : $e", Toast.LENGTH_SHORT).show()
                }
        }

    }
}