package com.example.topmovies.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentFavoriteBinding
import com.example.topmovies.ui.home.HomeFragmentDirections
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showInvisible
import com.example.topmovies.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewmodel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Show All Favorite
            viewmodel.loadFavoriteList()
            //List
            viewmodel.favoriteList.observe(viewLifecycleOwner) {
                favoriteRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    favoriteAdapter
                )
                favoriteAdapter.setData(it)
            }
            //Click
            favoriteAdapter.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetail(it.id)
                findNavController().navigate(direction)
            }
            //Empty
            viewmodel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }
        }
    }
}