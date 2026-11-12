package com.example.oleholehjogja

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    private lateinit var souvenir: Souvenir
    private lateinit var ivDetailBookmark: ImageView
    private lateinit var tabDesc: TextView
    private lateinit var tabVariants: TextView
    private lateinit var tabTips: TextView
    private lateinit var indicatorDesc: View
    private lateinit var indicatorVariants: View
    private lateinit var indicatorTips: View
    private lateinit var paneDesc: LinearLayout
    private lateinit var paneVariants: LinearLayout
    private lateinit var paneTips: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Retrieve item
        val itemFromIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("souvenir", Souvenir::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("souvenir") as? Souvenir
        }

        val souvenirId = intent.getStringExtra("souvenir_id")
        val resolvedSouvenir = itemFromIntent ?: (if (souvenirId != null) SouvenirData.getById(souvenirId) else null)

        if (resolvedSouvenir == null) {
            Toast.makeText(this, "Data oleh-oleh tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        souvenir = resolvedSouvenir

        initViews()
        populateData()
        setupTabs()
        setupActions()
    }

    private fun initViews() {
        ivDetailBookmark = findViewById(R.id.ivDetailBookmark)
        tabDesc = findViewById(R.id.tabDesc)
        tabVariants = findViewById(R.id.tabVariants)
        tabTips = findViewById(R.id.tabTips)
        indicatorDesc = findViewById(R.id.indicatorDesc)
        indicatorVariants = findViewById(R.id.indicatorVariants)
        indicatorTips = findViewById(R.id.indicatorTips)
        paneDesc = findViewById(R.id.paneDesc)
        paneVariants = findViewById(R.id.paneVariants)
        paneTips = findViewById(R.id.paneTips)

        findViewById<View>(R.id.btnDetailBack).setOnClickListener {
            finish()
        }
    }

    private fun populateData() {
        findViewById<TextView>(R.id.tvDetailCategoryHeader).text = souvenir.categoryLabel.uppercase()
        findViewById<ImageView>(R.id.ivDetailImage).setImageResource(souvenir.imageResId)
        findViewById<TextView>(R.id.tvDetailTitle).text = souvenir.name
        findViewById<TextView>(R.id.tvDetailSubtitle).text = souvenir.subtitle

        findViewById<TextView>(R.id.tvDetailBadgeShelf).text = souvenir.shelfLife
        findViewById<TextView>(R.id.tvDetailBadgeCat).text = souvenir.categoryLabel
        findViewById<TextView>(R.id.tvDetailBadgePrice).text = souvenir.priceRange

        // Tab 1 Data
        findViewById<TextView>(R.id.tvDetailStory).text = souvenir.description
        findViewById<TextView>(R.id.tvDetailQuote).text = souvenir.quote

        // Tab 2 Data (Variants)
        val containerVariants = findViewById<LinearLayout>(R.id.containerVariants)
        containerVariants.removeAllViews()
        souvenir.variants.forEach { variant ->
            val v = LayoutInflater.from(this).inflate(R.layout.item_variant, containerVariants, false)
            v.findViewById<TextView>(R.id.tvVariantTitle).text = variant.title
            v.findViewById<TextView>(R.id.tvVariantDesc).text = variant.desc
            containerVariants.addView(v)
        }

        // Tab 3 Data (Packing Tips & Locations)
        val containerPackingTips = findViewById<LinearLayout>(R.id.containerPackingTips)
        containerPackingTips.removeAllViews()
        souvenir.packingTips.forEach { tip ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 5, 0, 5)
            }
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(dpToPx(4), dpToPx(4)).apply {
                    topMargin = dpToPx(6)
                }
                background = ContextCompat.getDrawable(context, R.drawable.bg_circle_button)
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gold))
            }
            val text = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = dpToPx(8)
                }
                setText(tip)
                setTextColor(ContextCompat.getColor(context, R.color.charcoal))
                textSize = 11.5f
                setLineSpacing(dpToPx(1).toFloat(), 1f)
            }
            row.addView(dot)
            row.addView(text)
            containerPackingTips.addView(row)
        }

        val containerLocations = findViewById<LinearLayout>(R.id.containerLocations)
        containerLocations.removeAllViews()
        souvenir.locations.forEach { loc ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 5, 0, 5)
            }
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(dpToPx(4), dpToPx(4)).apply {
                    topMargin = dpToPx(6)
                }
                background = ContextCompat.getDrawable(context, R.drawable.bg_circle_button)
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.terracotta))
            }
            val text = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = dpToPx(8)
                }
                setText(loc)
                setTextColor(ContextCompat.getColor(context, R.color.charcoal))
                textSize = 11.5f
                setLineSpacing(dpToPx(1).toFloat(), 1f)
            }
            row.addView(dot)
            row.addView(text)
            containerLocations.addView(row)
        }

        updateBookmarkIcon()
    }

    private fun setupTabs() {
        tabDesc.setOnClickListener { switchTab(0) }
        tabVariants.setOnClickListener { switchTab(1) }
        tabTips.setOnClickListener { switchTab(2) }
        switchTab(0)
    }

    private fun switchTab(tabIndex: Int) {
        val activeColor = ContextCompat.getColor(this, R.color.terracotta)
        val inactiveColor = ContextCompat.getColor(this, R.color.charcoal_secondary)
        val terracottaColor = ContextCompat.getColor(this, R.color.terracotta)
        val transparentColor = ContextCompat.getColor(this, android.R.color.transparent)

        paneDesc.visibility = if (tabIndex == 0) View.VISIBLE else View.GONE
        paneVariants.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE
        paneTips.visibility = if (tabIndex == 2) View.VISIBLE else View.GONE

        tabDesc.setTextColor(if (tabIndex == 0) activeColor else inactiveColor)
        tabDesc.typeface = if (tabIndex == 0) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT
        indicatorDesc.setBackgroundColor(if (tabIndex == 0) terracottaColor else transparentColor)

        tabVariants.setTextColor(if (tabIndex == 1) activeColor else inactiveColor)
        tabVariants.typeface = if (tabIndex == 1) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT
        indicatorVariants.setBackgroundColor(if (tabIndex == 1) terracottaColor else transparentColor)

        tabTips.setTextColor(if (tabIndex == 2) activeColor else inactiveColor)
        tabTips.typeface = if (tabIndex == 2) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT
        indicatorTips.setBackgroundColor(if (tabIndex == 2) terracottaColor else transparentColor)
    }

    private fun setupActions() {
        findViewById<FrameLayout>(R.id.btnDetailBookmark).setOnClickListener {
            val isNowBookmarked = BookmarkManager.toggleBookmark(this, souvenir.id)
            updateBookmarkIcon()
            val msg = if (isNowBookmarked) "Berhasil disimpan ke favorit" else "Dihapus dari simpanan"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.btnShare).setOnClickListener {
            val shareText = "Katalog OlehOlehJogja: ${souvenir.name} (${souvenir.subtitle})\n\n" +
                    "${souvenir.description}\n\n" +
                    "Estimasi Ketahanan: ${souvenir.shelfLife}\n" +
                    "Kisaran Harga: ${souvenir.priceRange}"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Bagikan Info Oleh-Oleh"))
        }

        findViewById<View>(R.id.btnSaveRoute).setOnClickListener {
            val routeInfo = "Sentra Belanja ${souvenir.name}:\n" + souvenir.locations.mapIndexed { idx, s -> "${idx + 1}. $s" }.joinToString("\n")
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Rute Belanja Oleh-Oleh", routeInfo)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Lokasi belanja berhasil disalin!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBookmarkIcon() {
        val isBookmarked = BookmarkManager.isBookmarked(this, souvenir.id)
        if (isBookmarked) {
            ivDetailBookmark.setImageResource(R.drawable.ic_bookmark)
            ivDetailBookmark.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.terracotta))
        } else {
            ivDetailBookmark.setImageResource(R.drawable.ic_bookmark_border)
            ivDetailBookmark.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.sogan))
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
