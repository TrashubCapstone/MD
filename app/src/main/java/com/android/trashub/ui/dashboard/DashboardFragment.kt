package com.android.trashub.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.trashub.R
import com.android.trashub.databinding.FragmentDashboardBinding
import com.android.trashub.ml.Model7
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.Date

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private lateinit var currentPhotoPath: String
    private var selectedBitmap: Bitmap? = null

    // Daftar label kelas sesuai dengan keluaran model
    // Harus urut sama di tflite
    private val labels = arrayOf("cardboard", "clothes", "electronics", "food waste", "glass", "leaf waste", "metal", "paper", "plastic", "shoes", "trash", "wood waste")

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            dispatchTakePictureIntent()
        } else {
            // Permission denied. Handle appropriately.
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnGaleri.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnKamera.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        binding.btnSimpan.setOnClickListener {
            val bitmap: Bitmap? = getBitmapForProcessing()
            bitmap?.let {
                val resultText = runModelOnImage(it)
                navigateToResultFragment(it, resultText)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun checkCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                dispatchTakePictureIntent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // In an educational UI, explain to the user why your app requires this permission for a specific feature to behave as expected.
                // Request the permission again if the user agrees.
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                // You can directly ask for the permission.
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.android.trashub.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(null)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val file = File(currentPhotoPath)
            val photoUri = Uri.fromFile(file)
            cropImage(photoUri, photoUri) // Cropping the image from camera
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            selectedImage?.let {
                val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.jpg"))
                cropImage(it, destinationUri) // Cropping the image from gallery
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
                selectedBitmap = bitmap
                binding.image.setImageBitmap(bitmap)
            }
        }
    }

    private fun cropImage(sourceUri: Uri, destinationUri: Uri) {
        val uCrop = UCrop.of(sourceUri, destinationUri)
        uCrop.withAspectRatio(1f, 1f)
        uCrop.withMaxResultSize(224, 224)
        uCrop.start(requireContext(), this)
    }

    private fun getBitmapForProcessing(): Bitmap? {
        return selectedBitmap
    }

    private fun runModelOnImage(bitmap: Bitmap): String {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = resizedBitmap.getPixel(x, y)
                byteBuffer.putFloat((pixel shr 16 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((pixel shr 8 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((pixel and 0xFF) * (1f / 255))
            }
        }

        val model = Model7.newInstance(requireContext()) // Menggunakan model yang baru (Model7)
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        model.close()

        val probabilities = outputFeature0.floatArray
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1

        return if (maxIndex != -1) {
            labels[maxIndex]
        } else {
            "Unknown"
        }
    }

    private fun navigateToResultFragment(bitmap: Bitmap, resultText: String) {
        val bundle = Bundle().apply {
            putParcelable("image", bitmap)
            putString("result", resultText)
        }
        findNavController().navigate(R.id.action_navigation_dashboard_to_resultFragment, bundle)
    }

}