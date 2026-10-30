import java.util.Scanner;

public class MainHashTable {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        StudentHashTable dataMahasiswa = new StudentHashTable();

        int pilihan = -1;

        do {
            System.out.println("\n====================================");
            System.out.println("  SISTEM MANAJEMEN DATA MAHASISWA");
            System.out.println("        MENGGUNAKAN HASH TABLE");
            System.out.println("====================================");
            System.out.println("1. Tambah Data Mahasiswa");
            System.out.println("2. Cari Data Mahasiswa");
            System.out.println("3. Hapus Data Mahasiswa");
            System.out.println("4. Tampilkan Semua Mahasiswa");
            System.out.println("0. Keluar");
            System.out.println("====================================");
            System.out.print("Pilih menu: ");

            if (!input.hasNextInt()) {
                System.out.println("Pilihan harus berupa angka!");
                input.nextLine();
                continue;
            }

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1: {
                    // Berfungsi untuk INSERT data 
                    System.out.println("\n--- TAMBAH DATA MAHASISWA ---");

                    System.out.print("Masukkan NIM: ");
                    String nim = input.nextLine().trim();

                    if (nim.isEmpty()) {
                        System.out.println("NIM tidak boleh kosong.");
                        break;
                    }

                    System.out.print("Masukkan Nama: ");
                    String nama = input.nextLine().trim();

                    if (nama.isEmpty()) {
                        System.out.println("Nama tidak boleh kosong.");
                        break;
                    }

                    System.out.print("Masukkan IPK (0-4): ");

                    if (!input.hasNextDouble()) {
                        System.out.println("IPK harus berupa angka.");
                        input.nextLine();
                        break;
                    }

                    double ipk = input.nextDouble();
                    input.nextLine();

                    if (ipk < 0 || ipk > 4) {
                        System.out.println(
                                "IPK harus berada antara 0 dan 4."
                        );
                        break;
                    }

                    Mahasiswa mahasiswa =
                            new Mahasiswa(nim, nama, ipk);

                    if (dataMahasiswa.insert(mahasiswa)) {
                        System.out.println(
                                "Data mahasiswa berhasil ditambahkan!"
                        );
                    } else {
                        System.out.println(
                                "NIM sudah terdaftar!"
                        );
                    }

                    break;
                }

                case 2: {
                    // Berfungsi untuk SEARCH data
                    System.out.println("\n--- CARI DATA MAHASISWA ---");

                    System.out.print("Masukkan NIM yang dicari: ");
                    String nimCari = input.nextLine().trim();

                    Mahasiswa hasil =
                            dataMahasiswa.search(nimCari);

                    if (hasil != null) {
                        System.out.println(
                                "\nData mahasiswa ditemukan:"
                        );
                        System.out.println(hasil);
                    } else {
                        System.out.println(
                                "Mahasiswa dengan NIM "
                                + nimCari + " tidak ditemukan."
                        );
                    }

                    break;
                }

                case 3: {
                    // Berfungsi untuk DELETE data
                    System.out.println("\n--- HAPUS DATA MAHASISWA ---");

                    System.out.print("Masukkan NIM yang dihapus: ");
                    String nimHapus = input.nextLine().trim();

                    Mahasiswa mahasiswa =
                            dataMahasiswa.search(nimHapus);

                    if (mahasiswa != null) {
                        System.out.println(
                                "\nData mahasiswa yang akan dihapus:"
                        );
                        System.out.println(mahasiswa);

                        System.out.print(
                                "Yakin menghapus data? (y/n): "
                        );

                        String konfirmasi =
                                input.nextLine().trim();

                        if (konfirmasi.equalsIgnoreCase("y")) {
                            dataMahasiswa.delete(nimHapus);

                            System.out.println(
                                    "Data mahasiswa berhasil dihapus."
                            );
                        } else {
                            System.out.println(
                                    "Penghapusan dibatalkan."
                            );
                        }
                    } else {
                        System.out.println(
                                "Mahasiswa dengan NIM "
                                + nimHapus + " tidak ditemukan."
                        );
                    }

                    break;
                }

                case 4:
                    // Berfungsi untuk PRINT TABLE
                    dataMahasiswa.printTable();
                    break;

                case 0:
                    System.out.println(
                            "Program selesai. Terima kasih!"
                    );
                    break;

                default:
                    System.out.println(
                            "Menu tidak tersedia. Silakan coba lagi."
                    );
            }

        } while (pilihan != 0);

        input.close();
    }
}