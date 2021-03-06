import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.lang.*;

class SPLSolver
{
    double[][] A; //matriks
    double[] R; //hasil
    String[] e; //persamaan
    int n; //baris
    int m; //kolom
    boolean noSolution = false; //true jika nggak ada solusi
    boolean infiniteSolution = false; //true jika solusi tdk terbatas
    double[] datatahunawal= {1950, 1955, 1960, 1965, 1966, 1967, 1968, 1969};
	double[] datahargaawal= {33525, 46519, 53941, 72319, 75160, 76160, 84690, 90866};
    double[] data10awal= {11.6, 10.3, 9.1, 8.2, 7.4, 6.8};
	double[] data20awal= {10.5, 9.2, 8.2, 7.4, 6.7, 6.1};
	double[] datasuhuawal = {5, 10, 15, 20, 25, 30};
	double[] dataxawal= {0.1, 0.3, 0.5, 0.7, 0.9, 1.1, 1.3};
	double[] dataFXawal = {0.003, 0.067, 0.148, 0.248, 0.370, 0.518, 0.697};
	double[] data10baru, data20baru, datasuhubaru, dataxbaru, dataFXbaru, datatahunbaru; // maksimal 100 data
	double[] datahargabaru; // maksimal 100 data
	double eps = 0.00000000000000001;

    BufferedReader in;
    PrintWriter out;

    void Read1SPL()
    {
        Scanner in = new Scanner(System.in);

        System.out.printf("Masukkan jumlah baris: ");
        n = Integer.parseInt(in.nextLine());
        System.out.printf("Masukkan jumlah kolom: ");
        m = Integer.parseInt(in.nextLine());

        System.out.println();
        System.out.println();

        A = new double[n+1][m+1];

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                System.out.printf("Elemen matriks baris ke-" + i + " kolom ke-" + j + ": ");
                A[i][j] = Double.parseDouble(in.nextLine());
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Matriks awal");
        System.out.println("-------------------------------------------------------------------------------");
        PrintMatrix1(A);
    }

    void Read2SPL() throws IOException
    {
        Scanner inf = new Scanner(System.in);

        System.out.printf("Masukkan nama file input: ");
        String input = inf.nextLine() + ".txt";
        System.out.printf("Masukkan nama file output: ");
        String output = inf.nextLine() + ".txt";

        in = new BufferedReader(new FileReader(input));
        out = new PrintWriter(new FileWriter(output));

        String line = in.readLine();
        StringTokenizer st = new StringTokenizer(line);
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        A = new double[n+1][m+1];

        for (int i = 1; i <= n; i++)
        {
            line = in.readLine();
            st = new StringTokenizer(line);
            for(int j = 1; j <= m; j++)
            {
                A[i][j] = Double.parseDouble(st.nextToken());
            }
        }
        in.close();
        out.println("Matriks awal");
        out.println("-------------------------------------------------------------------------------");
        PrintMatrix2(out, A);
    }

    void PrintMatrix1(double[][] A)
    {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                System.out.printf(A[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void PrintMatrix2(PrintWriter out, double[][] A)
    {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                out.print(A[i][j] + "  ");
            }
            out.println();
        }
        out.println();
    }

    void AssignResult1()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            System.out.println();
            System.out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                System.out.println();
                System.out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    System.out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];
                for (int i = 1; i <= n; i++)
                {
                    R[i] = A[i][m];
                    System.out.println("x" + i + " = " + A[i][m]);
                }
            }
        }
    }

    void AssignResult2()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];
                for (int i = 1; i <= n; i++)
                {
                    R[i] = A[i][m];
                    out.print("x" + i + " = " + A[i][m]);
                    out.println();
                }
            }
        }
        out.close();
    }

    void Solver1SPL()
    {
        int i = 1;
        int j = 1;
        int k;
        while (i <= n && j <= m)
        {
            //cari angka bukan nol di kolom j atau di bawah baris i
            k = i;
            while (k <= n && A[k][j] == 0)
            {
                k++;
            }
            //jika ada di baris k
            if (k <= n)
            {
                //jika k bukan i, swap baris i dengan baris k
                if (k != i)
                {
                    Swap(A, i, k, j);
                    System.out.println("Tukar baris ke-" + i + " dengan baris ke-" + k);
                    System.out.println("-------------------------------------------------------------------------------");
                    PrintMatrix1(A);
                }

                //jika A[i][j] bukan 1, bagi baris i dengan A[i][j]
                if (A[i][j] != 1)
                {
                    System.out.println("Bagi baris ke-" + i + " dengan " + A[i][j]);
                    System.out.println("-------------------------------------------------------------------------------");
                    Divide(A, i, j);
                    PrintMatrix1(A);
                }
                //hapus semua angka bukan nol dari kolom j dengan mengurangi masing-masing baris
                //selain i dengan sejumlah kelipatan i
                System.out.println("Kurangi masing-masing baris dengan kelipatan baris ke-" + i);
                System.out.println("-------------------------------------------------------------------------------");
                Eliminate(A, i, j);
                PrintMatrix1(A);
                i++;
            }
            j++;
        }
    }

    void Solver2SPL()
    {
        int i = 1;
        int j = 1;
        int k;
        while (i <= n && j <= m)
        {
            //cari angka bukan nol di kolom j atau di bawah baris i
            k = i;
            while (k <= n && A[k][j] == 0)
            {
                k++;
            }
            //jika ada di baris k
            if (k <= n)
            {
                //jika k bukan i, swap baris i dengan baris k
                if (k != i)
                {
                    out.println("Tukar baris ke-" + i + " dengan baris ke-" + k);
                    out.println("-------------------------------------------------------------------------------");
                    Swap(A, i, k, j);
                    PrintMatrix2(out, A);
                }

                //jika A[i][j] bukan 1, bagi baris i dengan A[i][j]
                if (A[i][j] != 1)
                {
                    out.println("Bagi baris ke-" + i + " dengan " + A[i][j]);
                    out.println("-------------------------------------------------------------------------------");
                    Divide(A, i, j);
                    PrintMatrix2(out, A);
                }
                //hapus semua angka bukan nol dari kolom j dengan mengurangi masing-masing baris
                //selain i dengan sejumlah kelipatan iSystem.out.println("Kurangi masing-masing baris dengan kelipatan baris ke-" + i);
                out.println("Kurangi masing-masing baris dengan kelipatan baris ke-" + i);
                out.println("-------------------------------------------------------------------------------");
                Eliminate(A, i, j);
                PrintMatrix2(out, A);
                i++;
            }
            j++;
        }
    }

    void Swap(double[][] A, int i, int k, int j)
    {
        int m = A[0].length - 1;
        double temp;
        for (int q = j; q <= m; q++)
        {
            temp = A[i][q];
            A[i][q] = A[k][q];
            A[k][q] = temp;
        }
    }

    void Divide(double[][] A, int i, int j)
    {
        int m = A[0].length - 1;
        for (int q = j+1; q <= m; q++)
        {
            A[i][q] /= A[i][j];
        }
        A[i][j] = 1;
    }

   void Eliminate(double[][] A, int i, int j)
   {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for (int p = 1; p <= n; p++)
        {
            if (p != i && A[p][j] != 0 )
            {
                for (int q = j+1; q <= m; q++)
                {
                    A[p][q] -= A[p][j]*A[i][q];
                }
                A[p][j] = 0;
            }
        }
    }

    boolean IsRowZero(int i)
    {
        boolean b = true;
        int m = A[0].length - 1;
        for (int p = 1; p <= m; p++)
        {
            if (A[i][p] != 0)
            {
                b = false;
            }
        }
        return b;
    }

    boolean IsNoSolutionRow(int i)
    {
        boolean b = false;
        int m = A[0].length - 2;
        if (A[i][m+1] != 0)
        {
            b = true;
            for (int p = 1; p <= m; p++)
            {
                if (A[i][p] != 0)
                {
                    b = false;
                }
            }
        }
        return b;
    }

    boolean IsNoSolution()
    {
        boolean b = false;
        boolean f = false;
        int n = A.length - 1;
        int m = A[0].length - 2;
        int p = n;
        while ((p >= 1) && !f)
        {
            if (IsRowZero(p))
            {
                p--;
            }
            else
            {
                if (IsNoSolutionRow(p))
                {
                    b = true;
                    f = true;
                }
                else
                {
                    f = true;
                }
            }
        }
        return b;
    }

    boolean IsInfiniteColumn(int i)
    {
        boolean b = true;
        int n = A.length - 1;
        boolean one = false; //true jika ketemu angka 1
        int zero = n-1; //counter angka 0
        for (int p = 1; p <= n; p++)
        {
            if (A[p][i] == 0)
            {
                zero--;
            }
            else
            {
                if (A[p][i] == 1)
                {
                    one = true;
                }
            }
        }
        if (zero == 0 && one)
        {
            b = false;
        }
        return b;
    }

    boolean IsInfiniteSolution()
    {
        boolean b = false;
        int m = A[0].length - 2;
        for (int p = m; p >= 1; p--) //tinjau dari kolom paling belakang
        {
            if (IsInfiniteColumn(p))
            {
                b = true;
            }
        }
        noSolution = IsNoSolution();
        if (noSolution)
        {
            b = false;
        }
        return b;
    }

    void GetInfiniteEquations()
    {
        int c = 0;
        int n = A.length - 1;
        int m = A[0].length - 2;
        for (int p = m; p >= 1; p--) //tinjau dari kolom paling belakang
        {
            if (!IsInfiniteColumn(p))
            {
                c++;
            }
        }
        e = new String[c+1]; //penampung persamaan
        int[] r = new int[c+1]; //buat nyimpen suatu persamaan ada di baris mana
        int u = 1;
        for (int q = 1; q <= m; q++)
        {
            if (!IsInfiniteColumn(q))
            {
                e[u] = "x" + q + " =";
                int k = 1;
                while (k <= m && A[k][q] != 1)
                {
                    k++;
                }
                r[u] = k;
                u++;
            }
        }
        u = 1;
        while (u <= e.length-1)
        {
            for (int a = 1; a <= m; a++)
            {
                if (a == r[u])
                {
                    for (int b = 1; b <= m+1; b++)
                    {
                        String y = e[u].substring(1,2);
                        int z = Integer.parseInt(y);
                        //nilai koefisien selain -1, 1, dan 0
                        if ((A[a][b] != 0) && (A[a][b] != 1) && (A[a][b] != -1) && (b != z) && (b != m+1))
                        {
                            double x = (-1)*A[a][b];
                            if (x < 0)
                            {
                                if (e[u].length() <= 5)
                                {
                                    e[u] += " -";
                                }
                                else
                                {
                                    e[u] = e[u].substring(0, e[u].length()-3);
                                    e[u] += " - ";
                                }
                                e[u] += A[a][b] + "x" + b + " + ";
                            }
                            else
                            {
                                e[u] += " " + (-1)*A[a][b] + "x" + b + " + ";
                            }
                        }
                        else
                        {
                            //nilai koefisien -1
                            if ((A[a][b] == -1) && (b != z) && (b != m+1))
                            {
                                e[u] += " x" + b + " + ";
                            }
                            else
                            {
                                //nilai koefisien 1
                                if ((A[a][b] == 1)&& (b != z) && (b != m+1))
                                {
                                    if (e[u].length() <= 5)
                                    {
                                        e[u] += " -";
                                    }
                                    else
                                    {
                                        e[u] = e[u].substring(0, e[u].length()-3);
                                        e[u] += " - ";
                                    }
                                    e[u] += "x" + b + " + ";
                                }
                                else
                                {
                                    //kolom konstanta
                                    if ((A[a][b] != 0) && (b == m+1))
                                    {
                                        if (A[a][b] < 0)
                                        {
                                            if (e[u].length() <= 5)
                                            {
                                                e[u] += " -";
                                            }
                                            else
                                            {
                                                e[u] = e[u].substring(0, e[u].length()-3);
                                                e[u] += " - ";
                                            }
                                            e[u] += (-1)*A[a][b] + " + ";
                                        }
                                        else
                                        {
                                            e[u] += " " + A[a][b] + " + ";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    e[u] = e[u].substring(0, e[u].length()-3);
                }
            }
            u++;
        }
    }
    
    // Kelompok Implementasi
    
    void ReadSPLTeknikSipil() throws IOException
    {
        Scanner inf = new Scanner(System.in);

        String input = "TeknikSipilIn.txt";
        String output = "TeknikSipilOut.txt";

        in = new BufferedReader(new FileReader(input));
        out = new PrintWriter(new FileWriter(output));

        String line = in.readLine();
        StringTokenizer st = new StringTokenizer(line);
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        A = new double[n+1][m+1];

        for (int i = 1; i <= n; i++)
        {
            line = in.readLine();
            st = new StringTokenizer(line);
            for(int j = 1; j <= m; j++)
            {
                A[i][j] = Double.parseDouble(st.nextToken());
            }
        }
        in.close();
        PrintMatrix1(A);
        PrintMatrix2(out, A);
    }
    void AssignResultTeknikSipil()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            System.out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                System.out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    System.out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];
                for (int i = 1; i <= 3; i++)
                {
                    R[i] = A[i][m];
                    System.out.println("F" + i + " = " + A[i][m]);
                }
                R[4] = A[4][m];
                System.out.println("H" + 2 + " = " + A[4][m]);

                R[5] = A[5][m];
                System.out.println("V" + 2 + " = " + A[5][m]);

                R[4] = A[4][m];
                System.out.println("V" + 3 + " = " + A[6][m]);
            }
        }
    }
    void AssignResultTeknikSipilTxt()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];
                for (int i = 1; i <= 3; i++)
                {
                    R[i] = A[i][m];
                    out.print("F" + i + " = " + A[i][m]);
                    out.println();
                }

                R[4] = A[4][m];
                out.print("H" + 2 + " = " + A[4][m]);
                out.println();

                R[5] = A[5][m];
                out.print("V" + 2 + " = " + A[5][m]);
                out.println();

                R[6] = A[6][m];
                out.print("V" + 3 + " = " + A[6][m]);
                out.println();
            }
        }
        out.close();
    }
    void ReadSPLArus() throws IOException
    {
        Scanner inf = new Scanner(System.in);

        String input = "ArusIn.txt";
        String output = "ArusOut.txt";

        in = new BufferedReader(new FileReader(input));
        out = new PrintWriter(new FileWriter(output));

        String line = in.readLine();
        StringTokenizer st = new StringTokenizer(line);
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        A = new double[n+1][m+1];

        for (int i = 1; i <= n; i++)
        {
            line = in.readLine();
            st = new StringTokenizer(line);
            for(int j = 1; j <= m; j++)
            {
                A[i][j] = Double.parseDouble(st.nextToken());
            }
        }
        in.close();
        PrintMatrix1(A);
        PrintMatrix2(out, A);
	}
	void AssignResultArus()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            System.out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                System.out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    System.out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];

                R[1] = A[1][m];
                System.out.println("I12" + " = " + A[1][m]);

                R[2] = A[2][m];
                System.out.println("I52" + " = " + A[2][m]);

                R[3] = A[3][m];
                System.out.println("I32" + " = " + A[3][m]);

                R[4] = A[4][m];
                System.out.println("I65" + " = " + A[4][m]);

                R[5] = A[5][m];
                System.out.println("I54" + " = " + A[5][m]);

                R[6] = A[6][m];
                System.out.println("I43" + " = " + A[6][m]);

                R[7] = A[7][m];
                System.out.println("V2" + " = " + A[7][m]);

                R[8] = A[8][m];
                System.out.println("V3" + " = " + A[8][m]);

                R[9] = A[9][m];
                System.out.println("V4" + " = " + A[9][m]);

                R[10] = A[10][m];
                System.out.println("V5" + " = " + A[10][m]);


            }
        }
    }

    void AssignResultArusTxt()
    {
        noSolution = IsNoSolution();
        infiniteSolution = IsInfiniteSolution();
        if (noSolution)
        {
            out.println("Tidak ada solusi");
        }
        else
        {
            if (infiniteSolution)
            {
                out.println("Solusi tidak terbatas");
                GetInfiniteEquations();
                int q = e.length - 1;
                for (int p = 1; p <= q; p++)
                {
                    out.println(e[p]);
                }
            }
            else
            {
                int n = A.length - 1;
                int m = A[0].length - 1;
                R = new double[n+1];

                R[1] = A[1][m];
                out.print("I12" + " = " + A[1][m]);
                out.println();

                R[2] = A[2][m];
                out.print("I52" + " = " + A[2][m]);
                out.println();

                R[3] = A[3][m];
                out.print("I32" + " = " + A[3][m]);
                out.println();

                R[4] = A[4][m];
                out.print("I65" + " = " + A[4][m]);
                out.println();

                R[5] = A[5][m];
                out.print("I54" + " = " + A[5][m]);
                out.println();

                R[6] = A[6][m];
                out.print("I43" + " = " + A[6][m]);
                out.println();

                R[7] = A[7][m];
                out.print("V2" + " = " + A[7][m]);
                out.println();

                R[8] = A[8][m];
                out.print("V3" + " = " + A[8][m]);
                out.println();

                R[9] = A[9][m];
                out.print("V4" + " = " + A[9][m]);
                out.println();

                R[10] = A[10][m];
                out.print("V5" + " = " + A[10][m]);
                out.println();


            }
        }
        out.close();
    }

    // kelompok menu

    void mainmenu() {
		System.out.println("--------------------------------------------");
		System.out.println("                   Menu                     ");
		System.out.println("--------------------------------------------");
		System.out.println();
		System.out.println(" 1. SPL Solver");
		System.out.println(" 2. Implementasi Metode Penyelesaian SPL");
		System.out.println(" 3. Interpolasi");
		System.out.println();
		System.out.println("--------------------------------------------");
	}

	void menuinput() {
		System.out.println();
		System.out.println("Metode input");
        System.out.println("1. Keyboard");
		System.out.println("2. File");
        System.out.printf("   Pilihan: ");
    }

    void menuImplementasi() {
		System.out.println();
		System.out.println(" 1. Rangka Statis Berbentuk Segitiga");
		System.out.println(" 2. Arus Pada Rangkaian Listrik");
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.printf("  Pilihan: ");
	}

	void menuinterpolasi() {
		System.out.println("--------------------------------------------");
		System.out.println("              Menu Interpolasi              ");
		System.out.println("--------------------------------------------");
		System.out.println();
		System.out.println(" 1. Mencari X");
		System.out.println(" 2. Mencari Konsentrasi Oksigen Jenuh");
		System.out.println(" 3. Harga Rumah");
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.printf("  Pilihan: ");
	}

	// KELOMPOK INTERPOLASI
	
	void interpolsolver() {
		int i = 1;
        int j = 1;
        int k;
        while (i <= n && j <= m)
        {
            //cari angka bukan nol di kolom j atau di bawah baris i
            k = i;
            while (k <= n && A[k][j] == 0)
            {
                k++;
            }
            //jika ada di baris k
            if (k <= n)
            {
                //jika k bukan i, swap baris i dengan baris k
                if (k != i)
                {
                    Swap(A, i, k, j);
                    System.out.println("Tukar baris ke-" + i + " dengan baris ke-" + k);
                    System.out.println("-------------------------------------------------------------------------------");
                }

                //jika A[i][j] bukan 1, bagi baris i dengan A[i][j]
                if (A[i][j] != 1)
                {
                    System.out.println("Bagi baris ke-" + i + " dengan " + A[i][j]);
                    System.out.println("-------------------------------------------------------------------------------");
                    Divide(A, i, j);
                }
                //hapus semua angka bukan nol dari kolom j dengan mengurangi masing-masing baris
                //selain i dengan sejumlah kelipatan i
                Eliminate(A, i, j);
                i++;
            }
            j++;
        }
     }
	void AssignResult1interp() {
                int n = A.length - 1;
                int m = A[0].length - 1;
                
                
                R = new double[n+1];
                System.out.print("Persamaan : f(x) = ");
                for (int i = 1; i <= n; i++)
                {	
					R[i] = A[i][m];
                    if ((A[i][m]>eps) || (A[i][m]<(-eps))){
						System.out.print(A[i][m] + "x^" + (i-1));
						if (i<n) {
							System.out.print(" + ");
						}
					} else
						System.out.println(A[i][m]);

                }
				System.out.println();
	}
	
	void penyelesaianinterp1(double[] x, double[] y, int idxmax, int tipe) { // menggunakan keyboard
		int i, j, colmax;
		
		System.out.println();
		System.out.println("Ingin menggunakan polinom berapa derajat?");
		System.out.printf("Tulis derajat : ");
		Scanner inp = new Scanner(System.in);
		int derajat = Integer.parseInt(inp.nextLine());
		colmax=derajat+1;
		n = idxmax;
		m = colmax;
		System.out.println("-------------------------------------------------------------------------------");
		switch(tipe) {
			case 1 : {
				System.out.println("                           Matriks X - F(x)                                 "); 
				break;
			}
			case 2 : {
				System.out.println("                           Matriks 10 Derajat                               "); 
				break;
			}
			case 3 : {
				System.out.println("                           Matriks 20 Derajat                               "); 
				break;
			}
			default : {
				System.out.println("                           Matriks Harga Rumah                              "); 
				break;
			}
		}
		System.out.println("-------------------------------------------------------------------------------");
		
		A = new double[idxmax+1][colmax+1];
			for (i=1; i<=idxmax; i++) {
			for (j=1; j<=colmax; j++) {
				if ((j == colmax)) {
					A[i][j] = y[i-1];
				} else
					A[i][j] = Math.pow(x[i-1], j-1);
				}
			}
	
		System.out.println();
        System.out.println();
        System.out.println("Matriks awal");
        System.out.println("-------------------------------------------------------------------------------");
	PrintMatrix1(A);
	}
	
	// mencari x //
	void tampilxfx(double[] datax, double[] datafx, int dat) {
		int i;

		System.out.printf("| x  |");
		for (i=0; i<dat; i++) {
			System.out.printf(" " +datax[i]+ " ");
			if (i<=dat) {
				System.out.printf("|");
			}
		} // menuliskan data x;
		System.out.println();
		System.out.printf("| Fx |");
		for (i=0; i<dat; i++) {
			System.out.printf(" "+datafx[i]+" ");
			if (i<=dat) {
				System.out.printf("|");
			}
		} // menuliskan data F(x);
		
		System.out.println();
	}

	void xfxsolver() {
		int i;
		int NDatax;
		
			System.out.println();
			System.out.println("-----Mencari Fx-----");
			System.out.println("1. Tampilkan data x dan Fx");
			//System.out.println("2. Input data x dan Fx baru");
			System.out.printf("  Masukkan Pilihan : ");
			

			Scanner pilih = new Scanner(System.in);
			int pil = Integer.parseInt(pilih.nextLine());

			if (pil==1) {
				NDatax = 7; //nilai data x dan fx sama
				System.out.println();
				System.out.println("Data x-f(x) awal : ");
				tampilxfx(dataxawal, dataFXawal, NDatax); //menampilkan tampilxf
				
				penyelesaianinterp1(dataxawal, dataFXawal, NDatax, 1);
				//panggil penyelesaian untuk x
			}
	}

	// akhir mencari x
	
	void konsentrasisolv1() {
		int i;
		
			System.out.println("-----Mencari Konsentrasi Oksigen-----");
			System.out.println("1. Tampilkan data-data awal");
			//System.out.println("2. Input data-data baru");
			System.out.printf("  Masukkan Pilihan : ");

			Scanner pilih = new Scanner(System.in);
			int pil = Integer.parseInt(pilih.nextLine());

			if (pil==1) {
				int NDatax = 6; //nilai data x dan fx sama
				System.out.println();
				System.out.println("Data untuk suhu 10 derajat : ");
				tampilxfx(datasuhuawal, data10awal, NDatax);
				System.out.printf("Untuk suhu 10 derajat, ");
				penyelesaianinterp1(datasuhuawal, data10awal, NDatax, 2);
				//panggil penyelesaian untuk suhu  x dan y								
			}
		}
		
		void konsentrasisolv2() {
		int i;
		
			System.out.println("-----Mencari Konsentrasi Oksigen-----");
			System.out.println("1. Tampilkan data-data awal");
			//System.out.println("2. Input data-data baru");
			System.out.printf("  Masukkan Pilihan : ");

			Scanner pilih = new Scanner(System.in);
			int pil = Integer.parseInt(pilih.nextLine());

			if (pil==1) {
				int NDatax = 6; //nilai data x dan fx sama
				//panggil penyelesaian untuk suhu  x dan y
				System.out.println();
				System.out.println("Data untuk suhu 20 derajat : ");
				tampilxfx(datasuhuawal, data20awal, NDatax);
				System.out.printf("Untuk suhu 20 derajat, ");
				penyelesaianinterp1(datasuhuawal, data20awal, NDatax, 3);
								
			}
		}
		
		void hargarumahsolv() {
		
			System.out.println("-----Mencari Harga Rumah-----");
			System.out.println("1. Tampilkan data-data awal");
			//System.out.println("2. Input data-data baru");
			System.out.printf("  Masukkan Pilihan : ");

			Scanner pilih = new Scanner(System.in);
			int pil = Integer.parseInt(pilih.nextLine());

			if (pil==1) {
				int NDatax = 8; //nilai data x dan fx sama
				tampilxfx(datatahunawal, datahargaawal, NDatax); //menampilkan tampilxfx
				penyelesaianinterp1(datatahunawal, datahargaawal, NDatax, 4);
				
				//panggil penyelesaian untuk suhu  x dan y
			}
		}
		
		void carix(int tipe) {
			System.out.println("Tulis x yang ingin dicari: ");
			System.out.printf("-> ");
			Scanner input = new Scanner(System.in);
			Double inp = Double.parseDouble(input.nextLine()); 
			
			if (tipe==4) {
				inp = inp/1000;
			}
			
			int n = A.length - 1;
			int m = A[0].length - 1;
			R = new double[n+1];
			double hasil = 0;
			for (int i = 1; i <= n; i++) {
					if ((A[i][m]>eps) || (A[i][m]<(-eps))) {
						hasil = hasil + A[i][m]*Math.pow(inp,i-1);
					}
            }
				 
                
			System.out.println("Perkiraan f(x) : " + String.format( "%.5f", hasil) ); //menuliskan hasil dengan presisi 5 angka di setelah koma
		}

    public static void main(String[] args) throws IOException
    {
        try
        {
            SPLSolver spl = new SPLSolver();
            SPLSolver interpolation = new SPLSolver();
            SPLSolver utama = new SPLSolver();
            SPLSolver implementasi = new SPLSolver();
            boolean stop;

			utama.mainmenu();

			System.out.printf("Masukkan Pilihan : ");
			Scanner pilmen = new Scanner(System.in); // Memilih menu SPL, implementasi, atau interpolasi
			int p = Integer.parseInt(pilmen.nextLine());
			//pilihan menu utama

			switch (p) {
				case 1: {
					/* Algo untuk penyelesaian SPL*/
					spl.menuinput();
					Scanner in = new Scanner(System.in);
					int c = Integer.parseInt(in.nextLine());
                    System.out.println();
                    System.out.println();

					//cara input keyboard
					if(c == 1)
					{
						spl.Read1SPL();
						spl.Solver1SPL();
						spl.AssignResult1();
					}
					//cara input file
					else
					{
						spl.Read2SPL();
						spl.Solver2SPL();
						spl.AssignResult2();
						System.out.println("Selesai");
					}
					break;
				}
				case 2: {
					//menu implementasi SPL
					//algo implementasi SPL
					implementasi.menuImplementasi();

					Scanner choiceImplement = new Scanner(System.in);
					int choice = Integer.parseInt(choiceImplement.nextLine());

					if(choice == 1)
					{
						//Algo Insinyur Teknik Sipil-Rangka Segitiga
						implementasi.ReadSPLTeknikSipil();
						implementasi.Solver1SPL();
						implementasi.Solver2SPL();
						implementasi.AssignResultTeknikSipil();
						implementasi.AssignResultTeknikSipilTxt();
					}
					else
					{
						//Algo Arus Rangkaian Listrik
						implementasi.ReadSPLArus();
						implementasi.Solver1SPL();
						implementasi.Solver2SPL();
						implementasi.AssignResultArus();
						implementasi.AssignResultArusTxt();
					}
					break;
			    }
				default: {
					/* Algo untuk interpolasi */
					interpolation.menuinterpolasi();

					Scanner interp = new Scanner(System.in);
					int x = Integer.parseInt(interp.nextLine());

					//memilih apakah mencari X, mencari konsentrasi oksigen, atau harga rumah

					switch(x) {
						case 1: {
							interpolation.xfxsolver(); //memanggil prosedur xfxsolver
							interpolation.Solver1SPL();
							interpolation.AssignResult1interp();
							System.out.println("-Cari nilai x-");
							interpolation.carix(1);					
							break;
							}
						case 2: {
							//algo mencari konsentrasi oksigen
							interpolation.konsentrasisolv1();
							interpolation.interpolsolver();
							interpolation.AssignResult1interp();
							System.out.println("-Cari nilai x-");
							interpolation.carix(2);
							interpolation.konsentrasisolv2();
							interpolation.interpolsolver();
							interpolation.AssignResult1interp();
							System.out.println("-Cari nilai x-");
							interpolation.carix(3);						
							break;
						}
						default: {
							// algo mencari harga rumah
							interpolation.hargarumahsolv();
							interpolation.Solver1SPL();
							interpolation.AssignResult1interp();
							System.out.println("-Cari nilai x-");
							interpolation.carix(4);
							break;
						}
					}
					break;
				}
		   }

        }
        catch (IOException e)
        {
            //do something useful here
        }
    }
}
