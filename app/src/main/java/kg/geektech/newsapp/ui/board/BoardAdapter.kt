package kg.geektech.newsapp.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.geektech.newsapp.R
import kg.geektech.newsapp.databinding.PagerBoardBinding
import kg.geektech.newsapp.models.PagerModel

class BoardAdapter(private val onClickStart: () -> Unit) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    private lateinit var navController: NavController
    private val titlesSwipe = arrayListOf<PagerModel>(
        PagerModel(
            "Салам",
            R.raw.news1,
            "Swipe left"
        ),
        PagerModel(
            "Привет",
            R.raw.news2,
            "Swipe left"
        ),
        PagerModel(
            "Hello",
            R.raw.news3,
            "Tap to Start"
        )
    )



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PagerBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titlesSwipe[position])
    }

    override fun getItemCount() = titlesSwipe.size

    inner class ViewHolder(private var binding: PagerBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pagerModel: PagerModel) {

            binding.textTitle.text = pagerModel.title
            binding.textDesc.text = pagerModel.description
            binding.imageView.setAnimation(pagerModel.image)
            if (adapterPosition != titlesSwipe.size -1) {
                binding.btnStart.visibility = View.INVISIBLE
            }
            binding.btnStart.setOnClickListener {
                onClickStart()
            }

        }

    }

}