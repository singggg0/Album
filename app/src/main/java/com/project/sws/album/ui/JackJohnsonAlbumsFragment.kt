package com.project.sws.album.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.sws.album.databinding.FragmentJackJohnsonAlbumsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JackJohnsonAlbumsFragment : Fragment() {
    private val vm: AlbumViewModel by activityViewModels()
    private var binding: FragmentJackJohnsonAlbumsBinding? = null
    private val showBookmarkedOnly: Boolean by lazy {
        arguments?.takeIf { it.containsKey(SHOW_BOOKMARKED_ONLY) }
            ?.getBoolean(SHOW_BOOKMARKED_ONLY)
            ?: false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentJackJohnsonAlbumsBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            rv.let {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.itemAnimator = null
                it.adapter = AlbumAdapter(this@JackJohnsonAlbumsFragment).apply {
                    onBookmarkCheckedChange = { id, isChecked ->
                        if (isChecked) {
                            vm.bookmarkAlbum(id)
                        } else {
                            vm.removeBookmarkAlbum(id)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                when (showBookmarkedOnly) {
                    true -> vm.bookmarkedAlbums
                    false -> vm.albums
                }.collectLatest {
                    (binding?.rv?.adapter as AlbumAdapter?)?.submitList(it)
                }
            }
        }
    }

    companion object {
        private const val SHOW_BOOKMARKED_ONLY = "SHOW_BOOKMARKED_ONLY"
        fun newInstance(showBookmarkedOnly: Boolean): JackJohnsonAlbumsFragment {
            val args = Bundle().apply {
                putBoolean(SHOW_BOOKMARKED_ONLY, showBookmarkedOnly)
            }
            return JackJohnsonAlbumsFragment().apply {
                arguments = args
            }
        }
    }
}