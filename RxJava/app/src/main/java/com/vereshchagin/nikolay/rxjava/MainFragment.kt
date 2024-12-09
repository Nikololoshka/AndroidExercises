package com.vereshchagin.nikolay.rxjava

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vereshchagin.nikolay.rxjava.api.Post
import com.vereshchagin.nikolay.rxjava.databinding.FragmentMainBinding
import com.vereshchagin.nikolay.rxjava.extentions.plusAssign
import com.vereshchagin.nikolay.rxjava.list.PostListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var timerDisposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMakeRequest()
        setupTimer()
        setupRecycler()
        setupEditText()
    }

    /**
     * Сделайте сетевой запрос и отобразите результат на экране? (база)
     */
    private fun setupMakeRequest() {
        binding.makeRequest.setOnClickListener { viewModel.makeRequest() }
        compositeDisposable += viewModel.response
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { binding.response.text = it }
    }

    /**
     * Сделайте таймер. TextView которая раз в секунду меняется (timer)
     */
    private fun setupTimer() {
        /*
        compositeDisposable += Observable.interval(1L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val currentDateTime = Calendar.getInstance()
                val formatter = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
                binding.timer.text = formatter.format(currentDateTime.time)
            }
         */

        timerDisposable?.dispose()
        timerDisposable = Observable.timer(1L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { setupTimer() }
            .subscribe {
                val currentDateTime = Calendar.getInstance()
                val formatter = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
                binding.timer.text = formatter.format(currentDateTime.time)
            }
    }

    /**
     * Сделайте ресайклер. По нажатию на элемент передавайте его позицию во фрагмент.
     * и во фрагменте этот номер отображайте в тосте. (Subject)
     */
    private fun setupRecycler() {
        val itemClickedSubject = PublishSubject.create<Int>()
        compositeDisposable += itemClickedSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { index ->
                Toast.makeText(requireContext(), "Post by index: $index", Toast.LENGTH_SHORT).show()
            }

        val adapter = PostListAdapter(itemClickedSubject)
        binding.recycler.adapter = adapter

        val posts = MutableList(20) { Post(it, "Post №${it}", "Post details...", -1) }
        adapter.submitList(posts)
    }

    /**
     * Сделайте EditText. При наборе текста выводите в лог
     * содержимое EditText всегда, когда пользователь 3 секунды что-то не вводил (debounce)
     */
    private fun setupEditText() {
        compositeDisposable += Observable
            .create { emitter ->
                binding.editText.addTextChangedListener(
                    onTextChanged = { text, _, _, _ -> text?.let { emitter.onNext(it) } }
                )
            }
            .debounce(3L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { Log.d("MainFragment", "setupEditText: $it") }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        compositeDisposable.dispose()
        timerDisposable?.dispose()
    }
}