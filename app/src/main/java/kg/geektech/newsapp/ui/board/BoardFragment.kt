package kg.geektech.newsapp.ui.board

import android.os.Bundle
import android.system.Os.close
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.geektech.newsapp.Prefs
import kg.geektech.newsapp.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BoardAdapter {
            close()
        }
        /*binding.viewPager.adapter = adapter
        val dotsIndicator = binding.indicator
        val viewPager = binding.viewPager
        dotsIndicator.setViewPager2(viewPager)*/

        binding.viewPager.adapter = adapter
        binding.indicator.attachTo(binding.viewPager)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.btnSkip.visibility = View.INVISIBLE
                } else {
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })
        /*binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.btnSkip.setOnClickListener {
                    findNavController().navigateUp()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 2) {
                    binding.btnSkip.visibility = View.INVISIBLE
                }else{
                    binding.btnSkip.visibility = View.VISIBLE
                }

            }
        })

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })*/
        binding.btnSkip.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun close(){
        Prefs(requireContext()).saveState()
        findNavController().navigateUp()
    }
}