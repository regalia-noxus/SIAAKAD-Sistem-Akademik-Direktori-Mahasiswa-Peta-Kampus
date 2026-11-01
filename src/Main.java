
import Mahasiswa.MainHashTable; 
import ExpressionTree.ExpressionTree; 
import bst.MainBST; 
import graph.MainGraphNavigation; 
import java.util.Scanner; 

// Group 3 - Program Studi Computer Science
// 1. HILMAN WASIANDI KHAIRURRIZAL      | NIM: 2902817490 | Computer Science
// 2. CHRISTOFFER YOEL EVANDRI VIEBRIAN | NIM: 2902816336 | Computer Science
// 3. SULAIHA                          | NIM: 2902806442 | Computer Science
// 4. IRFAN SISWARA                    | NIM: 2902813126 | Computer Science
// 5. M. AKMAL ZULKARNAEN PRIADI       | NIM: 2902826015 | Computer Science
public class Main {
    // Titik awal aplikasi saat Main dijalankan; args menampung argumen dari terminal. static memungkinkan Java memanggil fungsi ini tanpa membuat objek Main terlebih dahulu.
    public static void main(String[] args) {
        // System.in adalah sumber input keyboard; satu Scanner dipakai bersama oleh submenu.
        Scanner input = new Scanner(System.in);
        // Menyimpan nomor menu yang dipilih pengguna.
        int choice;
        // do-while menampilkan menu minimal satu kali sebelum memeriksa syarat pengulangan.
        do {
            showMainMenu();

            //Mengambil pilihan menu utama dari pengguna
            choice = readInteger(input, "Pilih menu utama: ");
            // switch memilih tindakan berdasarkan nomor menu; break mengakhiri case tersebut.
            switch (choice) {
                case 1:
                    
                    MainHashTable.start(input);
                    break;
                case 2:
                    
                    MainBST.start(input);
                    break;
                case 3:
                    
                    startExpressionTree(input);
                    break;
                case 4:
                    
                    MainGraphNavigation.start(input);
                    break;
                case 5:
                    // Data benchmark terpisah dari data pengguna di setiap submenu.
                    BenchmarkTest.run();
                    break;
                case 6:
                    // Menampilkan pasangan mahasiswa, nilai, dan gedung hasil generator.
                    BenchmarkTest.displayDummyData();
                    break;
                case 0:
                    // Pilihan 0 membuat syarat do-while bernilai false sehingga aplikasi selesai.
                    System.out.println("\nTerima kasih telah menggunakan SIAAKAD.");
                    break;
                default:
                    // Dijalankan jika angka yang dipilih tidak memiliki case pada menu.
                    System.out.println("Pilihan tidak tersedia.");
            }
        //Kembali menampilkan menu utama setelah modul selesai dijalankan
        } while (choice != 0);
        //Menutup Scanner setelah seluruh proses input selesai
        input.close();
    }

    //Menampilkan nama aplikasi dan daftar modul yang dapat dipilih
    // Fungsi ini tidak menerima parameter dan tidak mengembalikan nilai (void).
    private static void showMainMenu() {
        // println mencetak satu baris lalu memindahkan posisi output ke baris berikutnya.
        System.out.println("\n==================================================");
        System.out.println("                       SIAAKAD");
        System.out.println("  SISTEM AKADEMIK DIREKTORI MAHASISWA & PETA KAMPUS");
        System.out.println("==================================================");
        System.out.println("1. Manajemen Data Mahasiswa (Hash Table)");
        System.out.println("2. Manajemen Nilai Mahasiswa (BST)");
        System.out.println("3. Kalkulator Ekspresi (Tree)");
        System.out.println("4. Navigasi Peta Kampus (Graph)");
        System.out.println("5. Benchmark");
        System.out.println("6. Tampilkan 20 Data Dummy");
        System.out.println("0. Keluar");
        System.out.println("==================================================");
    }

    //Membaca pilihan menu berupa angka dan mengulang jika input tidak valid
    // input adalah Scanner bersama; message adalah petunjuk input yang akan ditampilkan.
    // Nilai kembalian int dipakai oleh switch di menu utama.
    private static int readInteger(Scanner input, String message) {
        // Loop berakhir saat return menghasilkan angka yang valid atau menandai akhir input.
        while (true) {
            // print mempertahankan posisi kursor agar pengguna mengetik setelah pesan.
            System.out.print(message);
            // Akhir input diperlakukan seperti pilihan keluar pada menu utama.
            if (!input.hasNextLine()) {
                return 0;
            }
            // nextLine membaca satu baris; trim menghapus spasi di awal dan akhir teks.
            String value = input.nextLine().trim();
            try {
                // parseInt mengubah teks menjadi bilangan bulat, lalu return keluar dari fungsi.
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                //Menangani input huruf atau karakter yang bukan angka
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    // Membungkus modul Expression Tree yang belum memiliki submenu sendiri.
    // Scanner diterima dari main; return hanya kembali ke menu utama tanpa menutup input.
    private static void startExpressionTree(Scanner input) {
        // Pengguna dapat menghitung beberapa ekspresi sebelum mengetik perintah kembali.
        while (true) {
            System.out.println("\n=== KALKULATOR EXPRESSION TREE ===");
            System.out.println("Gunakan postfix, pisahkan token dengan spasi. Contoh: 80 90 + 2 /");
            System.out.print("Masukkan ekspresi (atau ketik kembali): ");
            // Mengakhiri submenu dengan aman jika tidak ada lagi baris input yang tersedia.
            if (!input.hasNextLine()) {
                return;
            }
            String postfix = input.nextLine().trim();
            // equalsIgnoreCase menerima kata kembali tanpa membedakan huruf besar/kecil.
            if (postfix.equalsIgnoreCase("kembali")) {
                return;
            }
            try {
                // Validasi jumlah operand sebelum modul memanggil Stack.pop().
                validatePostfix(postfix);
                // Membuat pohon baru agar ekspresi sebelumnya tidak terbawa ke perhitungan ini.
                ExpressionTree tree = new ExpressionTree();
                // Postfix menempatkan operator setelah operand; contoh 80 90 + berarti 80 + 90.
                tree.buildTreeFromPostfix(postfix);
                // Inorder menampilkan bentuk infix; postorder menampilkan kembali bentuk postfix.
                tree.inorderTraversal();
                tree.postorderTraversal();
                // evaluate menghitung hasil dengan menelusuri operand dan operator pada pohon.
                System.out.println("Hasil: " + tree.evaluate());
            } catch (IllegalArgumentException | ArithmeticException e) {
                // Menampilkan kesalahan format atau pembagian nol lalu melanjutkan submenu.
                System.out.println("Ekspresi tidak valid: " + e.getMessage());
            }
        }
    }

    // Operator membutuhkan dua operand dan menghasilkan satu nilai pengganti.
    // Fungsi ini memvalidasi teks postfix; input tidak valid ditolak melalui exception.
    private static void validatePostfix(String postfix) {
        // Ekspresi kosong tidak dapat dibentuk menjadi pohon perhitungan.
        if (postfix.isEmpty()) {
            throw new IllegalArgumentException("Ekspresi tidak boleh kosong.");
        }
        // Menghitung banyaknya nilai sementara yang tersedia, bukan hanya jumlah angka awal.
        int operands = 0;
        // split dengan \\s+ memisahkan token berdasarkan satu atau lebih spasi/tab.
        for (String token : postfix.split("\\s+")) {
            // Empat simbol berikut merupakan operator biner yang didukung modul.
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                if (operands < 2) {
                    throw new IllegalArgumentException("Operator harus memiliki dua operand.");
                }
                // Dua nilai dipakai dan satu hasil dikembalikan: jumlah nilai berkurang satu.
                operands--;
            } else {
                // Token selain operator harus dapat dibaca sebagai angka, termasuk angka desimal.
                double value = Double.parseDouble(token);
                // Menolak NaN dan Infinity yang bukan nilai angka berhingga.
                if (!Double.isFinite(value)) {
                    throw new IllegalArgumentException("Operand harus berupa angka berhingga.");
                }
                // Satu angka baru menambah satu nilai yang dapat dipakai operator berikutnya.
                operands++;
            }
        }
        // Ekspresi postfix lengkap harus menyisakan tepat satu hasil akhir.
        if (operands != 1) {
            throw new IllegalArgumentException("Jumlah operand dan operator tidak sesuai.");
        }
    }
}
