package com.example.oleholehjogja

object SouvenirData {
    val list = listOf(
        Souvenir(
            id = "bakpia-pathok",
            name = "Bakpia Pathok",
            subtitle = "Sentra Pathuk, Ngampil",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "3 - 5 Hari",
            priceRange = "Rp 25.000",
            imageResId = R.drawable.img_bakpia,
            isPopular = true,
            description = "Bakpia Pathok adalah kue pastry bulat legendaris khas Yogyakarta berlapis kulit renyah dengan isian kumbu kacang hijau manis lembut. Berawal dari pemukiman Tionghoa di kawasan Pathuk sejak tahun 1948, bakpia dipanggang secara tradisional menggunakan tungku arang batok kelapa untuk menghasilkan aroma smokey yang khas dan kulit yang gurih berlapis tipis. Hingga kini, Bakpia Pathok telah berkembang dengan beragam varian modern seperti cokelat lumer, keju gurih, dan kumbu hitam tanpa meninggalkan resep klasiknya.",
            quote = "\"Pilihan utama buah tangan keluarga Nusantara sejak paruh abad ke-20.\"",
            variants = listOf(
                Variant("Kacang Hijau Original", "Isian paling otentik dengan olahan kacang hijau lembut beraroma vanila."),
                Variant("Cokelat & Keju Melt", "Varian modern favorit anak muda dengan lelehan cokelat kental dan keju gurih."),
                Variant("Kumbu Hitam Klasik", "Isian kacang tolo hitam tradisional dengan cita rasa gurih manis mendalam."),
                Variant("Kukus Aneka Rasa", "Tekstur spons bolu kekinian yang lumer di mulut.")
            ),
            packingTips = listOf(
                "Pilih varian Bakpia Kering panggang kering untuk ketahanan perjalanan lebih dari 24 jam.",
                "Sangat aman dibawa ke dalam bagasi kabin pesawat tanpa kendala batas cairan.",
                "Hindari tertindih barang berat dalam koper; gunakan kardus khusus berhandle jinjing."
            ),
            locations = listOf(
                "Sentra Industri Bakpia Pathuk (Sepanjang Jl. KS Tubun, Ngampilan)",
                "Outlet Resmi Bakpia Pathok 25 (Kawasan Stasiun Tugu & Jl. AIPDA KS Tubun)",
                "Pasar Tradisional Beringharjo Lantai 1"
            )
        ),
        Souvenir(
            id = "gudeg-kendil",
            name = "Gudeg Kendil",
            subtitle = "Sentra Wijilan & Barek",
            category = "basah",
            categoryLabel = "Makanan Basah",
            shelfLife = "2 Hari / 1 Thn",
            priceRange = "Rp 40.000",
            imageResId = R.drawable.img_gudeg,
            isPopular = true,
            description = "Gudeg Kendil adalah mahakarya kuliner tradisional Yogyakarta yang terbuat dari nangka muda (gori) yang dimasak perlahan bersama santan kental, gula merah kelapa, dan racikan rempah Jawa selama berjam-jam hingga berwarna cokelat kemerahan pekat. Gudeg khas Jogja cenderung kering dengan rasa manis gurih yang legit, disajikan bersama krecek pedas berkuah kental, telur pindang bacem, tahu-tempe bacem, dan ayam kampung suwir berempah. Dikemas dalam kendil tanah liat gerabah tradisional atau kaleng steril modern yang tahan hingga satu tahun.",
            quote = "\"Warisan resep rahasia dapur keraton yang membawa cita rasa manis Jogja mendunia.\"",
            variants = listOf(
                Variant("Gudeg Komplit Ayam Kampung", "Paket lengkap berisi nangka, telur bacem, ayam suwir, dan sambal krecek gurih pedas."),
                Variant("Gudeg Kaleng Steril", "Inovasi kaleng vakum tanpa bahan pengawet kimia yang tahan hingga 1 tahun perjalanan."),
                Variant("Gudeg Kering Sambal Krecek", "Kadar air minim yang dipersiapkan khusus agar tidak cepat basi di perjalanan darat.")
            ),
            packingTips = listOf(
                "Untuk gudeg kendil basah, wajib dimasukkan bagasi kargo pesawat jika mengandung kuah santan.",
                "Pilih kemasan Gudeg Kaleng jika bepergian antarpulau atau penerbangan jarak jauh.",
                "Segera hangatkan atau kukus gudeg kendil sesampainya di kota tujuan."
            ),
            locations = listOf(
                "Sentra Gudeg Wijilan (Kawasan Plengkung Wijilan dekat Kraton Yogyakarta)",
                "Gudeg Yu Djum Pusat (Jl. Kaliurang KM 4.5, Barek, Sleman)",
                "Gudeg Bu Tjitro 1925 (Jl. Janti No. 330, Yogyakarta)"
            )
        ),
        Souvenir(
            id = "wedang-uwuh",
            name = "Wedang Uwuh",
            subtitle = "Imogiri, Bantul",
            category = "minuman",
            categoryLabel = "Minuman Tradisional",
            shelfLife = "6 - 12 Bulan",
            priceRange = "Rp 15.000",
            imageResId = R.drawable.img_wedang,
            isPopular = true,
            description = "Wedang Uwuh secara harfiah berarti 'minuman sampah' dalam bahasa Jawa karena tampilannya yang menyerupai tumpukan dedaunan rempah kering. Minuman khas makam raja-raja Imogiri ini diracik dari kayu secang (yang memberi warna merah delima bening alami), kayu manis, cengkeh, jahe emprit bakar, pala, gagang cengkeh, dan gula batu kristal. Minuman ini berkhasiat melancarkan peredaran darah, meredakan masuk angin, menghangatkan tubuh, dan kaya akan antioksidan alami.",
            quote = "\"Seduhan rempah merah hangat penolak dingin dan penjaga stamina tubuh alami.\"",
            variants = listOf(
                Variant("Rajangan Rempah Utuh", "Kemasan rempah kering klasik yang diseduh air mendidih untuk rasa rempah paling tajam."),
                Variant("Wedang Uwuh Celup", "Inovasi kantong saring modern tanpa ampas daun, praktis untuk diseduh di kantor."),
                Variant("Sirup Konsentrat Uwuh", "Ekstrak cair rempah secang yang mudah disajikan hangat maupun dingin bersama es batu.")
            ),
            packingTips = listOf(
                "Kemasannya sangat ringan, kering, dan aman dimasukkan ke dalam koper maupun tas kabin.",
                "Simpan pada suhu ruang kering dan terhindar dari sinar matahari langsung.",
                "Pilih kemasan mika transparan bersegel rapat untuk masa simpan hingga satu tahun."
            ),
            locations = listOf(
                "Sentra Perajin Wedang Uwuh (Kawasan Wisata Makam Imogiri, Bantul)",
                "Pasar Tradisional Beringharjo Lantai 2",
                "Toko Oleh-Oleh Mirota / Hamzah Batik Jl. Malioboro"
            )
        ),
        Souvenir(
            id = "yangko-kotagede",
            name = "Yangko Kotagede",
            subtitle = "Kotagede, Yogyakarta",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "2 - 3 Minggu",
            priceRange = "Rp 18.000",
            imageResId = R.drawable.img_yangko,
            isPopular = true,
            description = "Yangko adalah kue kenyal manis berbentuk persegi empat kecil yang terbuat dari tepung ketan bermutu tinggi dengan baluran tepung terigu sangrai halus agar tidak lengket di tangan. Konon penganan ini dahulu merupakan bekal para prajurit dan bangsawan Kerajaan Mataram Islam saat berperang karena daya tahannya yang cukup lama tanpa bahan pengawet. Teksturnya yang kenyal lembut dengan isian cincangan kacang tanah manis gurih sering dijuluki sebagai 'Mochi-nya Yogyakarta'.",
            quote = "\"Mochi tradisional warisan Kerajaan Mataram Islam yang lembut kenyal di setiap gigitan.\"",
            variants = listOf(
                Variant("Kacang Tanah Klasik", "Isian cincang kacang tanah gurih manis dengan resep turun-temurun sejak era 1920-an."),
                Variant("Kombinasi Buah Tropis", "Kotak aneka rasa modern: stroberi, durian, nangka, pandan, dan melon."),
                Variant("Cokelat Lumer", "Varian favorit anak-anak dengan lelehan cokelat manis lembut di bagian tengah.")
            ),
            packingTips = listOf(
                "Hindari meletakkan kardus yangko di tempat bersuhu panas langsung agar tepung tidak lembap.",
                "Jaga agar kemasan tidak terhimpit benda keras di dalam bagasi.",
                "Tahan hingga 3 minggu pada suhu ruangan sejuk."
            ),
            locations = listOf(
                "Yangko Pak Paku (Jl. Mondorakan, Kotagede, Yogyakarta)",
                "Yangko Giant (Jl. Magelang KM 5 & Kotagede)",
                "Sentra Pusat Oleh-Oleh Jl. Solo dan Jl. Malioboro"
            )
        ),
        Souvenir(
            id = "geplak-bantul",
            name = "Geplak Bantul",
            subtitle = "Pasar Bantul, Bantul",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "7 - 10 Hari",
            priceRange = "Rp 20.000",
            imageResId = R.drawable.img_geplak,
            isPopular = true,
            description = "Geplak adalah jajanan khas Kabupaten Bantul yang terbuat dari perpaduan parutan kelapa muda segar, gula pasir murni, dan aroma vanila atau pandan yang dimasak padat lalu dibentuk bulat-bulat khas. Geplak sangat terkenal dengan warna-warninya yang cerah memikat mata seperti merah muda, hijau muda, kuning, dan putih. Di masa lampau saat Bantul menjadi lumbung tebu dan pabrik gula di era kolonial, masyarakat menciptakan geplak sebagai camilan manis pendamping teh pahit nasgitel di sore hari.",
            quote = "\"Camilan manis warna-warni penghibur lidah pelancong pencinta kuliner otentik Jawa.\"",
            variants = listOf(
                Variant("Pelangi Aneka Rasa", "Campuran warna-warni cerah dengan aroma stroberi, pandan, vanila, dan jeruk."),
                Variant("Geplak Gula Jawa Murni", "Varian cokelat karamel menggunakan gula kelapa asli khas pedesaan Bantul."),
                Variant("Geplak Waluh (Labu Kuning)", "Inovasi geplak berbahan labu kuning alami yang kaya serat dan lebih lembut.")
            ),
            packingTips = listOf(
                "Pilih kemasan besek bambu tradisional yang memiliki sirkulasi udara baik agar kelapa tidak lekas asam.",
                "Konsumsi dalam waktu 7 hingga 10 hari sejak pembelian untuk cita rasa kelapa paling segar.",
                "Dapat disimpan di lemari es untuk menambah daya simpan hingga 2 minggu."
            ),
            locations = listOf(
                "Sentra Geplak Mbok Tumpuk (Jl. Wahid Hasyim No. 104, Bantul)",
                "Toko Geplak Jago (Jl. Wachid Hasyim, Sumuran, Palbapang, Bantul)",
                "Pasar Seni dan Kerajinan Gabusan Bantul"
            )
        ),
        Souvenir(
            id = "bakpia-kukus",
            name = "Bakpia Kukus Tugu",
            subtitle = "Stasiun Tugu & Jl. Solo",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "5 - 7 Hari",
            priceRange = "Rp 35.000",
            imageResId = R.drawable.img_bakpia_kukus,
            isPopular = true,
            description = "Bakpia Kukus Tugu Jogja merupakan terobosan inovasi kuliner modern yang menggabungkan konsep bakpia tradisional dengan bolu kukus super lembut bergaya modern. Kue ini berbentuk bulat mengembang dengan tekstur spons yang sangat halus dan berisi pasta lava lezat yang melimpah di dalamnya. Begitu digigit, isian cokelat atau keju di dalamnya akan lumer memanjakan lidah, menjadikannya salah satu oleh-oleh kekinian paling diburu wisatawan yang berkunjung ke Yogyakarta.",
            quote = "\"Sensasi modern oleh-oleh kekinian Jogja dengan lelehan lava manis super lembut.\"",
            variants = listOf(
                Variant("Original Cokelat Lava", "Bolu vanila lembut spons berisikan lelehan krim cokelat kental istimewa."),
                Variant("Brownies Keju Gurih", "Bolu cokelat pekat brownies dengan isian keju cheddar lumer melimpah."),
                Variant("Original Kacang Hijau", "Paduan bolu kukus lembut dengan cita rasa klasik kumbu kacang hijau."),
                Variant("Red Velvet Keju", "Bolu red velvet merah anggun dengan filling keju krim lezat.")
            ),
            packingTips = listOf(
                "Tahan suhu ruangan hingga 7 hari; simpan di dalam kulkas untuk ketahanan ekstra hingga 10 hari.",
                "Sangat aman untuk dibawa ke kabin pesawat karena bentuk kotaknya yang kokoh dan rapi.",
                "Nikmati dalam keadaan dingin dari lemari es untuk sensasi tekstur krim yang lebih padat."
            ),
            locations = listOf(
                "Store Resmi Stasiun Tugu Yogyakarta (Pintu Selatan & Pintu Timur)",
                "Official Store Jl. Solo KM 9 No. 3, Maguwoharjo",
                "Store Resmi Jl. Kaliurang KM 5.5, Depok, Sleman"
            )
        ),
        Souvenir(
            id = "oseng-mercon",
            name = "Oseng Mercon Bu Narti",
            subtitle = "Jl. KH. Ahmad Dahlan",
            category = "basah",
            categoryLabel = "Makanan Basah",
            shelfLife = "12 Bulan (Kaleng)",
            priceRange = "Rp 45.000",
            imageResId = R.drawable.img_oseng_mercon,
            isPopular = false,
            description = "Oseng-oseng Mercon Bu Narti adalah pelopor kuliner pedas legendaris di Yogyakarta sejak tahun 1998. Terdiri dari tetelan daging sapi pilihan, koyor kenyal, dan gajih yang dimasak dengan cabai rawit merah melimpah (dengan rasio cabai hampir sebanding dengan dagingnya). Disebut 'mercon' karena sensasi pedasnya yang meledak-ledak di dalam mulut seperti petasan. Kini hadir dalam kemasan kaleng steril vakum yang diawetkan melalui teknologi thermal tanpa bahan pengawet kimia.",
            quote = "\"Ledakan rasa pedas dahsyat khas Jogja yang melegenda bagi para pemburu kuliner ekstrem.\"",
            variants = listOf(
                Variant("Oseng Mercon Tetelan Daging Sapi", "Resep otentik dengan tetelan sapi dan koyor kenyal berkuah cabai pedas murni."),
                Variant("Oseng Mercon Kikil & Ayam", "Kombinasi kikil kenyal dan suwiran ayam rempah berbumbu pedas menggoda."),
                Variant("Oseng Mercon Ati Ampela", "Varian jeroan gurih pedas nikmat pendamping nasi hangat.")
            ),
            packingTips = listOf(
                "Kemasan kaleng sangat kokoh, anti-bocor, dan aman dibawa di dalam koper bagasi perjalanan jauh.",
                "Untuk penyajian, rebus kaleng dalam air mendidih selama 5 menit atau tuangkan ke wajan penggorengan.",
                "Sangat cocok disantap bersama sepiring nasi putih hangat mengepul."
            ),
            locations = listOf(
                "Warung Oseng Mercon Bu Narti (Jl. KH. Ahmad Dahlan No. 107, Notoprajan)",
                "Toko Oleh-Oleh Khas Jogja Malioboro Mall",
                "Sentra Oleh-Oleh Bandara Internasional Yogyakarta (YIA)"
            )
        ),
        Souvenir(
            id = "peyek-tumpuk",
            name = "Peyek Tumpuk Mbok Tumpuk",
            subtitle = "Bantul, Yogyakarta",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "1 - 2 Bulan",
            priceRange = "Rp 22.000",
            imageResId = R.drawable.img_peyek,
            isPopular = false,
            description = "Peyek Tumpuk Mbok Tumpuk adalah rempeyek kacang tanah khas Bantul yang memiliki keunikan bentuk bulat tebal bertumpuk-tumpuk, berbeda dari rempeyek biasa yang tipis pipih. Dibuat dengan adonan tepung beras pilihan, santan kelapa, ketumbar, kencur, daun jeruk purut iris halus, dan kacang tanah melimpah. Meskipun tebal padat, peyek ini memiliki tekstur yang sangat renyah renyah dan gurih di mulut tanpa meninggalkan rasa minyak berlebih.",
            quote = "\"Kreasi rempeyek tebal bertumpuk yang tetap renyah gurih di setiap gigitan.\"",
            variants = listOf(
                Variant("Peyek Tumpuk Kacang Tanah", "Rempah ketumbar wangi dengan taburan kacang tanah gurih melimpah."),
                Variant("Peyek Teri Asin Gurih", "Pecel rempeyek dengan paduan ikan teri asin mini yang renyah gurih."),
                Variant("Peyek Kacang Hijau", "Varian unik menggunakan kacang hijau sangrai bertekstur krispi.")
            ),
            packingTips = listOf(
                "Gunakan wadah toples plastik tertutup rapat agar kerenyahannya bertahan hingga 2 bulan.",
                "Hindari tertindih benda berat agar bulatan peyek tumpuk tidak hancur menjadi serpihan.",
                "Sangat cocok dijadikan lauk pelengkap nasi gudeg, pecel, atau camilan santai."
            ),
            locations = listOf(
                "Gerai Pusat Mbok Tumpuk (Jl. Wahid Hasyim No. 104, Taskombang, Palbapang, Bantul)",
                "Pasar Beringharjo Lantai 1 Blok Tengah",
                "Toko Oleh-Oleh Khas Bantul Sepanjang Jl. Bantul"
            )
        ),
        Souvenir(
            id = "kopi-jos",
            name = "Kopi Jos Arang Merapi",
            subtitle = "Stasiun Tugu & Malioboro",
            category = "minuman",
            categoryLabel = "Minuman Tradisional",
            shelfLife = "12 Bulan",
            priceRange = "Rp 25.000",
            imageResId = R.drawable.img_kopi_jos,
            isPopular = false,
            description = "Kopi Jos adalah minuman kopi tubruk khas angkringan Yogyakarta yang disajikan unik dengan mencelupkan bongkahan arang kayu membara merah ke dalam cangkir seduhan kopi panas hingga berbunyi mendesis 'josss'. Arang kayu yang membara dipercaya berfungsi mengikat kadar kafein berlebih, menetralkan asam lambung, dan memberikan sensasi rasa karamel smoky yang khas. Kini tersedia dalam kemasan bubuk kopi arabika-robusta lereng Gunung Merapi lengkap dengan potongan arang aktif khusus food-grade.",
            quote = "\"Cita rasa malam Malioboro dalam secangkir kehangatan kopi tubruk khas angkringan.\"",
            variants = listOf(
                Variant("Bubuk Kopi Robusta Merapi", "Kopi robusta lereng Merapi berpanggang medium dark dengan rasa cokelat pekat."),
                Variant("Kopi Joss Celup Herbal", "Inovasi kopi celup praktis siap seduh dengan campuran rempah jahe dan arang aktif."),
                Variant("Biji Kopi Sangrai Whole Bean", "Biji kopi sangrai murni untuk pencinta seduhan manual brew di rumah.")
            ),
            packingTips = listOf(
                "Kemas dalam pouch kedap udara berkatup one-way valve agar aroma kopi tetap tajam dan segar.",
                "Aman dibawa dalam penerbangan domestik maupun internasional.",
                "Simpan pada tempat kering dan jauhkan dari bahan berbau menyengat."
            ),
            locations = listOf(
                "Angkringan Lik Man (Kawasan Sebelah Utara Stasiun Tugu Yogyakarta)",
                "Angkringan Kopi Jos Sepanjang Jl. Mangkubumi",
                "Pusat Oleh-Oleh Kopi Nusantara Bandara YIA"
            )
        ),
        Souvenir(
            id = "cokelat-monggo",
            name = "Cokelat Monggo Heritage",
            subtitle = "Kotagede, Yogyakarta",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "6 - 12 Bulan",
            priceRange = "Rp 38.000",
            imageResId = R.drawable.img_cokelat,
            isPopular = false,
            description = "Cokelat Monggo adalah pelopor produsen cokelat artisan premium di Yogyakarta yang didirikan oleh ahli cokelat asal Belgia di kawasan bersejarah Kotagede sejak tahun 2005. Memadukan keahlian teknik pengolahan cokelat Belgia klasik dengan 100% biji kakao murni terbaik hasil petani Indonesia. Setiap batangan cokelat dibalut kemasan ramah lingkungan bermotif wayang, batik, dan ornamen budaya Jawa yang sangat elegan, menjadikannya oleh-oleh mewah bercitarasa tinggi.",
            quote = "\"Kreasi cokelat kelas dunia dengan sentuhan kemasan wayang khas budaya luhur Jawa.\"",
            variants = listOf(
                Variant("Dark Chocolate 58% - 69% - 77%", "Cokelat murni pahit manis kaya antioksidan dengan rasa kakao yang intens."),
                Variant("Mango & Durian Praline", "Isian pasta buah tropis asli berpadu lembut dengan balutan cokelat susu."),
                Variant("Ginger & Red Chili Chocolate", "Kombinasi unik rasa cokelat lezat dengan sensasi hangat jahe dan pedas cabai lokal.")
            ),
            packingTips = listOf(
                "Gunakan kantong insulasi termal (thermal pouch) / ice gel jika bepergian di cuaca terik.",
                "Simpan pada suhu sejuk antara 18 hingga 22 derajat celcius agar cokelat tidak meleleh.",
                "Sangat cocok untuk kado dan buah tangan premium bagi kerabat atau rekan kerja."
            ),
            locations = listOf(
                "Museum & Pabrik Cokelat Monggo (Jl. Tugu Gentong, Sribitan, Bangunjiwo, Kasihan, Bantul)",
                "Toko Resmi Cokelat Monggo Kotagede (Jl. Dalem KG III No. 978, Kotagede)",
                "Outlet Resmi Bandara Internasional Yogyakarta (YIA)"
            )
        ),
        Souvenir(
            id = "jadah-tempe",
            name = "Jadah Tempe Kaliurang",
            subtitle = "Kaliurang, Sleman",
            category = "basah",
            categoryLabel = "Makanan Basah",
            shelfLife = "1 - 2 Hari",
            priceRange = "Rp 20.000",
            imageResId = R.drawable.img_jadah_tempe,
            isPopular = false,
            description = "Jadah Tempe adalah kuliner tradisional legendaris khas kawasan lereng Gunung Merapi, Kaliurang. Merupakan kombinasi sempurna antara jadah (olahan ketan putih gurih kenyal berbalur parutan kelapa) dan tempe bacem (tempe kedelai yang dimasak manis gurih dengan gula kelapa, ketumbar, dan daun salam). Cara menikmatinya adalah dengan menjepit sepotong tempe bacem hangat di antara dua potong jadah layaknya 'burger tradisional Jawa'. Kuliner ini konon merupakan camilan kegemaran Sri Sultan Hamengkubuwono IX saat beristirahat di Kaliurang.",
            quote = "\"Burger tradisional Jawa kegemaran Sultan HB IX yang menghangatkan sejuknya lereng Merapi.\"",
            variants = listOf(
                Variant("Jadah Tempe Bacem Komplit", "Paket berisi 10 potong jadah ketan gurih dan 10 potong tempe bacem legit."),
                Variant("Jadah Tahu Bacem", "Kombinasi jadah pulen dengan tahu bacem lembut berkuah rempah manis."),
                Variant("Paket Tempe Bacem Kemasan Vakum", "Tempe bacem bumbu matang siap santap yang tahan hingga 5 hari.")
            ),
            packingTips = listOf(
                "Jadah segar paling nikmat disantap dalam waktu 24 jam setelah dibeli.",
                "Untuk perjalanan darat ke luar kota, mintalah tempe bacem dalam kemasan terpisah.",
                "Kukus kembali jadah selama 5 menit sesampainya di rumah untuk mengembalikan kelembutannya."
            ),
            locations = listOf(
                "Jadah Tempe Mbah Carik Pusat (Jl. Kaliurang KM 25, Hargobinangun, Pakem, Sleman)",
                "Warung Jadah Tempe Kaliurang (Kawasan Wisata Tlogo Putri Merapi)",
                "Sentra Oleh-Oleh Kuliner Jl. Palagan Tentara Pelajar"
            )
        ),
        Souvenir(
            id = "tiwul-manis",
            name = "Tiwul Manis Yu Tum",
            subtitle = "Wonosari, Gunungkidul",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "3 - 5 Hari",
            priceRange = "Rp 18.000",
            imageResId = R.drawable.img_tiwul,
            isPopular = false,
            description = "Tiwul Manis adalah panganan tradisional khas Kabupaten Gunungkidul yang terbuat dari gaplek (singkong yang dikeringkan secara alami di bawah sinar matahari) lalu ditumbuk halus dan dikukus bersama taburan gula merah kelapa serta kelapa parut gurih. Dahulu merupakan makanan pokok pengganti beras di era paceklik, kini tiwul bertransformasi menjadi hidangan primadona oleh-oleh eksotis yang diburu wisatawan. Memiliki indeks glikemik rendah dan tekstur kenyal manis yang sangat khas bernuansa pedesaan.",
            quote = "\"Kearifan pangan tradisional Gunungkidul yang bertransformasi menjadi kuliner primadona wisatawan.\"",
            variants = listOf(
                Variant("Tiwul Manis Gula Jawa Asli", "Resep klasik beraroma gula kelapa alami dengan taburan kelapa parut kukus."),
                Variant("Tiwul Instan Cepat Saji", "Bentuk kering praktis dalam kemasan kedap udara, tahan hingga 6 bulan dan siap dikukus."),
                Variant("Gatot Manis Kenyal", "Olahan singkong fermentasi alami berwarna hitam manis bertekstur super kenyal.")
            ),
            packingTips = listOf(
                "Pilih versi Tiwul Instan kemasan dus jika membawa oleh-oleh perjalanan antarpulau.",
                "Tiwul basah siap saji sebaiknya dikonsumsi dalam waktu 2 hari atau disimpan di lemari es.",
                "Sajikan hangat bersama teh melati poci atau kopi panas di pagi hari."
            ),
            locations = listOf(
                "Tiwul Yu Tum Pusat (Jl. Pramuka No. 36, Wonosari, Gunungkidul)",
                "Rest Area Bunder Jl. Wonosari - Yogyakarta",
                "Pasar Tradisional Wonosari Gunungkidul"
            )
        ),
        Souvenir(
            id = "salak-pondoh",
            name = "Salak Pondoh Sleman",
            subtitle = "Turi, Sleman",
            category = "kering",
            categoryLabel = "Makanan Kering",
            shelfLife = "7 - 10 Hari",
            priceRange = "Rp 25.000",
            imageResId = R.drawable.img_salak,
            isPopular = false,
            description = "Salak Pondoh adalah varietas buah salak unggulan yang dibudidayakan secara intensif di tanah vulkanik subur kawasan lereng Gunung Merapi, Kabupaten Sleman. Keistimewaan salak pondoh terletak pada daging buahnya yang tebal, renyah tanpa getah, dan rasanya yang manis legit tanpa rasa sepat (kelat) bahkan sejak buahnya masih muda. Salak ini telah mengantongi sertifikasi Indikasi Geografis resmi dan menjadi komoditas buah tropis kebanggaan Daerah Istimewa Yogyakarta yang diekspor hingga ke mancanegara.",
            quote = "\"Manis renyahnya buah tropis tanah vulkanik subur lereng Gunung Merapi.\"",
            variants = listOf(
                Variant("Salak Pondoh Super Segar", "Buah utuh berukuran besar dengan daging tebal manis renyah."),
                Variant("Keripik Salak Pondoh Oven", "Camilan keripik buah salak murni tanpa minyak yang renyah dan awet 6 bulan."),
                Variant("Manisan & Dodol Salak", "Olahan daging salak berpadu gula murni bertekstur kenyal manis.")
            ),
            packingTips = listOf(
                "Gunakan keranjang anyaman bambu berongga agar buah tidak lembap atau memar di perjalanan.",
                "Pilih buah salak dengan tangkai yang masih segar untuk ketahanan hingga 10 hari.",
                "Sangat aman dimasukkan ke dalam bagasi pesawat maupun mobil pribadi."
            ),
            locations = listOf(
                "Desa Wisata Salak Pondoh (Kecamatan Turi, Sleman, Yogyakarta)",
                "Pasar Buah Tempel (Perbatasan Sleman - Magelang)",
                "Pasar Buah Gamping (Jl. Wates KM 5, Yogyakarta)"
            )
        ),
        Souvenir(
            id = "wedang-ronde",
            name = "Wedang Ronde Asli Jogja",
            subtitle = "Kawasan Alun-Alun Kidul",
            category = "minuman",
            categoryLabel = "Minuman Tradisional",
            shelfLife = "2 Hari",
            priceRange = "Rp 12.000",
            imageResId = R.drawable.img_ronde,
            isPopular = false,
            description = "Wedang Ronde adalah minuman kuah jahe hangat tradisional yang sangat populer dinikmati di malam hari di sekitar Alun-Alun Kidul Yogyakarta. Terdiri dari bola-bola ronde kenyal beraneka warna yang dibuat dari tepung ketan berisikan tumbukan kacang tanah manis gurih, disajikan dalam mangkuk bersama kuah jahe wangi gula tebu, kolang-kaling iris, potongan roti tawar dadu, dan taburan kacang tanah sangrai renyah. Menikmati semangkuk wedang ronde hangat di bawah gemerlap lampu malam Jogja merupakan pengalaman kuliner yang tak terlupakan.",
            quote = "\"Semangkuk kehangatan jahe manis dengan bola-bola ronde kenyal di malam syahdu Jogja.\"",
            variants = listOf(
                Variant("Ronde Komplit Kuah Jahe", "Bola ronde warna-warni isi kacang, kolang-kaling, roti tawar, dan kacang sangrai."),
                Variant("Kuah Jahe Gula Jawa", "Sari jahe emprit bakar berpadu manis legit gula kelapa alami."),
                Variant("Paket DIY Bola Ronde", "Adonan beku bola ronde dan sirup jahe murni siap seduh kapan saja di rumah.")
            ),
            packingTips = listOf(
                "Untuk dibawa bepergian, mintalah kuah jahe dan bola ronde dikemas dalam plastik terpisah.",
                "Hangatkan kuah jahe hingga mendidih sebelum menuangkannya ke mangkuk berisi ronde.",
                "Nikmati selagi panas untuk sensasi kehangatan jahe yang maksimal."
            ),
            locations = listOf(
                "Kawasan Wisata Malam Alun-Alun Kidul (Alkid) Yogyakarta",
                "Wedang Ronde Mbah Payem (Jl. Kauman, Ngupasan, Gondomanan)",
                "Sentra Angkringan & Kuliner Malam Jl. Malioboro"
            )
        )
    )

    fun getById(id: String): Souvenir? {
        return list.find { it.id == id }
    }
}
