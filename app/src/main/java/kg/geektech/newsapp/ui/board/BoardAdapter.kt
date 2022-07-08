package kg.geektech.newsapp.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.geektech.newsapp.R
import kg.geektech.newsapp.databinding.PagerBoardBinding
import kg.geektech.newsapp.models.PagerModel

class BoardAdapter(private val onClickStart: () -> Unit) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    private val titlesSwipe = arrayListOf<PagerModel>(
        PagerModel(
            "Page 1",
            R.raw.news1,
            "Swipe left"
        ),
        PagerModel(
            "Page 1",
            R.raw.news2,
            "Swipe left"
        ),
        PagerModel(
            "Page 1",
            R.raw.news3,
            "Swipe left"
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
            Glide.with(binding.imageView).load(pagerModel.image).into(binding.imageView)

            if (adapterPosition != titlesSwipe.size -1) {
                binding.btnStart.visibility = View.INVISIBLE
            }
            binding.btnStart.setOnClickListener {
                onClickStart
            }

        }

    }

}