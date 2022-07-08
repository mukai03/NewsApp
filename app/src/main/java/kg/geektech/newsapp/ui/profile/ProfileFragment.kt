package kg.geektech.newsapp.ui.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kg.geektech.newsapp.R
import kg.geektech.newsapp.databinding.FragmentHomeBinding
import kg.geektech.newsapp.databinding.FragmentNotificationsBinding
import kg.geektech.newsapp.databinding.FragmentProfileBinding
import kg.geektech.newsapp.ui.notifications.NotificationsViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }
        textWatcher()
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri : Uri? ->
            Glide.with(binding.imageView).load(uri).centerCrop().into(binding.imageView)
    }

    private fun textWatcher() {
        val pref = requireContext().getSharedPreferences("name", Context.MODE_PRIVATE)
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                pref.edit().putString("name", p0.toString()).apply()
                Log.e("Tag", "textWatcher:${pref.getString("name", "defaultName")}")
                binding.textView.text = pref.getString("name", "defaultName")
            }

        }
        binding.editText.addTextChangedListener(listener)
    }
}
