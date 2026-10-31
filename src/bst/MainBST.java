package bst;

import java.util.Scanner;

public class MainBST {
    private static BinarySearchTree bstTree = new BinarySearchTree();

    public static void start(Scanner input) {
        if (bstTree.isEmpty()) {
            loadDefaultData();
        }

        int choice;
        do {
            showMenu();
            choice = readInteger(input, "Pilih submenu BST: ");
            switch (choice) {
                case 1:
                    System.out.println("\n--- TAMBAH DATA NILAI MAHASISWA (BST INSERT) ---");
                    long key = readLong(input, "Masukkan NIM / Kunci ID Mahasiswa (angka): ");
                    String nama = readText(input, "Masukkan Nama Mahasiswa                  : ");
                    double nilai = readDouble(input, "Masukkan Nilai Akademik / IPK (0 - 100)  : ");

                    bstTree.insert(key, nama, nilai);
                    System.out.printf("Berhasil menyimpan data [%d | %s | Nilai: %.2f] ke dalam BST.\n", key, nama, nilai);
                    break;

                case 2:
                    System.out.println("\n--- PENCARIAN DATA NILAI MAHASISWA (BST SEARCH) ---");
                    long searchKey = readLong(input, "Masukkan NIM / Kunci ID yang dicari: ");
                    BSTNode found = bstTree.search(searchKey);
                    if (found != null) {
                        System.out.println("Data DITEMUKAN di BST:");
                        System.out.println(found);
                    } else {
                        System.out.println("Data dengan NIM/ID " + searchKey + " TIDAK DITEMUKAN dalam BST.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- HAPUS DATA NILAI MAHASISWA (BST DELETE) ---");
                    long delKey = readLong(input, "Masukkan NIM / Kunci ID yang akan dihapus: ");
                    BSTNode toDelete = bstTree.search(delKey);
                    if (toDelete == null) {
                        System.out.println("Data dengan NIM/ID " + delKey + " tidak ditemukan.");
                    } else {
                        System.out.println("Data yang akan dihapus: " + toDelete);
                        String confirm = readText(input, "Yakin ingin menghapus node ini? (y/n): ");
                        if (confirm.equalsIgnoreCase("y")) {
                            boolean deleted = bstTree.delete(delKey);
                            if (deleted) {
                                System.out.println("Data dengan NIM/ID " + delKey + " berhasil dihapus dari BST.");
                            } else {
                                System.out.println("Gagal menghapus data.");
                            }
                        } else {
                            System.out.println("Penghapusan dibatalkan.");
                        }
                    }
                    break;

                case 4:
                    bstTree.inorder();
                    break;

                case 5:
                    bstTree.preorder();
                    bstTree.postorder();
                    break;

                case 6:
                    bstTree.printTree();
                    break;

                case 7:
                    bstTree.clear();
                    loadDefaultData();
                    System.out.println("\nData simulasi awal berhasil dimuat ulang ke dalam BST.");
                    bstTree.printTree();
                    break;

                case 0:
                    System.out.println("Kembali ke menu utama SIAAKAD.");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia. Silakan masukkan angka sesuai menu.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n========================================================");
        System.out.println("    MODUL 2: MANAJEMEN NILAI MAHASISWA (BINARY SEARCH TREE)   ");
        System.out.println("========================================================");
        System.out.println("1. Tambah Data Nilai Mahasiswa (Insert)");
        System.out.println("2. Cari Data Nilai Mahasiswa (Search)");
        System.out.println("3. Hapus Data Nilai Mahasiswa (Delete 3 Kasus LN 09)");
        System.out.println("4. Tampilkan Data Terurut Menaik (Inorder Traversal)");
        System.out.println("5. Tampilkan Preorder & Postorder Traversal");
        System.out.println("6. Visualisasi Struktur Pohon (Print Tree)");
        System.out.println("7. Muat Ulang Data Simulasi");
        System.out.println("0. Kembali ke Menu Utama SIAAKAD");
        System.out.println("========================================================");
    }

    private static void loadDefaultData() {
        bstTree.insert(50, "Dimas Pratama", 85.5);
        bstTree.insert(30, "Andi Saputra", 78.0);
        bstTree.insert(70, "Citra Lestari", 92.0);
        bstTree.insert(20, "Budi Santoso", 72.5);
        bstTree.insert(40, "Dewi Anggraini", 81.0);
        bstTree.insert(60, "Eko Prasetyo", 88.0);
        bstTree.insert(80, "Fajar Nugraha", 95.5);
    }

    private static int readInteger(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat.");
            }
        }
    }

    private static long readLong(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka numerik.");
            }
        }
    }

    private static double readDouble(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("Input nilai harus berupa angka desimal (contoh: 85.5 atau 3.75).");
            }
        }
    }

    private static String readText(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input tidak boleh kosong.");
        }
    }
}
