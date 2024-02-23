package com.example.topmovies.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentSplashBinding
import com.example.topmovies.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var storeUserData: StoreUserData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set delay
        lifecycleScope.launch {
            delay(2000)
            //Check user token
            storeUserData.getUserToken().collect{
                if (it.isEmpty()){
                    findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
                }else{
                    findNavController().navigate(R.id.actionToHome)
                }
            }

        }
    }
}