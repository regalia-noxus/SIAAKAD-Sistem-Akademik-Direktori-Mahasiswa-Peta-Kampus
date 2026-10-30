package graph;

//Modul Graph untuk Sistem Navigasi Kampus
//Setiap lokasi menjadi vertex dan setiap jalur antar lokasi menjadi edge
//Graph menggunakan Adjacency List yang dibuat manual dengan array dan linked list
public class GraphNavigation {
    private String[] locations;             //Array untuk menyimpan nama lokasi berdasarkan nomor indeks
    private AdjNode[] adjacencyList;        //Array untuk menyimpan head linked list dari setiap lokasi
    private int locationCount;              //Menyimpan jumlah lokasi yang sudah berhasil ditambahkan
    private static final int INFINITY = Integer.MAX_VALUE;      //Nilai untuk menandai jarak yang belum ditemukan oleh Dijkstra

    //Class Node untuk menyimpan satu hubungan dari lokasi ke lokasi lain
    private static class AdjNode {
        int destination;    //Tujuan
        int distance;       //Jarak
        AdjNode next;       //Menunjuk ke node berikutnya dalam linked list

        //Membuat node baru dengan tujuan dan jarak yang sudah ditentukan
        AdjNode(int destination, int distance) {
            this.destination = destination;
            this.distance = distance;
            this.next = null;       //Node baru belum terhubung dengan node lain
        }
    }

    //Menentukan jumlah maksimal lokasi yang dapat disimpan di dalam Graph
    public GraphNavigation(int maxLocations) {
        //Kapasitas tidak boleh nol atau negatif
        if (maxLocations <= 0) {
            maxLocations = 1;
        }

        locations = new String[maxLocations];       //Membuat array nama lokasi sesuai kapasitas yang diberikan
        adjacencyList = new AdjNode[maxLocations];  //Membuat array head linked list untuk setiap lokasi
        locationCount = 0;
    }

    //Menambahkan lokasi baru ke dalam Graph
    public boolean addLocation(String locationName) {
        //Validasi nama lokasi tidak null, kosong, atau hanya spasi
        if (locationName == null || locationName.trim().isEmpty()) {
            System.out.println("Nama lokasi tidak boleh kosong.");
            return false;
        }
        locationName = locationName.trim();

        //Mengecek jumlah lokasi sudah mencapai kapasitas array
        if (locationCount >= locations.length) {
            System.out.println("Kapasitas lokasi sudah penuh.");
            return false;
        }

        //Memeriksa nama lokasi agar tidak terduplikasi
        if (getLocationIndex(locationName) != -1) {
            System.out.println("Lokasi sudah terdaftar.");
            return false;
        }

        locations[locationCount] = locationName;        //Menyimpan nama lokasi pada indeks kosong berikutnya
        adjacencyList[locationCount] = null;            //Daftar tetangga lokasi baru masih kosong karena belum punya jalur
        locationCount++;                                //Menambah jumlah lokasi setelah data berhasil disimpan
        return true;
    }

    //Mencari indeks lokasi berdasarkan nama
    private int getLocationIndex(String locationName) {
        if (locationName == null) {
            return -1;
        }

        //Memeriksa setiap lokasi yang sudah terdaftar
        for (int i = 0; i < locationCount; i++) {
            if (locations[i].equalsIgnoreCase(locationName.trim())) {
                return i;
            }
        }
        return -1;      //lokasi tidak ditemukan
    }

    //Menambahkan jalur dua arah antara lokasi awal dan lokasi tujuan
    public boolean addEdge(String startLocation, String endLocation, int distance) {
        int start = getLocationIndex(startLocation);    //Mengubah nama lokasi awal menjadi indeks array
        int end = getLocationIndex(endLocation);        //Mengubah nama lokasi tujuan menjadi indeks array

        if (start == -1 || end == -1) {
            System.out.println("Lokasi awal atau tujuan tidak ditemukan.");
            return false;
        }

        if (start == end) {
            System.out.println("Lokasi tidak bisa dihubungkan ke dirinya sendiri.");
            return false;
        }

        if (distance < 0) {
            System.out.println("Jarak tidak boleh negatif.");
            return false;
        }

        //Graph tidak berarah sehingga kedua arah disimpan
        //Jalur A ke B, jalur B ke A
        addOrUpdateNeighbor(start, end, distance);
        addOrUpdateNeighbor(end, start, distance);
        return true;
    }

    //Menambahkan tetangga (lokasi tujuan ke linked list). Jika jalur sudah ada jaraknya diupdate
    private void addOrUpdateNeighbor(int source, int destination, int distance) {
        AdjNode newNode = new AdjNode(destination, distance);

        //Jika linked list masih kosong node baru menjadi node pertama
        if (adjacencyList[source] == null) {
            adjacencyList[source] = newNode;
            return;
        }

        AdjNode current = adjacencyList[source];

        //Menelusuri linked list sampai hubungan ditemukan atau mencapai node terakhir
        while (current != null) {
            //Mengecek apakah lokasi tujuan sudah ada di daftar tetangga
            if (current.destination == destination) {
                current.distance = distance;
                return;
            }

            //Jika node berikutnya kosong, current adalah node terakhir
            if (current.next == null) {
                current.next = newNode;
                return;
            }
            current = current.next;     //Berpindah ke node berikutnya
        }
    }

    //Menampilkan seluruh nama lokasi yang sudah dimasukkan ke dalam Graph
    public void displayLocations() {
        System.out.println("\n===== DAFTAR LOKASI KAMPUS =====");

        if (locationCount == 0) {
            System.out.println("Belum ada lokasi yang terdaftar.");
            return;
        }

        for (int i = 0; i < locationCount; i++) {
            System.out.println((i + 1) + ". " + locations[i]);
        }
    }

    //Menampilkan setiap jalur hanya satu kali
    public void displayRoads() {
        System.out.println("\n===== DAFTAR JALUR KAMPUS =====");
        boolean found = false;

        //Memeriksa daftar tetangga dari setiap lokasi
        for (int i = 0; i < locationCount; i++) {
            AdjNode current = adjacencyList[i];

            //Menelusuri semua tetangga dari lokasi yang sedang diperiksa
            while (current != null) {
                if (i < current.destination) {
                    System.out.println(locations[i] + " <-> "
                        + locations[current.destination] + " : "
                        + current.distance + " meter");
                    found = true;
                }
                current = current.next;     //Melanjutkan pemeriksaan ke node tetangga berikutnya
            }

        }

        //Tidak ada hubungan antar lokasi
        if (!found) {
            System.out.println("Belum ada jalur yang terdaftar.");
        }
    }

    //Menampilkan struktur Graph dalam Adjacency List
    public void printGraph() {
        System.out.println("\n===== ADJACENCY LIST =====");
        if (locationCount == 0) {
            System.out.println("Graph masih kosong.");
            return;
        }

        //Menampilkan daftar tetangga untuk setiap lokasi
        for (int i = 0; i < locationCount; i++) {
            System.out.print(locations[i] + " : ");
            AdjNode current = adjacencyList[i];
            if (current == null) {
                System.out.print("Belum memiliki jalur");
            }

            //Menelusuri dan menampilkan seluruh node tetangga
            while (current != null) {
                System.out.print(locations[current.destination]
                    + " (" + current.distance + " meter)");
                if (current.next != null) {
                    System.out.print(" -> ");
                }
                current = current.next;     //Berpindah ke node tetangga berikutnya
            }

            System.out.println();
        }
    }

    //Breadth First Search menelusuri lokasi berdasarkan tingkat kedekatan
    public void bfs(String startLocation) {
        int start = getLocationIndex(startLocation);
        if (start == -1) {
            System.out.println("Lokasi awal tidak ditemukan.");
            return;
        }

        boolean[] visited = new boolean[locationCount];     //Mencatat lokasi yang sudah dikunjungi
        int[] queue = new int[locationCount];               //Antrian untuk BFS
        int front = 0;
        int rear = 0;

        visited[start] = true;
        queue[rear] = start;        //Indeks lokasi awal ke bagian belakang antrian
        rear++;

        System.out.print("Hasil BFS: ");
        //Looping selama masih ada lokasi di dalam antrian
        while (front < rear) {
            int currentIndex = queue[front];            //Mengambil lokasi paling depan dari antrian
            front++;

            System.out.print(locations[currentIndex] + " ");
            AdjNode current = adjacencyList[currentIndex];      //Mengambil daftar tetangga

            //Mengecek setiap tetangga dari lokasi
            while (current != null) {
                int neighbor = current.destination;

                //Tetangga belum dikunjungi
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue[rear] = neighbor;     //Memasukkan tetangga ke bagian belakang antrian
                    rear++;
                }
                current = current.next;         //Melanjutkan ke tetangga berikutnya
            }
        }
        System.out.println();
    }

    //Depth First Search menelusuri jalur sedalam mungkin menggunakan rekursi
    public void dfs(String startLocation) {
        int start = getLocationIndex(startLocation);        //Mencari indeks lokasi yang digunakan sebagai titik awal DFS
        if (start == -1) {
            System.out.println("Lokasi awal tidak ditemukan.");
            return;
        }

        boolean[] visited = new boolean[locationCount];     //Menyimpan lokasi yang sudah dikunjungi proses DFS
        System.out.print("Hasil DFS: ");
        dfsVisit(start, visited);                           //Penelusuran rekursif dari lokasi awal
        System.out.println();
    }

    //Method rekursif yang mengunjungi satu lokasi lalu memeriksa semua tetangganya pada DFS
    private void dfsVisit(int currentIndex, boolean[] visited) {
        visited[currentIndex] = true;
        System.out.print(locations[currentIndex] + " ");

        AdjNode current = adjacencyList[currentIndex];      //Mengambil daftar tetangga dari lokasi saat ini
        //Memeriksa setiap tetangga satu per satu
        while (current != null) {
            int neighbor = current.destination;

            //Jika tetangga belum dikunjungi lanjutkan penelusuran ke tetangga
            if (!visited[neighbor]) {
                dfsVisit(neighbor, visited);
            }

            //Setelah satu tetangga selesai diproses lanjutkan ke tetangga berikutnya
            current = current.next;
        }
    }

    //Dijkstra mencari jalur dengan total jarak paling pendek
    public void dijkstra(String startLocation, String endLocation) {
        //Mencari indeks lokasi awal dan lokasi tujuan
        int start = getLocationIndex(startLocation);
        int end = getLocationIndex(endLocation);

        if (start == -1 || end == -1) {
            System.out.println("Lokasi awal atau tujuan tidak ditemukan.");
            return;
        }

        int[] distance = new int[locationCount];            //Menyimpan jarak terpendek sementara dari lokasi awal
        int[] previous = new int[locationCount];            //Menyimpan lokasi sebelumnya untuk menyusun kembali rute
        boolean[] visited = new boolean[locationCount];     //Menandai lokasi yang jarak terpendeknya sudah ditetapkan

        //Mengatur nilai awal untuk seluruh lokasi
        for (int i = 0; i < locationCount; i++) {
            distance[i] = INFINITY;                         //Semua lokasi belum dapat dijangkau di awal pencarian
            previous[i] = -1;                               //Lokasi belum memiliki lokasi sebelumnya
        }

        distance[start] = 0;
        //Memproses lokasi sampai tujuan ditemukan
        for (int count = 0; count < locationCount; count++) {
            int currentIndex = findMinimumDistance(distance, visited);  //Mengambil lokasi belum dikunjungi dengan jarak sementara paling kecil

            //Proses pencarian dihentikan
            if (currentIndex == -1) {
                break;
            }

            visited[currentIndex] = true;
            //Jika lokasi tujuan sudah diproses pencarian tidak dilanjutkan
            if (currentIndex == end) {
                break;
            }

            AdjNode current = adjacencyList[currentIndex];      //Mengambil daftar tetangga dari lokasi yang sedang diproses
            //Memeriksa kemungkinan jalur yang lebih pendek melalui setiap tetangga
            while (current != null) {
                int neighbor = current.destination;

                //Tetangga hanya diperiksa jika belum dikunjungi dan lokasi dapat dijangkau
                if (!visited[neighbor] && distance[currentIndex] != INFINITY) {
                    int newDistance = distance[currentIndex] + current.distance;        //Menghitung jarak baru melalui lokasi saat ini menuju tetangga

                    //Jika jarak baru lebih kecil simpan jarak dan lokasi sebelumnya
                    if (newDistance < distance[neighbor]) {
                        distance[neighbor] = newDistance;
                        previous[neighbor] = currentIndex;
                    }
                }
                current = current.next;     //Berpindah ke tetangga berikutnya
            }
        }

        //Jika jarak tujuan masih INFINITY berarti tidak ada jalur yang menghubungkan keduanya
        if (distance[end] == INFINITY) {
            System.out.println("Tidak ada jalur dari " + startLocation
                + " ke " + endLocation + ".");
            return;
        }

        int[] path = new int[locationCount];        //Menyimpan urutan indeks lokasi pada rute yang ditemukan
        int pathCount = 0;

        int currentIndex = end;
        //Menelusuri array previous sampai mencapai lokasi awal yang memiliki nilai -1
        while (currentIndex != -1) {
            //Menyimpan lokasi saat ini ke dalam array path
            path[pathCount] = currentIndex;
            pathCount++;

            //Berpindah ke lokasi sebelumnya dari hasil proses Dijkstra
            currentIndex = previous[currentIndex];
        }

        System.out.println("\n===== RUTE TERPENDEK DI KAMPUS =====");
        System.out.println("Lokasi awal  : " + locations[start]);
        System.out.println("Lokasi tujuan: " + locations[end]);
        System.out.print("Jalur        : ");

        //Array path tersimpan dari tujuan ke awal sehingga ditampilkan dari indeks terakhir
        for (int i = pathCount - 1; i >= 0; i--) {
            System.out.print(locations[path[i]]);
            if (i > 0) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
        System.out.println("Total jarak  : " + distance[end] + " meter");
    }

    //Mencari lokasi yang belum dikunjungi dengan jarak paling kecil
    //Method untuk Dijkstra menentukan lokasi yang diproses berikutnya
    private int findMinimumDistance(int[] distance, boolean[] visited) {
        int minimum = INFINITY;         //Nilai minimum dimulai dari INFINITY supaya jarak yang lebih kecil dapat ditemukan
        int minimumIndex = -1;          //Nilai -1 belum ada lokasi yang memenuhi kondisi pencarian

        //Memeriksa seluruh lokasi yang sudah terdaftar
        for (int i = 0; i < locationCount; i++) {
            //Lokasi dipilih jika belum dikunjungi dan jaraknya lebih kecil dari nilai minimum
            if (!visited[i] && distance[i] < minimum) {
                minimum = distance[i];
                minimumIndex = i;
            }
        }
        return minimumIndex;    //Mengembalikan indeks lokasi dengan jarak paling kecil atau -1 jika tidak ditemukan
    }
}
