package com.example.pokedex.adapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokedex.R
import java.util.*

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .error(R.drawable.ic_pokeball)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("backgroundColor")
fun setBackground(view: View, color: String?) {
    view.setBackgroundResource(getBackgroundResource(color))
}

fun getBackgroundResource(color: String?): Int =
    if (color != null) {
        when (color.toLowerCase(Locale.ROOT)) {
            "black" -> R.color.pokemonBlack
            "blue" -> R.color.pokemonBlue
            "brown" -> R.color.pokemonBrown
            "gray" -> R.color.pokemonGray
            "green" -> R.color.pokemonGreen
            "pink" -> R.color.pokemonPink
            "purple" -> R.color.pokemonPurple
            "red" -> R.color.pokemonRed
            "white" -> R.color.pokemonWhite
            "yellow" -> R.color.pokemonYellow
            else -> R.color.pokemonWhite
        }
    } else {
        R.color.pokemonWhite
    }

@BindingAdapter("textColor")
fun setTextColor(textView: TextView, backgroundColor: String?) {
    val color = textView.context.resources.getString(getBackgroundResource(backgroundColor))
    val lum = Color.parseColor(color).luminance
    val colorHex = if (lum < 0.7) {
        0xFFFFFFFF
    } else {
        0xFF000000
    }
    textView.setTextColor(colorHex.toInt())
}

