package kg.geektech.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import kg.geektech.newsapp.App
import kg.geektech.newsapp.R
import kg.geektech.newsapp.databinding.FragmentHomeBinding
import kg.geektech.newsapp.databinding.FragmentNewsBinding
import kg.geektech.newsapp.models.News

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private var news : News? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news = arguments?.getSerializable("news", ) as News?

        news?.let {
            binding.editText.setText(it.title)
        }

        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val text = binding.editText.text.toString().trim()

        if(news == null){
            news = News(0,text, System.currentTimeMillis())
            App.database.newsDao().insert(news!!)
        } else {
            news?.title = text
        }

//        val news = News(text, System.currentTimeMillis())
        val bundle = Bundle()
        bundle.putSerializable("news", news)
        parentFragmentManager.setFragmentResult("rk_news", bundle)
        findNavController().navigateUp()
    }
}
