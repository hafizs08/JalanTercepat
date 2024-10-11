import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class JalanTercepat {
    static class Point {
        int x, y, tmp;
        String jalur;

        public Point(int x, int y, int tmp, String jalur) {
            this.x = x;
            this.y = y;
            this.tmp = tmp;
            this.jalur = jalur;
        }
    }

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static String[] arah = {"kiri", "kanan", "atas", "bawah"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> mapInput = new ArrayList<>();
        String line;

        while (!(line = sc.nextLine()).equals("ok")) {
            mapInput.add(line);
        }

        int n = mapInput.size();
        int m = mapInput.get(0).length();
        char[][] map = new char[n][m];

        Point start = null, end = null;

        for (int i = 0; i < n; i++) {
            String row = mapInput.get(i);
            for (int j = 0; j < m; j++) {
                map[i][j] = row.charAt(j);
                if (map[i][j] == '^') {
                    start = new Point(i, j, 0, "");
                } else if (map[i][j] == '*') {
                    end = new Point(i, j, 0, "");
                }
            }
        }

        if (start == null || end == null) {
            System.out.println("enggak ada tandah ^ atau *");
            return;
        }

        String hasil = bfs(map, start, end, n, m);
        System.out.println(hasil);
    }

    static String bfs(char[][] map, Point start, Point end, int n, int m) {
        boolean[][] rumah = new boolean[n][m];
        Queue<Point> baris = new LinkedList<>();
        baris.offer(start);
        rumah[start.x][start.y] = true;

        while (!baris.isEmpty()) {
            Point jalan = baris.poll();

            if (jalan.x == end.x && jalan.y == end.y) {
                return tercepat(jalan.jalur) + "\n" + jalan.tmp + " langkah";
            }

            for (int i = 0; i < 4; i++) {
                int nx = jalan.x + dx[i];
                int ny = jalan.y + dy[i];

                if (isValid(nx, ny, n, m, map, rumah)) {
                    rumah[nx][ny] = true;
                    baris.offer(new Point(nx, ny, jalan.tmp + 1, jalan.jalur + arah[i] + " "));
                }
            }
        }
        return "tidak ada jalan";
    }

    static boolean isValid(int x, int y, int n, int m, char[][] map, boolean[][] rumah) {
        return x >= 0 && x < n && y >= 0 && y < m && map[x][y] != '#' && !rumah[x][y];
    }

    static String tercepat(String jalur) {
        String[] steps = jalur.trim().split(" ");
        StringBuilder hasil = new StringBuilder();
        String tujuan = "";
        int tambah = 0;

        for (String step : steps) {
            if (step.equals(tujuan)) {
                tambah++;
            } else {
                if (!tujuan.isEmpty()) {
                    hasil.append(tambah).append(" ").append(tujuan).append("\n");
                }
                tujuan = step;
                tambah = 1;
            }
        }
        hasil.append(tambah).append(" ").append(tujuan);
        return hasil.toString();
    }
}
