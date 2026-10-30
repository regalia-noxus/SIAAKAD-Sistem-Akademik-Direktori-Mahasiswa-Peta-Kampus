import graph.MainGraphNavigation;
import java.util.Scanner;

//Program utama SIAAKAD
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            showMainMenu();
            choice = readInteger(input, "Pilih menu utama: ");
            switch (choice) {
                case 1:
                    // Masuk ke program Orang 1.
                    break;
                case 2:
                    // Masuk ke program Orang 2.
                    break;
                case 3:
                    // Masuk ke program Orang 3.
                    break;
                case 4:
                    MainGraphNavigation.start(input);
                    break;
                case 5:
                    // Masuk ke program Orang 5.
                    break;
                case 0:
                    System.out.println("\nTerima kasih telah menggunakan SIAAKAD.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } while (choice != 0);
        input.close();
    }

    //Menampilkan menu utama
    private static void showMainMenu() {
        System.out.println("\n==================================================");
        System.out.println("                       SIAAKAD");
        System.out.println("  SISTEM AKADEMIK DIREKTORI MAHASISWA PETA KAMPUS");
        System.out.println("==================================================");
        System.out.println("1. Manajemen Data Mahasiswa (Hash Table)");
        System.out.println("2. Manajemen Nilai Mahasiswa (BST)");
        System.out.println("3. Kalkulator Ekspresi (Tree)");
        System.out.println("4. Navigasi Peta Kampus (Graph)");
        System.out.println("5. Benchmark");
        System.out.println("0. Keluar");
        System.out.println("==================================================");
    }

    //Membaca angka menu dan mengulang jika input bukan angka
    private static int readInteger(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }
}
