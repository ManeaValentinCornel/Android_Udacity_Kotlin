package com.vcmanea.a08_marsapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.vcmanea.a08_marsapp.network.MarsProperty
import com.vcmanea.a08_marsapp.overview.MarsApiStatus
import com.vcmanea.a08_marsapp.overview.PhotoGridAdapter

//This tells data-binding, we want this binding adapter executed when an XML item has the imageUrl attribute
@BindingAdapter("imgUrl")
//The first parameter is the view parameter and is specified as an ImageView, wich means that only ImageView and any derived classes can use this adapter
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        //covnert the imgUrl to uri, making sure the resulting URi has the HTTPS scheme, because the server we're pulling the images from requires HTTPS.
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}
@BindingAdapter("getData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {

    data?.let {
        val adapter = recyclerView.adapter as PhotoGridAdapter

        adapter.submitList(data)
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(image: ImageView, status: MarsApiStatus?) {
    status?.let {
        when (status) {
            MarsApiStatus.LOADING -> {
                image.visibility = View.VISIBLE
                image.setImageResource(R.drawable.loading_animation)
            }
            MarsApiStatus.ERROR -> {
//            image.visibility= View.VISIBLE
//            image.setImageResource(R.drawable.ic_connection_error)
            }
            MarsApiStatus.DONE -> {
                image.visibility = View.GONE
            }
        }

    }

}








