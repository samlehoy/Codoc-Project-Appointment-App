package com.example.codoc

object ListDokter {
    fun getDataDokter(): ArrayList<DokterModel> {
        val listDokter = ArrayList<DokterModel>()

        val dokter1 = DokterModel(R.drawable.icon1, "Dr.Ahmad", "Dokter Umum", "16 Tahun", "98%")

        val dokter2 = DokterModel(R.drawable.icon1, "Dr.Joko", "Dokter Umum", "16 Tahun", "98%")

        val dokter3 = DokterModel(R.drawable.icon1, "Dr.Jarwo", "Dokter Umum", "16 Tahun", "98%")

        val dokter4 = DokterModel(R.drawable.icon1, "Dr.Sindi", "Dokter Umum", "16 Tahun", "98%")

        val dokter5 = DokterModel(R.drawable.icon1, "Dr.Jini", "Dokter Umum", "16 Tahun", "98%")

        val dokter6 = DokterModel(R.drawable.icon1, "Dr.Jono", "Dokter Umum", "16 Tahun", "98%")

        val dokter7 = DokterModel(R.drawable.icon1, "Dr.Junu", "Dokter Umum", "16 Tahun", "98%")

        val dokter8 = DokterModel(R.drawable.icon1, "Dr.Jene", "Dokter Umum", "16 Tahun", "98%")

        return listDokter
    }
}