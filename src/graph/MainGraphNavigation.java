package graph;

import java.util.Scanner;

public class MainGraphNavigation {
    public static void start(Scanner input) {
        loadCampusMap();
        int choice;
        do {
            showMenu();
            choice = readInteger(input, "Pilih submenu Graph: ");
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    String start = readText(input, "Masukkan lokasi awal  : ");
                    String end = readText(input, "Masukkan lokasi tujuan: ");
                    break;
                case 0:
                    System.out.println("Kembali ke menu utama SIAAKAD.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } while (choice != 0);
    }

    //Menampilkan submenu
    private static void showMenu() {
        System.out.println("\n==============================================");
        System.out.println("       GRAPH - NAVIGASI KAMPUS");
        System.out.println("==============================================");
        System.out.println("1. Tampilkan daftar lokasi kampus");
        System.out.println("2. Tampilkan jalur dan jarak antar lokasi");
        System.out.println("3. Tampilkan Adjacency List");
        System.out.println("4. Jalankan BFS");
        System.out.println("5. Jalankan DFS");
        System.out.println("6. Cari rute terpendek (Dijkstra)");
        System.out.println("0. Kembali ke menu utama");
        System.out.println("==============================================");
    }

    //Memasukkan data lokasi dan jarak kampus
    private static void loadCampusMap() {
    }

    //Membaca input angka dan mengulang jika format input salah
    private static int readInteger(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang sesuai dengan menu.");
            }
        }
    }

    //Membaca input nama lokasi
    private static String readText(Scanner input, String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }
}
