package graph;

import java.util.Scanner;

//Program untuk mengelola menu navigasi kampus
public class MainGraphNavigation {
    private static GraphNavigation campus;      //Objek Graph untuk menyimpan lokasi dan jalur kampus

    //Method start dipanggil dari Main utama SIAAKAD
    public static void start(Scanner input) {
        campus = new GraphNavigation(20);       //Membuat Graph dengan kapasitas maksimal 20 lokasi
        loadCampusMap();        //Memasukkan data lokasi dan jarak simulasi sebelum menu ditampilkan

        int choice;
        do {
            showMenu();
            choice = readInteger(input, "Pilih submenu Graph: ");       //Membaca pilihan pengguna dari submenu Graph
            switch (choice) {
                case 1:
                    campus.displayLocations();      //Menampilkan semua lokasi kampus yang sudah dimasukkan
                    break;
                case 2:
                    campus.displayRoads();          //Menampilkan jalur antar lokasi beserta jaraknya dalam meter
                    break;
                case 3:
                    campus.printGraph();            //Menampilkan susunan Adjacency List untuk melihat hubungan setiap lokasi
                    break;
                case 4:
                    //Meminta lokasi awal lalu menjalankan Breadth First Search
                    campus.bfs(readText(input, "Masukkan lokasi awal BFS: "));
                    break;
                case 5:
                    //Meminta lokasi awal lalu menjalankan Depth First Search
                    campus.dfs(readText(input, "Masukkan lokasi awal DFS: "));
                    break;
                case 6:
                    //Meminta lokasi awal dan tujuan sebelum mencari rute terpendek
                    String start = readText(input, "Masukkan lokasi awal  : ");
                    String end = readText(input, "Masukkan lokasi tujuan: ");
                    campus.dijkstra(start, end);         //Menjalankan Dijkstra berdasarkan jarak pada setiap jalur
                    break;
                case 0:
                    System.out.println("Kembali ke menu utama SIAAKAD.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } while (choice != 0);      //Menu akan terus ditampilkan sampai pengguna memilih angka 0
    }

    //Menampilkan submenu pada modul Graph
    private static void showMenu() {
        System.out.println("\n==============================================");
        System.out.println("       GRAPH - NAVIGASI PETA KAMPUS");
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

    //Memasukkan data contoh lokasi dan jarak untuk simulasi peta kampus
    private static void loadCampusMap() {
        //Mendaftarkan nama lokasi dahulu sebelum menghubungkan antar lokasi
        campus.addLocation("Gedung Anggrek");
        campus.addLocation("Gedung Syahdan");
        campus.addLocation("Gedung Kijang");
        campus.addLocation("Perpustakaan");
        campus.addLocation("Kantin");
        campus.addLocation("Laboratorium");
        campus.addLocation("Auditorium");
        campus.addLocation("Area Parkir");

        //Membuat jalur dua arah dengan jarak dalam satuan meter
        campus.addEdge("Gedung Anggrek", "Gedung Syahdan", 120);
        campus.addEdge("Gedung Anggrek", "Area Parkir", 80);
        campus.addEdge("Gedung Syahdan", "Gedung Kijang", 100);
        campus.addEdge("Gedung Syahdan", "Perpustakaan", 90);
        campus.addEdge("Gedung Kijang", "Kantin", 70);
        campus.addEdge("Gedung Kijang", "Laboratorium", 110);
        campus.addEdge("Perpustakaan", "Auditorium", 130);
        campus.addEdge("Kantin", "Auditorium", 85);
        campus.addEdge("Laboratorium", "Auditorium", 95);
        campus.addEdge("Area Parkir", "Kantin", 160);
    }

    //Membaca input angka dan mengulang jika format input salah
    private static int readInteger(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                //Mengubah teks menjadi angka integer untuk diproses sebagai pilihan menu
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
