public class Mahasiswa {
    private String nim;
    private String nama;
    private double ipk;

    public Mahasiswa(String nim, String nama, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.ipk = ipk;
    }

    //Berfungsi untuk Get data Nim
    public String getNim() {
        return nim;
    }

  //Berfungsi untuk Get data Nama
    public String getNama() {
        return nama;
    }

  //Berfungsi untuk Get data IPK
    public double getIpk() {
        return ipk;
    }

  //Berfungsi untuk menampilkan data NIM, Nama dan Npk
    @Override
    public String toString() {
        return "NIM: " + nim
                + " | Nama: " + nama
                + " | IPK: " + ipk;
    }
}