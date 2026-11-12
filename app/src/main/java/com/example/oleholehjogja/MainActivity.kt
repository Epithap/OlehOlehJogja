package com.example.oleholehjogja

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Jelajah Views
    private lateinit var rvSouvenirs: RecyclerView
    private lateinit var adapter: SouvenirAdapter
    private lateinit var etSearch: EditText
    private lateinit var btnClearSearch: ImageView
    private lateinit var btnBookmarkFilter: FrameLayout
    private lateinit var ivHeaderBookmarkIcon: ImageView
    private lateinit var tvBookmarkBadge: TextView
    private lateinit var btnCartHeader: FrameLayout
    private lateinit var tvCartBadge: TextView
    private lateinit var layoutBookmarkBanner: RelativeLayout
    private lateinit var tvBookmarkBannerText: TextView
    private lateinit var btnBannerSeeAll: TextView
    private lateinit var layoutEmptyState: LinearLayout
    private lateinit var btnResetFilter: View

    // 3 Main Tab Containers
    private lateinit var containerExplore: LinearLayout
    private lateinit var containerPopular: NestedScrollView
    private lateinit var containerProfile: NestedScrollView

    // Header Dynamic Sections
    private lateinit var layoutSearchBar: FrameLayout
    private lateinit var scrollCategories: View
    private lateinit var layoutProfileHeaderInfo: LinearLayout

    // Bottom Navigation Views
    private lateinit var navExplore: LinearLayout
    private lateinit var navPopular: LinearLayout
    private lateinit var navProfile: LinearLayout
    private lateinit var ivNavExplore: ImageView
    private lateinit var tvNavExplore: TextView
    private lateinit var ivNavPopular: ImageView
    private lateinit var tvNavPopular: TextView
    private lateinit var ivNavProfile: ImageView
    private lateinit var tvNavProfile: TextView

    // Profile Views
    private lateinit var tvProfileStatSaved: TextView

    // Category chips
    private lateinit var chips: List<Pair<TextView, String>>

    // State
    private var currentTab: Int = 0 // 0: Jelajah, 1: Populer, 2: Profil
    private var currentCategory: String = "semua"
    private var searchQuery: String = ""
    private var isBookmarkFilterOnly: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        setupSearch()
        setupCategories()
        setupBookmarks()
        setupBottomNav()
        setupPopularTabClicks()
        setupProfileTabClicks()
        filterList()
    }

    override fun onResume() {
        super.onResume()
        updateBookmarkBadge()
        updateCartBadge()
        updateProfileSavedStat()
        filterList()
    }

    private fun initViews() {
        rvSouvenirs = findViewById(R.id.rvSouvenirs)
        etSearch = findViewById(R.id.etSearch)
        btnClearSearch = findViewById(R.id.btnClearSearch)
        btnBookmarkFilter = findViewById(R.id.btnBookmarkFilter)
        ivHeaderBookmarkIcon = findViewById(R.id.ivHeaderBookmarkIcon)
        tvBookmarkBadge = findViewById(R.id.tvBookmarkBadge)
        btnCartHeader = findViewById(R.id.btnCartHeader)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        layoutBookmarkBanner = findViewById(R.id.layoutBookmarkBanner)
        tvBookmarkBannerText = findViewById(R.id.tvBookmarkBannerText)
        btnBannerSeeAll = findViewById(R.id.btnBannerSeeAll)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)
        btnResetFilter = findViewById(R.id.btnResetFilter)

        containerExplore = findViewById(R.id.containerExplore)
        containerPopular = findViewById(R.id.containerPopular)
        containerProfile = findViewById(R.id.containerProfile)

        layoutSearchBar = findViewById(R.id.layoutSearchBar)
        scrollCategories = findViewById(R.id.scrollCategories)
        layoutProfileHeaderInfo = findViewById(R.id.layoutProfileHeaderInfo)

        navExplore = findViewById(R.id.navExplore)
        navPopular = findViewById(R.id.navPopular)
        navProfile = findViewById(R.id.navProfile)
        ivNavExplore = findViewById(R.id.ivNavExplore)
        tvNavExplore = findViewById(R.id.tvNavExplore)
        ivNavPopular = findViewById(R.id.ivNavPopular)
        tvNavPopular = findViewById(R.id.tvNavPopular)
        ivNavProfile = findViewById(R.id.ivNavProfile)
        tvNavProfile = findViewById(R.id.tvNavProfile)

        tvProfileStatSaved = findViewById(R.id.tvProfileStatSaved)

        btnCartHeader.setOnClickListener {
            showCalculatorDialog()
        }

        btnResetFilter.setOnClickListener {
            resetAllFilters()
        }

        btnBannerSeeAll.setOnClickListener {
            isBookmarkFilterOnly = false
            updateBookmarkFilterUi()
            filterList()
        }
    }

    private fun setupRecyclerView() {
        adapter = SouvenirAdapter(
            context = this,
            items = SouvenirData.list,
            onItemClick = { item ->
                openDetail(item.id)
            },
            onBookmarkClick = { item ->
                val isNowBookmarked = BookmarkManager.toggleBookmark(this, item.id)
                updateBookmarkBadge()
                updateProfileSavedStat()
                val msg = if (isNowBookmarked) "Berhasil disimpan ke favorit" else "Dihapus dari simpanan"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                if (isBookmarkFilterOnly) {
                    filterList()
                }
            },
            onCartClick = { item ->
                CartManager.addItem(item.id)
                updateCartBadge()
                Toast.makeText(this, "${item.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            }
        )
        // 2-column grid layout exactly matching Figma design
        rvSouvenirs.layoutManager = GridLayoutManager(this, 2)
        rvSouvenirs.adapter = adapter
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s?.toString()?.trim() ?: ""
                btnClearSearch.visibility = if (searchQuery.isNotEmpty()) View.VISIBLE else View.GONE
                filterList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnClearSearch.setOnClickListener {
            etSearch.text.clear()
        }
    }

    private fun setupCategories() {
        val chipSemua: TextView = findViewById(R.id.chipSemua)
        val chipBasah: TextView = findViewById(R.id.chipBasah)
        val chipKering: TextView = findViewById(R.id.chipKering)
        val chipMinuman: TextView = findViewById(R.id.chipMinuman)

        chips = listOf(
            Pair(chipSemua, "semua"),
            Pair(chipBasah, "basah"),
            Pair(chipKering, "kering"),
            Pair(chipMinuman, "minuman")
        )

        chips.forEach { (chipView, categoryKey) ->
            chipView.setOnClickListener {
                currentCategory = categoryKey
                switchTab(0) // Switch to Explore view if tapped from Popular
                updateCategoryChipsUi()
                filterList()
            }
        }
    }

    private fun updateCategoryChipsUi() {
        chips.forEach { (chipView, categoryKey) ->
            if (categoryKey == currentCategory) {
                chipView.setBackgroundResource(R.drawable.bg_chip_selected)
                chipView.setTextColor(ContextCompat.getColor(this, R.color.sogan))
                chipView.typeface = android.graphics.Typeface.DEFAULT_BOLD
            } else {
                chipView.setBackgroundResource(R.drawable.bg_chip_unselected)
                chipView.setTextColor(ContextCompat.getColor(this, R.color.white))
                chipView.typeface = android.graphics.Typeface.DEFAULT
            }
        }
    }

    private fun setupBookmarks() {
        btnBookmarkFilter.setOnClickListener {
            switchTab(0)
            isBookmarkFilterOnly = !isBookmarkFilterOnly
            updateBookmarkFilterUi()
            filterList()
        }
        updateBookmarkBadge()
    }

    private fun updateBookmarkBadge() {
        val count = BookmarkManager.getCount(this)
        if (count > 0) {
            tvBookmarkBadge.text = count.toString()
            tvBookmarkBadge.visibility = View.VISIBLE
        } else {
            tvBookmarkBadge.visibility = View.GONE
        }
        tvBookmarkBannerText.text = getString(R.string.bookmark_banner_text, count)
    }

    private fun updateCartBadge() {
        val totalCount = CartManager.getTotalItemCount()
        if (totalCount > 0) {
            tvCartBadge.text = totalCount.toString()
            tvCartBadge.visibility = View.VISIBLE
        } else {
            tvCartBadge.visibility = View.GONE
        }
    }

    private fun updateProfileSavedStat() {
        val count = BookmarkManager.getCount(this)
        tvProfileStatSaved.text = if (count > 0) count.toString() else "12"
    }

    private fun updateBookmarkFilterUi() {
        if (isBookmarkFilterOnly) {
            btnBookmarkFilter.setBackgroundResource(R.drawable.bg_chip_selected)
            ivHeaderBookmarkIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.sogan))
            layoutBookmarkBanner.visibility = View.VISIBLE
        } else {
            btnBookmarkFilter.setBackgroundResource(R.drawable.bg_circle_button)
            ivHeaderBookmarkIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gold))
            layoutBookmarkBanner.visibility = View.GONE
        }
    }

    private fun setupBottomNav() {
        navExplore.setOnClickListener {
            switchTab(0)
        }

        navPopular.setOnClickListener {
            switchTab(1)
        }

        navProfile.setOnClickListener {
            switchTab(2)
        }
    }

    private fun switchTab(tabIndex: Int) {
        currentTab = tabIndex

        // Switch containers
        containerExplore.visibility = if (tabIndex == 0) View.VISIBLE else View.GONE
        containerPopular.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE
        containerProfile.visibility = if (tabIndex == 2) View.VISIBLE else View.GONE

        // Switch header layout elements
        if (tabIndex == 2) {
            // Profile tab header
            layoutSearchBar.visibility = View.GONE
            scrollCategories.visibility = View.GONE
            layoutProfileHeaderInfo.visibility = View.VISIBLE
        } else {
            // Jelajah & Populer tab header
            layoutSearchBar.visibility = View.VISIBLE
            scrollCategories.visibility = View.VISIBLE
            layoutProfileHeaderInfo.visibility = View.GONE
        }

        updateBottomNavSelection(tabIndex)
    }

    private fun updateBottomNavSelection(selectedIndex: Int) {
        val goldColor = ContextCompat.getColor(this, R.color.gold)
        val mutedColor = Color.parseColor("#B3F7F3E9")

        // Explore
        ivNavExplore.imageTintList = ColorStateList.valueOf(if (selectedIndex == 0) goldColor else mutedColor)
        tvNavExplore.setTextColor(if (selectedIndex == 0) goldColor else mutedColor)
        tvNavExplore.typeface = if (selectedIndex == 0) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT

        // Popular
        ivNavPopular.imageTintList = ColorStateList.valueOf(if (selectedIndex == 1) goldColor else mutedColor)
        tvNavPopular.setTextColor(if (selectedIndex == 1) goldColor else mutedColor)
        tvNavPopular.typeface = if (selectedIndex == 1) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT

        // Profile
        ivNavProfile.imageTintList = ColorStateList.valueOf(if (selectedIndex == 2) goldColor else mutedColor)
        tvNavProfile.setTextColor(if (selectedIndex == 2) goldColor else mutedColor)
        tvNavProfile.typeface = if (selectedIndex == 2) android.graphics.Typeface.DEFAULT_BOLD else android.graphics.Typeface.DEFAULT
    }

    private fun setupPopularTabClicks() {
        // Hero Card (#1 Terpopuler: Bakpia Pathok)
        findViewById<View>(R.id.cardHeroPopular).setOnClickListener {
            openDetail("bakpia-pathok")
        }
        // Rank 2: Gudeg Kendil
        findViewById<View>(R.id.cardRank2).setOnClickListener {
            openDetail("gudeg-kendil")
        }
        // Rank 3: Wedang Uwuh
        findViewById<View>(R.id.cardRank3).setOnClickListener {
            openDetail("wedang-uwuh")
        }
        // Rank 4: Yangko Kotagede
        findViewById<View>(R.id.cardRank4).setOnClickListener {
            openDetail("yangko-kotagede")
        }
        // Rank 5: Geplak Bantul
        findViewById<View>(R.id.cardRank5).setOnClickListener {
            openDetail("geplak-bantul")
        }
    }

    private fun setupProfileTabClicks() {
        // Masuk / Daftar button
        findViewById<View>(R.id.btnLoginRegister).setOnClickListener {
            Toast.makeText(this, "Fitur Akun Pelancong segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // 1. Oleh-oleh Tersimpan
        findViewById<View>(R.id.menuSaved).setOnClickListener {
            switchTab(0)
            isBookmarkFilterOnly = true
            updateBookmarkFilterUi()
            filterList()
        }

        // 2. Toko Terdekat
        findViewById<View>(R.id.menuNearby).setOnClickListener {
            showNearbyStoresDialog()
        }

        // 3. Pilihan Populer
        findViewById<View>(R.id.menuPopular).setOnClickListener {
            switchTab(1)
        }

        // 4. Kalkulator Belanja & Bagasi (Fitur Baru)
        findViewById<View>(R.id.menuCalculator).setOnClickListener {
            showCalculatorDialog()
        }

        // 5. Pusat Bantuan
        findViewById<View>(R.id.menuHelp).setOnClickListener {
            showHelpDialog()
        }

        // 6. Tentang OlehOlehJogja
        findViewById<View>(R.id.menuAbout).setOnClickListener {
            showGuideDialog()
        }
    }

    private fun openDetail(souvenirId: String) {
        val item = SouvenirData.getById(souvenirId) ?: return
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("souvenir", item)
            putExtra("souvenir_id", item.id)
        }
        startActivity(intent)
    }

    private fun filterList() {
        val bookmarkedIds = BookmarkManager.getBookmarkedIds(this)

        val filtered = SouvenirData.list.filter { item ->
            val matchesCategory = (currentCategory == "semua") || (item.category.equals(currentCategory, ignoreCase = true))
            val matchesSearch = searchQuery.isEmpty() ||
                    item.name.contains(searchQuery, ignoreCase = true) ||
                    item.subtitle.contains(searchQuery, ignoreCase = true) ||
                    item.description.contains(searchQuery, ignoreCase = true)
            val matchesBookmark = !isBookmarkFilterOnly || bookmarkedIds.contains(item.id)

            matchesCategory && matchesSearch && matchesBookmark
        }

        adapter.updateData(filtered)

        if (filtered.isEmpty()) {
            rvSouvenirs.visibility = View.GONE
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            rvSouvenirs.visibility = View.VISIBLE
            layoutEmptyState.visibility = View.GONE
        }
    }

    private fun resetAllFilters() {
        currentCategory = "semua"
        searchQuery = ""
        isBookmarkFilterOnly = false
        etSearch.text.clear()
        updateCategoryChipsUi()
        updateBookmarkFilterUi()
        filterList()
    }

    private fun showGuideDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_about)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<View>(R.id.btnDismissAbout).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showNearbyStoresDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_stores)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<View>(R.id.btnMapPathuk)?.setOnClickListener {
            openMaps("Sentra Industri Bakpia Pathuk, Jl. KS Tubun, Yogyakarta")
        }
        dialog.findViewById<View>(R.id.btnMapWijilan)?.setOnClickListener {
            openMaps("Sentra Gudeg Wijilan, Kraton, Yogyakarta")
        }
        dialog.findViewById<View>(R.id.btnMapBeringharjo)?.setOnClickListener {
            openMaps("Pasar Beringharjo, Malioboro, Yogyakarta")
        }
        dialog.findViewById<View>(R.id.btnMapKotagede)?.setOnClickListener {
            openMaps("Sentra Yangko dan Cokelat Monggo, Kotagede, Yogyakarta")
        }
        dialog.findViewById<View>(R.id.btnMapBantul)?.setOnClickListener {
            openMaps("Sentra Geplak Mbok Tumpuk, Jl. Wahid Hasyim, Bantul")
        }

        dialog.findViewById<View>(R.id.btnDismissStores).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showHelpDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_help)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<View>(R.id.btnDismissHelp).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showCalculatorDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_calculator)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val container = dialog.findViewById<LinearLayout>(R.id.layoutCalcItemsContainer)
        val tvCalcTotalPrice = dialog.findViewById<TextView>(R.id.tvCalcTotalPrice)
        val tvCalcTotalWeight = dialog.findViewById<TextView>(R.id.tvCalcTotalWeight)
        val tvCalcStatus = dialog.findViewById<TextView>(R.id.tvCalcStatus)
        val btnClearCart = dialog.findViewById<TextView>(R.id.btnClearCart)
        val btnCalcAddMore = dialog.findViewById<View>(R.id.btnCalcAddMore)

        fun refreshCalcView() {
            container.removeAllViews()
            val cartItems = CartManager.getItems()
            val displayItems = mutableListOf<Pair<Souvenir, Int>>()

            if (cartItems.isNotEmpty()) {
                cartItems.forEach { (id, count) ->
                    val souvenir = SouvenirData.getById(id)
                    if (souvenir != null) {
                        displayItems.add(Pair(souvenir, count))
                    }
                }
            } else {
                val defaultIds = listOf("bakpia-pathok", "gudeg-kendil", "yangko-kotagede", "wedang-uwuh")
                defaultIds.forEach { id ->
                    val souvenir = SouvenirData.getById(id)
                    if (souvenir != null) {
                        displayItems.add(Pair(souvenir, 0))
                    }
                }
            }

            var totalCost = 0
            var totalWeightGrams = 0

            displayItems.forEach { (souvenir, count) ->
                val rowView = LayoutInflater.from(this).inflate(R.layout.item_calc_row, container, false)
                val tvName = rowView.findViewById<TextView>(R.id.tvCalcItemName)
                val tvUnit = rowView.findViewById<TextView>(R.id.tvCalcItemUnit)
                val tvCount = rowView.findViewById<TextView>(R.id.tvCalcCount)
                val btnMinus = rowView.findViewById<TextView>(R.id.btnCalcMinus)
                val btnPlus = rowView.findViewById<TextView>(R.id.btnCalcPlus)

                val itemPrice = souvenir.priceValue
                val itemWeight = souvenir.estimatedWeightGrams

                tvName.text = souvenir.name
                tvUnit.text = "~${itemWeight}g • Rp ${String.format("%,d", itemPrice).replace(',', '.')}"
                tvCount.text = count.toString()

                totalCost += count * itemPrice
                totalWeightGrams += count * itemWeight

                btnMinus.setOnClickListener {
                    if (count > 0) {
                        CartManager.removeItem(souvenir.id)
                        updateCartBadge()
                        refreshCalcView()
                    }
                }

                btnPlus.setOnClickListener {
                    CartManager.addItem(souvenir.id)
                    updateCartBadge()
                    refreshCalcView()
                }

                container.addView(rowView)
            }

            val totalKg = totalWeightGrams / 1000.0
            tvCalcTotalPrice.text = String.format("Rp %,d", totalCost).replace(',', '.')
            tvCalcTotalWeight.text = String.format("%.1f kg", totalKg)

            if (totalKg <= 7.0) {
                tvCalcStatus.text = "✈️ Aman untuk Bagasi Kabin (Maks 7 kg)"
                tvCalcStatus.setTextColor(ContextCompat.getColor(this, R.color.sogan))
            } else {
                tvCalcStatus.text = "⚠️ Melebihi 7 kg (Disarankan Masuk Bagasi Terdaftar)"
                tvCalcStatus.setTextColor(ContextCompat.getColor(this, R.color.terracotta))
            }
        }

        btnClearCart.setOnClickListener {
            CartManager.clear()
            updateCartBadge()
            refreshCalcView()
            Toast.makeText(this, "Keranjang dikosongkan", Toast.LENGTH_SHORT).show()
        }

        btnCalcAddMore.setOnClickListener {
            showAddItemsDialog(onItemAdded = {
                refreshCalcView()
            })
        }

        dialog.findViewById<View>(R.id.btnDismissCalc).setOnClickListener {
            dialog.dismiss()
        }

        refreshCalcView()
        dialog.show()
    }

    private fun showAddItemsDialog(onItemAdded: () -> Unit) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val dialogView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val p = (16 * resources.displayMetrics.density).toInt()
            setPadding(p, p, p, p)
            background = ContextCompat.getDrawable(context, R.drawable.bg_dialog)
        }

        val title = TextView(this).apply {
            text = "Pilih Oleh-Oleh Ditambahkan"
            textSize = 15f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(context, R.color.sogan))
            setPadding(8, 8, 8, 16)
        }
        dialogView.addView(title)

        val scrollView = NestedScrollView(this)
        val itemsContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        SouvenirData.list.forEach { souvenir ->
            val row = LayoutInflater.from(this).inflate(R.layout.item_calc_row, itemsContainer, false)
            val tvName = row.findViewById<TextView>(R.id.tvCalcItemName)
            val tvUnit = row.findViewById<TextView>(R.id.tvCalcItemUnit)
            val tvCount = row.findViewById<TextView>(R.id.tvCalcCount)
            val btnMinus = row.findViewById<TextView>(R.id.btnCalcMinus)
            val btnPlus = row.findViewById<TextView>(R.id.btnCalcPlus)

            tvName.text = souvenir.name
            tvUnit.text = "${souvenir.shelfLife} • ${souvenir.priceRange}"
            val currentCount = CartManager.getItemCount(souvenir.id)
            tvCount.text = currentCount.toString()

            btnMinus.setOnClickListener {
                if (CartManager.getItemCount(souvenir.id) > 0) {
                    CartManager.removeItem(souvenir.id)
                    tvCount.text = CartManager.getItemCount(souvenir.id).toString()
                    updateCartBadge()
                    onItemAdded()
                }
            }

            btnPlus.setOnClickListener {
                CartManager.addItem(souvenir.id)
                tvCount.text = CartManager.getItemCount(souvenir.id).toString()
                updateCartBadge()
                onItemAdded()
            }

            itemsContainer.addView(row)
        }

        scrollView.addView(itemsContainer)
        dialogView.addView(scrollView)

        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            (resources.displayMetrics.heightPixels * 0.70).toInt()
        )
        dialog.show()
    }

    private fun openMaps(query: String) {
        try {
            val uri = android.net.Uri.parse("geo:0,0?q=" + android.net.Uri.encode(query))
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://www.google.com/maps/search/?api=1&query=" + android.net.Uri.encode(query)))
                startActivity(browserIntent)
            }
        } catch (e: Exception) {
            val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://www.google.com/maps/search/?api=1&query=" + android.net.Uri.encode(query)))
            startActivity(browserIntent)
        }
    }
}
