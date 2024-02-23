package com.example.topmovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentDetailBinding
import com.example.topmovies.db.MovieEntity
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showInvisible
import com.example.topmovies.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    @Inject
    lateinit var entity: MovieEntity

    //Other
    private var movieID = 0
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get Data
        movieID = args.movieID
        //Call Api
        if (movieID > 0) {
            viewModel.loadDetailMovie(movieID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Load data
            viewModel.detailMovie.observe(viewLifecycleOwner) { response ->
                posterBigImg.load(response.poster)
                posterNormalImg.load(response.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.imdbRating
                movieTimeTxt.text = response.runtime
                movieDateTxt.text = response.released
                movieSummaryInfo.text = response.plot
                movieActorsInfo.text = response.actors
                //Images Adapter
                imagesAdapter.differ.submitList(response.images)
                imagesRecyclerView.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    imagesAdapter
                )
                //Fav click
                favImg.setOnClickListener {

                    viewModel.favoriteMovie(movieID, entity)
                    entity.id = movieID
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.rated.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                }
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailScrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }
            //Default fav icon color
            lifecycleScope.launch {
                if (viewModel.existsMovie(movieID)) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.philippineSilver
                        )
                    )
                }
            }
            //Change image with click
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    favImg.setColorFilter( ContextCompat.getColor(  requireContext() , R.color.scarlet))

                } else {
                    favImg.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.philippineSilver
                        )
                    )
                }
            }
            //Back
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}
