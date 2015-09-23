import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SPLSolver
{
    private int i, j, k;
    private int size;
    private float mat[][], arr[];
    private float f;

    public void Solver()
    {
        for (j = 1; j <= size; j++)
        {
            for (i = 1; i <= size; i++)
            {
                if (i != j)
                {
                    f = mat[i][j] / mat[j][j];
                    for (k = 1; k <= size+1; k++)
                    {
                        mat[i][k] = mat[i][k] - f*mat[j][k];
                    }
                }
            }
        }
    }

    public void ReadInputFile(String inputFile)
    {
        String line;
        StringTokenizer st;

        BufferedReader in = new BufferedReader(new FileReader(inputFile));

        line = in.readLine();
        st = new StringTokenizer(line);
        size = Integer.parseInt(st.nextToken());

        for(i = 1; i <= size; i++)
        {
            line = in.readLine();
            st = new StringTokenizer(line);
            for(j = 1; j <= (size+1); j++)
            {
                mat[i][j] = Float.parseFloat(st.nextToken());
            }
        }

        in.close();
    }

    public void WriteOutputFile(String outputFile)
    {
        PrintWriter out = new PrintWriter(new FileWriter(outputFile));

        for (i = 1; i <= size; i++)
        {
            arr[i] = mat[i][size+1] / mat[i][i];
            out.printf(" x%d = %f\n",i,arr[i]);
        }

        out.close();
    }

    public void Read()
    {
        Scanner in = new Scanner(System.in);

        String line;
        StringTokenizer st;

        System.out.printf("\nMasukkan ukuran matriks: ");
        line = in.nextLine();
        st = new StringTokenizer(line);
        size = Integer.parseInt(st.nextToken());
        System.out.printf("\nMasukkan elemen matriks \n");
        for (i = 1; i <= size; i++)
        {
            for (j = 1; j <= (size+1); j++)
            {
                System.out.printf(" Elemen baris ke-" + i + "kolom ke-" + j + ": ");
                line = in.nextLine();
                st = new StringTokenizer(line);
                mat[i][j] = Float.parseFloat(st.nextToken());
            }
        }
    }

    public void Print()
    {
        for (i = 1; i <= size; i++)
        {
            arr[i] = mat[i][size+1] / mat[i][i];
            System.out.printf(" x%d = %f\n",i,arr[i]);
        }
    }

    public static void main(String[] args)
    {
        int c;
        String inputFile;
        String outputFile;

        Scanner in = new Scanner(System.in);

        System.out.printf("\nSPL Solver\n");
        System.out.printf("\nMetode input");
        System.out.printf("\n1. File");
        System.out.printf("\n2. Keyboard");
        System.out.printf("\nMasukkan metode input: ");
        c = Integer.parseInt(in.nextLine());

        if (c == 1)
        {
            System.out.printf("\nMasukkan nama file input: ");
            inputFile = in.nextLine();
            ReadInputFile(inputFile);
            Solver();
            System.out.printf("\nMasukkan nama file output: ");
            outputFile = in.nextLine();
            WriteOutputFile(outputFile);
            System.out.printf("\nSelesai");
        }
        else if (c == 2)
        {
            Read();
            Solver();
            Print();
        }
        else
        {
            System.out.printf("\nMasukan salah");
        }
    }
}
