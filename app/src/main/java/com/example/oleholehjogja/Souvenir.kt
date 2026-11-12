package com.example.oleholehjogja

import java.io.Serializable

data class Variant(
    val title: String,
    val desc: String
) : Serializable

data class Souvenir(
    val id: String,
    val name: String,
    val subtitle: String,
    val category: String, // "basah", "kering", "minuman", "kerajinan"
    val categoryLabel: String,
    val shelfLife: String,
    val priceRange: String,
    val imageResId: Int,
    val isPopular: Boolean,
    val description: String,
    val quote: String,
    val variants: List<Variant>,
    val packingTips: List<String>,
    val locations: List<String>
) : Serializable

val Souvenir.priceValue: Int
    get() {
        val clean = priceRange.replace(".", "").replace("Rp", "").trim()
        val firstNum = clean.split("-").firstOrNull()?.filter { it.isDigit() } ?: ""
        return firstNum.toIntOrNull() ?: 25000
    }

val Souvenir.estimatedWeightGrams: Int
    get() = when (id) {
        "bakpia-pathok" -> 450
        "gudeg-kendil" -> 600
        "wedang-uwuh" -> 150
        "yangko-kotagede" -> 350
        "geplak-bantul" -> 300
        "bakpia-kukus" -> 400
        "oseng-mercon" -> 500
        "peyek-tumpuk" -> 300
        "kopi-jos" -> 250
        "cokelat-monggo" -> 200
        "jadah-tempe" -> 500
        "tiwul-manis" -> 400
        "salak-pondoh" -> 1000
        else -> 350
    }
