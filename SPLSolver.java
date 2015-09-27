import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

class SPLSolver
{
    double[][] A; //matriks
    int n; //baris
    int m; //kolom

    BufferedReader in;
    PrintWriter out;

    void Read1SPL()
    {
        Scanner in = new Scanner(System.in);

        System.out.printf("Masukkan jumlah baris: ");
        n = Integer.parseInt(in.nextLine());
        System.out.printf("Masukkan jumlah kolom: ");
        m = Integer.parseInt(in.nextLine());

        A = new double[n+1][m+1];

        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                System.out.printf("Elemen matriks baris ke-" + i + " kolom ke-" + j + ": ");
                A[i][j] = Double.parseDouble(in.nextLine());
            }
        }
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
        PrintMatrix2(out, A);
    }

    void PrintMatrix1(double[][] A)
    {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= m; j++)
            {
                System.out.printf(A[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    void PrintMatrix2(PrintWriter out, double[][] A)
    {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= m; j++)
            {
                out.print(A[i][j] + "  ");
            }
            out.println();
        }
        out.println();
        out.println();
    }

    void Solver1SPL()
    {
        int i = 1;
        int j = 1;
        int k;
        while(i <= n && j <= m)
        {
            //cari angka bukan nol di kolom j atau di bawah baris i
            k = i;
            while (k<=n && A[k][j] == 0)
            {
                k++;
            }
            //jika ada di baris k
            if(k <= n)
            {
                //jika k bukan i, swap baris i dengan baris k
                if(k != i)
                {
                    Swap(A, i, k, j);
                    PrintMatrix1(A);
                }

                //jika A[i][j] bukan 1, bagi baris i dengan A[i][j]
                if(A[i][j] != 1)
                {
                    Divide(A, i, j);
                    PrintMatrix1(A);
                }
                //hapus semua angka bukan nol dari kolom j dengan mengurangi masing-masing baris
                //selain i dengan sejumlah kelipatan i
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
        while(i <= n && j <= m)
        {
            //cari angka bukan nol di kolom j atau di bawah baris i
            k = i;
            while (k<=n && A[k][j] == 0)
            {
                k++;
            }
            //jika ada di baris k
            if(k <= n)
            {
                //jika k bukan i, swap baris i dengan baris k
                if(k != i)
                {
                    Swap(A, i, k, j);
                    PrintMatrix2(out, A);
                }

                //jika A[i][j] bukan 1, bagi baris i dengan A[i][j]
                if(A[i][j] != 1)
                {
                    Divide(A, i, j);
                    PrintMatrix2(out, A);
                }
                //hapus semua angka bukan nol dari kolom j dengan mengurangi masing-masing baris
                //selain i dengan sejumlah kelipatan i
                Eliminate(A, i, j);
                PrintMatrix2(out, A);
                i++;
            }
            j++;
        }
        out.close();
    }

    void Swap(double[][] A, int i, int k, int j)
    {
        int m = A[0].length - 1;
        double temp;
        for(int q = j; q <= m; q++)
        {
            temp = A[i][q];
            A[i][q] = A[k][q];
            A[k][q] = temp;
        }
    }

    void Divide(double[][] A, int i, int j)
    {
        int m = A[0].length - 1;
        for(int q = j+1; q <= m; q++)
        {
            A[i][q] /= A[i][j];
        }
        A[i][j] = 1;
    }

   void Eliminate(double[][] A, int i, int j)
   {
        int n = A.length - 1;
        int m = A[0].length - 1;
        for(int p = 1; p <= n; p++)
        {
            if(p!=i && A[p][j] != 0 )
            {
                for(int q = j+1; q <= m; q++)
                {
                    A[p][q] -= A[p][j]*A[i][q];
                }
                A[p][j] = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            SPLSolver spl = new SPLSolver();

            System.out.println("Metode input");
            System.out.println("1. Keyboard");
            System.out.println("2. File");
            System.out.printf("Pilihan: ");

            Scanner in = new Scanner(System.in);
            int c = Integer.parseInt(in.nextLine());

            //cara input keyboard
            if(c == 1)
            {
                spl.Read1SPL();
                spl.Solver1SPL();
            }
            //cara input file
            else
            {
                spl.Read2SPL();
                spl.Solver2SPL();
                System.out.println("Selesai");
            }
        }
        catch (IOException e)
        {
            //do something useful here
        }
    }
}
