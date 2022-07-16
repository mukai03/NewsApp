package kg.geektech.newsapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kg.geektech.newsapp.Prefs
import kg.geektech.newsapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

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
//        binding.imageView.setOnClickListener {
//            getContent.launch("image/*")
//       }
        initLauncher()
        binding.imageView.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            launcher.launch(intent)
        }
        saveName()
    }
    private fun initLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val image = it.data?.data
                if (image != null) {
                    binding.imageView.setImageURI(image)
                }
            }
        }
    }

    private fun saveName() {
        val pref = Prefs(requireContext())
        val name = binding.editText.text.toString()
        binding.saveButton.setOnClickListener {
            Prefs(requireContext()).saveNames("name", name)
            Log.e("Tag", "name:${pref.saveNames("")}")
        }
        /*binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0 : CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val text = binding.editText.text.toString()
                Prefs(requireContext()).saveNames(text)
            }
        })*/
    }

/*    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri : Uri? ->
            Glide.with(binding.imageView).load(uri).centerCrop().into(binding.imageView)
    }*/

   /* private fun textWatcher() {
        val pref = Prefs(requireContext())
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                pref.textWatcher(p0)
                Log.e("Tag", "textWatcher:${pref.getTextWatcher()}")
                binding.textView.text = pref.getTextWatcher()
            }

        }
        binding.editText.addTextChangedListener(listener)
    }*/

}

