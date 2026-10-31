package bst;

public class BSTNode {
    long key;
    String nama;
    double nilai;
    BSTNode left;
    BSTNode right;

    public BSTNode(long key, String nama, double nilai) {
        this.key = key;
        this.nama = nama;
        this.nilai = nilai;
        this.left = null;
        this.right = null;
    }

    public long getKey() {
        return key;
    }

    public String getNama() {
        return nama;
    }

    public double getNilai() {
        return nilai;
    }

    @Override
    public String toString() {
        return String.format("[NIM/ID: %-10d | Nama: %-20s | Nilai: %.2f]", key, nama, nilai);
    }
}
