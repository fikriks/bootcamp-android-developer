package com.bootcamp.tugas3_bootcampidn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bootcamp.tugas3_bootcampidn.databinding.ActivityDetailNewsBinding
import com.bumptech.glide.Glide

class DetailNewsActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailNewsBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailNewsBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.title = "Detail"

		val data = intent.getParcelableExtra<ArticlesItem>(EXTRA_NEWS)!!

		binding.apply {
			Glide.with(imgNews)
				.load(data.urlToImage)
				.error(R.drawable.ic_launcher_background)
				.into(imgNews)

			tvJudul.text = data.title
			tvDeskripsi.text = data.description
			tvDeskripsi.setOnClickListener {
				data.description?.let { it -> shareDescription(it) }
			}

			btnVisit.setOnClickListener {
				val uri = Uri.parse(data.url)
				val browserIntent = Intent(Intent.ACTION_VIEW, uri)
				startActivity(browserIntent)
			}
		}
	}

	private fun shareDescription(description:String) {
		val intent = Intent()
		intent.apply {
			action = Intent.ACTION_SEND
			putExtra(Intent.EXTRA_TEXT,description)
			type = "text/plain"
		}

		val shareIntent = Intent.createChooser(intent,"Share Description")
		startActivity(shareIntent)
	}

	companion object{
		const val EXTRA_NEWS = "extra_news"
	}
}