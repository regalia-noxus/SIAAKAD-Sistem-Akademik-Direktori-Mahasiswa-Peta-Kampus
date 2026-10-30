import java.util.HashMap;
import java.util.Map;

public class StudentHashTable {

    private HashMap<String, Mahasiswa> table;

    public StudentHashTable() {
        table = new HashMap<>();
    }

    // Berfungsi untuk Menambahkan data mahasiswa
    public boolean insert(Mahasiswa mahasiswa) {
        String nim = mahasiswa.getNim();

        if (table.containsKey(nim)) {
            return false;
        }

        table.put(nim, mahasiswa);
        return true;
    }

    // Berfungsi untuk Mencari mahasiswa berdasarkan NIM
    public Mahasiswa search(String nim) {
        return table.get(nim);
    }

    // Berfungsi untuk Menghapus mahasiswa berdasarkan NIM
    public boolean delete(String nim) {
        if (table.containsKey(nim)) {
            table.remove(nim);
            return true;
        }

        return false;
    }

    // Berfungsi untuk Menampilkan seluruh data mahasiswa
    public void printTable() {
        if (table.isEmpty()) {
            System.out.println("Belum ada data mahasiswa.");
            return;
        }

        System.out.println("\n===== DAFTAR MAHASISWA =====");

        for (Map.Entry<String, Mahasiswa> entry
                : table.entrySet()) {
            System.out.println("----------------------------");
            System.out.println(entry.getValue());
        }

        System.out.println("----------------------------");
    }
}