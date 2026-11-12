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
import com.google.android.material.bottomsheet.BottomSheetDialog

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
    private lateinit var tvEmptyTitle: TextView
    private lateinit var tvEmptyDesc: TextView
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
    private lateinit var tvProfileStatCart: TextView
    private lateinit var tvMenuSavedBadge: TextView
    private lateinit var tvMenuCartBadge: TextView
    private lateinit var layoutStatSaved: View
    private lateinit var layoutStatCart: View

    // Category chips
    private lateinit var chips: List<Pair<TextView, String>>

    // State
    private var currentTab: Int = 0 // 0: Jelajah, 1: Populer, 2: Profil
    private var currentCategory: String = "semua"
    private var searchQuery: String = ""
    private var isBookmarkFilterOnly: Boolean = false
    private var cartNotificationRunnable: Runnable? = null

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

        if (intent?.getBooleanExtra("open_cart", false) == true) {
            showCalculatorDialog()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.getBooleanExtra("open_cart", false)) {
            showCalculatorDialog()
        }
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
        tvEmptyTitle = findViewById(R.id.tvEmptyTitle)
        tvEmptyDesc = findViewById(R.id.tvEmptyDesc)
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
        tvProfileStatCart = findViewById(R.id.tvProfileStatCart)
        tvMenuSavedBadge = findViewById(R.id.tvMenuSavedBadge)
        tvMenuCartBadge = findViewById(R.id.tvMenuCartBadge)
        layoutStatSaved = findViewById(R.id.layoutStatSaved)
        layoutStatCart = findViewById(R.id.layoutStatCart)

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
                updateProfileSavedStat()
                showAddToCartNotice(item)
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
        val cartCount = CartManager.getTotalItemCount()
        tvProfileStatSaved.text = count.toString()
        tvProfileStatCart.text = cartCount.toString()
        tvMenuSavedBadge.text = "$count item"
        tvMenuCartBadge.text = "$cartCount item"
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
            updateProfileSavedStat()
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
        findViewById<View>(R.id.btnCartHero).setOnClickListener {
            addPopularToCart("bakpia-pathok")
        }

        // Rank 2: Gudeg Kendil
        findViewById<View>(R.id.cardRank2).setOnClickListener {
            openDetail("gudeg-kendil")
        }
        findViewById<View>(R.id.btnCartRank2).setOnClickListener {
            addPopularToCart("gudeg-kendil")
        }

        // Rank 3: Wedang Uwuh
        findViewById<View>(R.id.cardRank3).setOnClickListener {
            openDetail("wedang-uwuh")
        }
        findViewById<View>(R.id.btnCartRank3).setOnClickListener {
            addPopularToCart("wedang-uwuh")
        }

        // Rank 4: Yangko Kotagede
        findViewById<View>(R.id.cardRank4).setOnClickListener {
            openDetail("yangko-kotagede")
        }
        findViewById<View>(R.id.btnCartRank4).setOnClickListener {
            addPopularToCart("yangko-kotagede")
        }

        // Rank 5: Geplak Bantul
        findViewById<View>(R.id.cardRank5).setOnClickListener {
            openDetail("geplak-bantul")
        }
        findViewById<View>(R.id.btnCartRank5).setOnClickListener {
            addPopularToCart("geplak-bantul")
        }
    }

    private fun addPopularToCart(souvenirId: String) {
        val item = SouvenirData.getById(souvenirId) ?: return
        CartManager.addItem(item.id)
        updateCartBadge()
        updateProfileSavedStat()
        showAddToCartNotice(item)
    }

    private fun showAddToCartNotice(souvenir: Souvenir) {
        val card = findViewById<View>(R.id.cardCartNotification) ?: return
        val ivThumb = findViewById<ImageView>(R.id.ivToastThumb)
        val tvTitle = findViewById<TextView>(R.id.tvToastTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvToastSubtitle)
        val btnOpen = findViewById<View>(R.id.btnToastOpenCart)

        ivThumb?.setImageResource(souvenir.imageResId)
        tvTitle?.text = "${souvenir.name} Masuk Keranjang!"
        val totalCount = CartManager.getTotalItemCount()
        val currentCount = CartManager.getItemCount(souvenir.id)
        tvSubtitle?.text = "$currentCount pcs dipilih • Total $totalCount item di keranjang"

        btnOpen?.setOnClickListener {
            card.visibility = View.GONE
            showCalculatorDialog()
        }

        cartNotificationRunnable?.let { card.removeCallbacks(it) }
        card.alpha = 0f
        card.visibility = View.VISIBLE
        card.animate().alpha(1f).setDuration(220).start()

        val runnable = Runnable {
            card.animate().alpha(0f).setDuration(250).withEndAction {
                card.visibility = View.GONE
            }.start()
        }
        cartNotificationRunnable = runnable
        card.postDelayed(runnable, 3500)
    }

    private fun setupProfileTabClicks() {
        // 1. Stats Card Clicks
        layoutStatSaved.setOnClickListener {
            switchTab(0)
            isBookmarkFilterOnly = true
            updateBookmarkFilterUi()
            filterList()
        }

        layoutStatCart.setOnClickListener {
            showCalculatorDialog()
        }

        // 2. Menu Item: Oleh-oleh Tersimpan
        findViewById<View>(R.id.menuSaved).setOnClickListener {
            switchTab(0)
            isBookmarkFilterOnly = true
            updateBookmarkFilterUi()
            filterList()
        }

        // 3. Menu Item: Keranjang Belanja & Kalkulator
        findViewById<View>(R.id.menuCalculator).setOnClickListener {
            showCalculatorDialog()
        }

        // 4. Menu Item: Toko Terdekat
        findViewById<View>(R.id.menuNearby).setOnClickListener {
            showNearbyStoresDialog()
        }

        // 5. Menu Item: Pilihan Populer
        findViewById<View>(R.id.menuPopular).setOnClickListener {
            switchTab(1)
        }

        // 6. Menu Item: Pusat Bantuan
        findViewById<View>(R.id.menuHelp).setOnClickListener {
            showHelpDialog()
        }

        // 7. Menu Item: Tentang OlehOlehJogja
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
            if (isBookmarkFilterOnly) {
                tvEmptyTitle.text = "Belum Ada Tersimpan"
                tvEmptyDesc.text = "Klik ikon bookmark pada kartu oleh-oleh untuk menyimpannya ke daftar favorit Anda."
                (btnResetFilter as? TextView)?.text = "Jelajahi Semua"
            } else {
                tvEmptyTitle.text = getString(R.string.empty_title)
                tvEmptyDesc.text = getString(R.string.empty_desc)
                (btnResetFilter as? TextView)?.text = getString(R.string.reset_filter)
            }
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
        val dialog = BottomSheetDialog(this)
        val sheetView = LayoutInflater.from(this).inflate(R.layout.dialog_calculator, null)
        dialog.setContentView(sheetView)

        val layoutCalcEmpty = sheetView.findViewById<LinearLayout>(R.id.layoutCalcEmpty)
        val layoutCalcFilled = sheetView.findViewById<LinearLayout>(R.id.layoutCalcFilled)
        val container = sheetView.findViewById<LinearLayout>(R.id.layoutCalcItemsContainer)
        val tvCalcTotalPrice = sheetView.findViewById<TextView>(R.id.tvCalcTotalPrice)
        val tvCalcTotalItems = sheetView.findViewById<TextView>(R.id.tvCalcTotalItems)
        val tvCalcTotalWeight = sheetView.findViewById<TextView>(R.id.tvCalcTotalWeight)
        val tvCalcStatus = sheetView.findViewById<TextView>(R.id.tvCalcStatus)
        val btnClearCart = sheetView.findViewById<TextView>(R.id.btnClearCart)
        val btnDismissCalcTop = sheetView.findViewById<View>(R.id.btnDismissCalcTop)
        val btnCalcAddMore = sheetView.findViewById<View>(R.id.btnCalcAddMore)
        val btnDismissCalc = sheetView.findViewById<View>(R.id.btnDismissCalc)
        val btnEmptyStartShopping = sheetView.findViewById<View>(R.id.btnEmptyStartShopping)

        fun refreshCalcView() {
            container.removeAllViews()
            val cartItems = CartManager.getItems()
            val totalQuantity = CartManager.getTotalItemCount()

            if (cartItems.isEmpty() || totalQuantity == 0) {
                layoutCalcEmpty.visibility = View.VISIBLE
                layoutCalcFilled.visibility = View.GONE
                btnClearCart.visibility = View.GONE
                return
            }

            layoutCalcEmpty.visibility = View.GONE
            layoutCalcFilled.visibility = View.VISIBLE
            btnClearCart.visibility = View.VISIBLE

            var totalCost = 0
            var totalWeightGrams = 0

            cartItems.forEach { (id, count) ->
                if (count > 0) {
                    val souvenir = SouvenirData.getById(id)
                    if (souvenir != null) {
                        val rowView = LayoutInflater.from(this).inflate(R.layout.item_calc_row, container, false)
                        val ivThumb = rowView.findViewById<ImageView>(R.id.ivCalcThumb)
                        val tvName = rowView.findViewById<TextView>(R.id.tvCalcItemName)
                        val tvUnit = rowView.findViewById<TextView>(R.id.tvCalcItemUnit)
                        val tvSubtotal = rowView.findViewById<TextView>(R.id.tvCalcItemSubtotal)
                        val tvCount = rowView.findViewById<TextView>(R.id.tvCalcCount)
                        val btnMinus = rowView.findViewById<TextView>(R.id.btnCalcMinus)
                        val btnPlus = rowView.findViewById<TextView>(R.id.btnCalcPlus)
                        val btnDelete = rowView.findViewById<View>(R.id.btnCalcDelete)

                        val itemPrice = souvenir.priceValue
                        val itemWeight = souvenir.estimatedWeightGrams
                        val subtotal = count * itemPrice

                        ivThumb.setImageResource(souvenir.imageResId)
                        tvName.text = souvenir.name
                        tvUnit.text = "~${itemWeight}g • Rp ${String.format("%,d", itemPrice).replace(',', '.')}"
                        tvSubtotal.text = "Subtotal: Rp ${String.format("%,d", subtotal).replace(',', '.')}"
                        tvCount.text = count.toString()

                        totalCost += subtotal
                        totalWeightGrams += count * itemWeight

                        btnMinus.setOnClickListener {
                            CartManager.removeItem(souvenir.id)
                            updateCartBadge()
                            refreshCalcView()
                        }

                        btnPlus.setOnClickListener {
                            CartManager.addItem(souvenir.id)
                            updateCartBadge()
                            refreshCalcView()
                        }

                        btnDelete.setOnClickListener {
                            CartManager.setItemCount(souvenir.id, 0)
                            updateCartBadge()
                            refreshCalcView()
                            Toast.makeText(this, "${souvenir.name} dihapus dari keranjang", Toast.LENGTH_SHORT).show()
                        }

                        container.addView(rowView)
                    }
                }
            }

            val totalKg = totalWeightGrams / 1000.0
            tvCalcTotalPrice.text = String.format("Rp %,d", totalCost).replace(',', '.')
            tvCalcTotalItems.text = "Total Item: $totalQuantity pcs"
            tvCalcTotalWeight.text = String.format("%.1f kg", totalKg)

            if (totalKg <= 7.0) {
                tvCalcStatus.text = "✈️ Aman untuk Bagasi Kabin (Maks 7 kg)"
                tvCalcStatus.setTextColor(ContextCompat.getColor(this, R.color.sogan))
                tvCalcStatus.setBackgroundResource(R.drawable.bg_badge_shelf)
            } else {
                tvCalcStatus.text = "⚠️ Melebihi 7 kg (Disarankan Masuk Bagasi Terdaftar)"
                tvCalcStatus.setTextColor(ContextCompat.getColor(this, R.color.terracotta))
                tvCalcStatus.setBackgroundResource(R.drawable.bg_badge_warning)
            }
        }

        btnEmptyStartShopping.setOnClickListener {
            dialog.dismiss()
            switchTab(0)
        }

        btnClearCart.setOnClickListener {
            CartManager.clear()
            updateCartBadge()
            refreshCalcView()
            Toast.makeText(this, "Keranjang belanja dikosongkan", Toast.LENGTH_SHORT).show()
        }

        btnDismissCalcTop.setOnClickListener {
            dialog.dismiss()
        }

        btnDismissCalc.setOnClickListener {
            dialog.dismiss()
        }

        btnCalcAddMore.setOnClickListener {
            showAddItemsDialog(onItemAdded = {
                refreshCalcView()
            })
        }

        refreshCalcView()
        dialog.show()
    }

    private fun showAddItemsDialog(onItemAdded: () -> Unit) {
        val dialog = BottomSheetDialog(this)
        val sheetView = LayoutInflater.from(this).inflate(R.layout.dialog_add_items, null)
        dialog.setContentView(sheetView)

        val container = sheetView.findViewById<LinearLayout>(R.id.layoutAddItemsContainer)
        val tvSummaryCount = sheetView.findViewById<TextView>(R.id.tvAddItemsSummaryCount)
        val tvSummaryPrice = sheetView.findViewById<TextView>(R.id.tvAddItemsSummaryPrice)
        val btnClose = sheetView.findViewById<View>(R.id.btnAddItemsClose)
        val btnDone = sheetView.findViewById<View>(R.id.btnAddItemsDone)

        fun updateSummary() {
            val totalQty = CartManager.getTotalItemCount()
            var totalPrice = 0
            CartManager.getItems().forEach { (id, count) ->
                val s = SouvenirData.getById(id)
                if (s != null) {
                    totalPrice += count * s.priceValue
                }
            }
            tvSummaryCount.text = "$totalQty item di keranjang"
            tvSummaryPrice.text = "Estimasi Total: Rp ${String.format("%,d", totalPrice).replace(',', '.')}"
        }

        container.removeAllViews()
        SouvenirData.list.forEach { souvenir ->
            val row = LayoutInflater.from(this).inflate(R.layout.item_add_picker_row, container, false)
            val ivThumb = row.findViewById<ImageView>(R.id.ivPickerThumb)
            val tvName = row.findViewById<TextView>(R.id.tvPickerName)
            val tvCategory = row.findViewById<TextView>(R.id.tvPickerCategory)
            val tvPrice = row.findViewById<TextView>(R.id.tvPickerPrice)
            val tvCount = row.findViewById<TextView>(R.id.tvPickerCount)
            val btnMinus = row.findViewById<TextView>(R.id.btnPickerMinus)
            val btnPlus = row.findViewById<TextView>(R.id.btnPickerPlus)

            ivThumb.setImageResource(souvenir.imageResId)
            tvName.text = souvenir.name
            tvCategory.text = "${souvenir.categoryLabel} • Tahan ${souvenir.shelfLife}"
            tvPrice.text = "Rp ${String.format("%,d", souvenir.priceValue).replace(',', '.')} • ~${souvenir.estimatedWeightGrams}g"

            var count = CartManager.getItemCount(souvenir.id)
            tvCount.text = count.toString()

            btnMinus.setOnClickListener {
                if (count > 0) {
                    CartManager.removeItem(souvenir.id)
                    count = CartManager.getItemCount(souvenir.id)
                    tvCount.text = count.toString()
                    updateCartBadge()
                    updateProfileSavedStat()
                    updateSummary()
                    onItemAdded()
                }
            }

            btnPlus.setOnClickListener {
                CartManager.addItem(souvenir.id)
                count = CartManager.getItemCount(souvenir.id)
                tvCount.text = count.toString()
                updateCartBadge()
                updateProfileSavedStat()
                updateSummary()
                onItemAdded()
            }

            container.addView(row)
        }

        updateSummary()

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnDone.setOnClickListener {
            dialog.dismiss()
        }

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
