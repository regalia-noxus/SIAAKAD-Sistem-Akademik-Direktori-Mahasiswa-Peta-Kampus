import Mahasiswa.Mahasiswa; 
import Mahasiswa.StudentHashTable; 
import bst.BinarySearchTree; 
import graph.GraphNavigation; 
import java.util.Arrays; 
import java.util.Collections; 
import java.util.List; 
import java.util.ArrayList; // Implementasi List untuk menampung data dummy.
import java.util.Locale; // Menentukan format angka desimal saat mencetak tabel.
import java.util.Random; // Menghasilkan angka simulasi dengan seed yang ditentukan.
import java.util.function.IntPredicate; // Fungsi yang menerima indeks int dan menghasilkan boolean.

// Menyediakan data simulasi dan membandingkan waktu pencarian.
public class BenchmarkTest {
    // static final berarti konstanta milik class yang nilainya tidak diubah selama program berjalan
    // Jumlah data adalah 20
    private static final int DATA_COUNT = 20;
    // Setiap pemanasan mencari seluruh 20 target sebanyak 1.000 kali sebelum diukur
    private static final int WARMUP = 1000;
    // Setiap pengukuran mengulang 20 target sebanyak 5.000 kali agar durasi lebih mudah diamati
    private static final int REPETITIONS = 5000;
    // Pengukuran dilakukan lima putaran sehingga tersedia lima durasi untuk setiap struktur
    private static final int ROUNDS = 5;
    // Menyimpan hasil yang digunakan program agar hasil pencarian tidak diabaikan JVM
    // volatile membuat penulisan dan pembacaan field ini memiliki aturan visibilitas khusus JVM
    // Field juga dibaca saat validasi sehingga tidak lagi mendapat warning tidak digunakan
    private static volatile int resultSink;

    // Satu data menyatukan identitas mahasiswa, nilai akademik, dan lokasi gedungnya.
    public static final class DummyData {
        // final mencegah referensi/nilai field diganti setelah objek DummyData dibuat.
        public final Mahasiswa mahasiswa;
        // Nilai akademik memakai skala 0-100 dan terpisah dari IPK mahasiswa yang berskala 0-4.
        public final double nilai;
        // Nama lokasi gedung dipasangkan dengan satu mahasiswa dalam data simulasi.
        public final String gedung;

        // Constructor menggabungkan tiga parameter menjadi satu objek data dummy.
        private DummyData(Mahasiswa mahasiswa, double nilai, String gedung) {
            // this menunjuk field objek; nama tanpa this menunjuk parameter constructor.
            this.mahasiswa = mahasiswa;
            this.nilai = nilai;
            this.gedung = gedung;
        }
    }

    // Seed tetap membuat nilai dan urutan pemasukan data dapat diulang saat demonstrasi.
    // Mengembalikan List berisi 20 objek DummyData baru setiap kali fungsi dipanggil.
    public static List<DummyData> generateDummyData() {
        // Array nama menyediakan 20 identitas contoh yang bukan identitas anggota kelompok.
        String[] names = {
            "Andi Saputra", "Budi Santoso", "Citra Lestari", "Dewi Anggraini",
            "Eko Prasetyo", "Fajar Nugraha", "Gita Maharani", "Hadi Wijaya",
            "Indah Permata", "Joko Susilo", "Kartika Sari", "Lukman Hakim",
            "Maya Putri", "Nanda Pratama", "Oki Setiawan", "Putri Amelia",
            "Rina Wulandari", "Satria Utama", "Tania Safitri", "Yoga Firmansyah"
        };
        // Indeks gedung dipasangkan dengan indeks nama saat objek DummyData dibuat.
        String[] buildings = {
            "Gedung Anggrek", "Gedung Syahdan", "Gedung Kijang", "Perpustakaan",
            "Kantin", "Laboratorium Komputer", "Auditorium", "Gedung Parkir",
            "Gedung Rektorat", "Gedung Administrasi", "Gedung Olahraga", "Gedung Klinik",
            "Gedung UKM", "Gedung Pascasarjana", "Gedung Desain", "Gedung Teknik",
            "Gedung Bisnis", "Gedung Bahasa", "Gedung Riset", "Gedung Asrama"
        };
        // 305L adalah seed bertipe long; seed yang sama menghasilkan urutan angka yang sama.
        Random random = new Random(305L);
        // List awal kosong, kemudian ditambah satu record pada setiap iterasi.
        List<DummyData> data = new ArrayList<>();
        for (int i = 0; i < DATA_COUNT; i++) {
            // NIM memakai long karena sepuluh digit melebihi kapasitas int Java.
            String nim = Long.toString(2902800001L + i);
            // nextInt(151) menghasilkan 0-150; rumus ini membentuk IPK 2,50 sampai 4,00.
            double ipk = (250 + random.nextInt(151)) / 100.0;
            // nextInt(401) menghasilkan 0-400; rumus ini membentuk nilai 60,0 sampai 100,0.
            double nilai = (600 + random.nextInt(401)) / 10.0;
            // Membuat Mahasiswa, memasangkannya dengan nilai dan gedung, lalu menyimpan ke List.
            data.add(new DummyData(new Mahasiswa(nim, names[i], ipk), nilai, buildings[i]));
        }
        // Pengacakan menghindari BST menjadi rantai akibat NIM dimasukkan berurutan.
        Collections.shuffle(data, new Random(305L));
        // Mengembalikan seluruh record beserta urutan yang sudah diacak kepada pemanggil.
        return data;
    }

    // Menampilkan data di luar bagian yang diukur sehingga output tidak memengaruhi durasi.
    // Dipanggil dari menu 6 untuk memeriksa isi generator tanpa menjalankan benchmark.
    public static void displayDummyData() {
        System.out.println("\n=== 20 DATA DUMMY MAHASISWA, NILAI, DAN LOKASI GEDUNG ===");
        for (DummyData row : generateDummyData()) {
            // %s mencetak teks/objek, %.1f mencetak satu angka desimal, dan %n membuat baris baru.
            // Locale.ROOT memakai format desimal yang konsisten pada berbagai pengaturan komputer.
            System.out.printf(Locale.ROOT, "%s | Nilai: %.1f | Lokasi: %s%n",
                    row.mahasiswa, row.nilai, row.gedung);
        }
        System.out.println("Data simulasi; bukan data akademik anggota kelompok.");
    }

    // Dapat dijalankan langsung atau dipanggil melalui pilihan 5 pada Main.
    // Java memanggil main saat perintah java -cp out BenchmarkTest dijalankan.
    public static void main(String[] args) {
        run();
    }

    // Mengisi ketiga struktur, memvalidasi target, mengukur pencarian, lalu mencetak perbandingan.
    // Tidak menerima input pengguna dan memakai data terpisah dari data pada submenu aplikasi.
    public static void run() {
        List<DummyData> data = generateDummyData();
        // Setiap pemanggilan membuat struktur baru agar benchmark tidak membawa data uji sebelumnya.
        StudentHashTable hashTable = new StudentHashTable();
        BinarySearchTree bst = new BinarySearchTree();
        GraphNavigation graph = new GraphNavigation(DATA_COUNT);
        // Ketiga array menyimpan kunci target terlebih dahulu agar konversi tidak ikut diukur.
        String[] nims = new String[DATA_COUNT];
        long[] numericNims = new long[DATA_COUNT];
        String[] locations = new String[DATA_COUNT];

        // Pembuatan objek, konversi kunci, dan insert dilakukan sebelum timer dimulai.
        for (int i = 0; i < data.size(); i++) {
            // Mengambil record pada indeks i dari List hasil generator.
            DummyData row = data.get(i);
            nims[i] = row.mahasiswa.getNim();
            // Hash Table menerima NIM String, sedangkan BST menerima kunci long.
            numericNims[i] = Long.parseLong(nims[i]);
            locations[i] = row.gedung;
            // Memasukkan identitas mahasiswa ke Hash Table, nilai ke BST, dan gedung ke Graph.
            hashTable.insert(row.mahasiswa);
            bst.insert(numericNims[i], row.mahasiswa.getNama(), row.nilai);
            graph.addLocation(locations[i]);
        }
        // Jalur melingkar dua arah membuat semua 20 gedung saling terjangkau.
        for (int i = 0; i < DATA_COUNT; i++) {
            // Modulo (%) menghubungkan gedung terakhir kembali ke gedung pertama.
            // Jarak simulasi bertambah 5 meter; BFS hanya memeriksa keterjangkauan, bukan bobot jarak.
            graph.addEdge(locations[i], locations[(i + 1) % DATA_COUNT], 50 + i * 5);
        }

        // Setiap struktur mencari seluruh 20 target dengan urutan yang sama.
        // Lambda i -> ... membungkus pencarian agar ketiganya dapat dipanggil lewat search.test(i).
        // Hasil true berarti target ditemukan; hasil false berarti target tidak ditemukan.
        IntPredicate hashSearch = i -> hashTable.search(nims[i]) != null;
        IntPredicate bstSearch = i -> bst.search(numericNims[i]) != null;
        // BFS selalu dimulai dari locations[0], lalu mencari lokasi milik record ke-i.
        IntPredicate graphSearch = i -> graph.searchBfs(locations[0], locations[i]);
        IntPredicate[] searches = {hashSearch, bstSearch, graphSearch};
        // Indeks label mengikuti indeks fungsi pencarian agar baris hasil tidak tertukar.
        String[] labels = {"Hash Table (NIM)", "BST (NIM)", "Graph BFS (gedung)"};

        // Memeriksa keberhasilan sebelum pengukuran dan memberi kesempatan JVM melakukan warm-up.
        for (IntPredicate search : searches) {
            for (int i = 0; i < DATA_COUNT; i++) {
                if (!search.test(i)) {
                    throw new IllegalStateException("Data dummy tidak ditemukan.");
                }
            }
            // Pemanasan menjalankan fungsi yang sama tanpa mencatat waktunya ke tabel hasil.
            searchAll(search, WARMUP);
        }

        // Array dua dimensi: indeks pertama memilih struktur, indeks kedua memilih putaran.
        long[][] durations = new long[searches.length][ROUNDS];
        for (int round = 0; round < ROUNDS; round++) {
            // Menggilir urutan pengujian mengurangi pengaruh posisi pertama atau terakhir.
            for (int offset = 0; offset < searches.length; offset++) {
                int index = (round + offset) % searches.length;
                // nanoTime membaca waktu relatif beresolusi nanodetik, bukan jam/tanggal kalender.
                long start = System.nanoTime();
                // Bagian yang diukur: 5.000 kali pencarian seluruh 20 target beserta loop penghitungnya.
                int found = searchAll(searches[index], REPETITIONS);
                // Selisih waktu sesudah dan sebelum pencarian adalah durasi eksekusi dalam ns.
                long elapsed = System.nanoTime() - start;
                // Menyimpan durasi dan hasil setelah timer selesai agar validasi tidak ikut diukur.
                durations[index][round] = elapsed;
                resultSink = found;
                // Membaca kembali hasil tersimpan sekaligus memvalidasi jumlah pencarian sukses.
                if (resultSink != REPETITIONS * DATA_COUNT) {
                    throw new IllegalStateException("Jumlah hasil pencarian tidak sesuai.");
                }
            }
        }

        // Seluruh pencetakan dilakukan setelah semua pengukuran selesai.
        System.out.println("\n=== BENCHMARK PENCARIAN: 20 DATA DUMMY ===");
        System.out.printf("Setiap putaran: %d pencarian (%d pengulangan x 20 target).%n",
                REPETITIONS * DATA_COUNT, REPETITIONS);
        System.out.printf("Hasil: median dari %d putaran setelah warm-up.%n", ROUNDS);
        System.out.printf("%-23s %20s %22s%n", "Struktur / kunci", "Total median (ns)", "Rata-rata (ns/cari)");
        for (int i = 0; i < searches.length; i++) {
            // Median mengurangi pengaruh satu putaran yang terganggu proses lain atau GC.
            Arrays.sort(durations[i]);
            // Lima durasi yang terurut memiliki nilai tengah pada indeks 2 (5 / 2).
            long median = durations[i][ROUNDS / 2];
            // Total median dibagi 100.000 pencarian untuk memperoleh rata-rata ns per pencarian.
            // Cast ke double mempertahankan pecahan; %.2f menampilkan dua angka desimal.
            System.out.printf(Locale.ROOT, "%-23s %20d %22.2f%n", labels[i], median,
                    median / (double) (REPETITIONS * DATA_COUNT));
        }
        // Penjelasan membatasi interpretasi hasil karena kunci NIM dan lokasi gedung berbeda.
        System.out.println("Semua target berhasil ditemukan; insert dan output tidak ikut diukur.");
        System.out.println("Hash Table/BST mencari NIM; Graph mencari gedung dari lokasi awal yang tetap.");
        System.out.println("20 pasangan data sama, tetapi jenis kunci dan pekerjaan algoritmanya berbeda.");
        System.out.println("BFS mencakup pembuatan antrian dan visited; waktu dapat berubah tiap eksekusi.");
        System.out.println("Hasil ini demonstrasi pencarian sukses, bukan kesimpulan kecepatan untuk semua kasus.");
        System.out.println("Teori: Hash Table rata-rata O(1), BST O(h), BFS O(V + E).");
    }

    // Mengulang semua target dan menghitung hasil agar operasi pencarian benar-benar digunakan
    // search adalah fungsi pencarian yang diuji; repetitions adalah jumlah pengulangan 20 target
    // Mengembalikan jumlah pencarian sukses untuk diperiksa setelah pengukuran selesai
    private static int searchAll(IntPredicate search, int repetitions) {
        int found = 0;
        // Loop luar mengatur berapa kali satu kelompok berisi 20 pencarian dijalankan.
        for (int repeat = 0; repeat < repetitions; repeat++) {
            // Loop dalam memastikan semua 20 target dicari satu kali pada setiap pengulangan
            for (int i = 0; i < DATA_COUNT; i++) {
                // test menjalankan lambda struktur yang dipilih; penghitung bertambah jika ditemukan
                if (search.test(i)) {
                    found++;
                }
            }
        }
        return found;
    }
}
