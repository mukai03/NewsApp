package kg.geektech.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kg.geektech.newsapp.App
import kg.geektech.newsapp.R
import kg.geektech.newsapp.databinding.FragmentHomeBinding
import kg.geektech.newsapp.models.News
import kg.geektech.newsapp.ui.news.NewsAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var changeable : Boolean = false
    private  val adapter= NewsAdapter(this::onClick, this::onLongClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.addItems(App.database.newsDao().sortAll())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentHomeBinding.inflate(inflater, container, false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.newsFragment)
        }
        parentFragmentManager
            .setFragmentResultListener("rk_news", viewLifecycleOwner) {requestKey, bundle ->
                val news = bundle.getSerializable("news") as News
                val position : Int? = null
                if (changeable){
                    position.let {
                        if (it !=null) {
                            adapter.replaceItem(news, it)
                        }
                    }
                }else{
                    adapter.addItem(news)
                    Log.e("Home", "text ${news.title} ${news.createdAt}")
                }
        }
    }

    private fun onClick(position: Int) {
        val news = adapter.getItem(position)
        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putSerializable("news", news)
        findNavController().navigate(R.id.newsFragment, bundle)
        changeable = true
    }

    private fun onLongClick(news: News){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Уведомление")
            .setMessage("Ты хочешь удалить новость")
            .setNegativeButton("Нет") { _, _ ->
            }
            .setPositiveButton("Удалить"){ _, _ ->
                App.database.newsDao().deleteItem(news)
                adapter.removeItem(news)
                adapter.addItems(App.database.newsDao().sortAll())
            }
            .show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }

}

