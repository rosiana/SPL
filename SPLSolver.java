// javac SPLSolver.java
// input keyboard: java SPLSolver
// input file: java SPLSolver input.txt output.txt

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SPLSolver
{
    private int i, j, k;
    private int size;
    private float mat[][], arr[];
    private float f;

    public void Solve()
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

    public void ReadInputFile(String inputFile) throws IOException
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

    public void WriteOutputFile(String outputFile) throws IOException
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

        // String line;
        // StringTokenizer st;

        System.out.printf("\nMasukkan ukuran matriks: ");
        size = in.nextInt();
        // st = new StringTokenizer(line);
        // size = Integer.parseInt(st.nextToken());
        System.out.printf("\nMasukkan elemen matriks \n");
        for (i = 1; i <= size; i++)
        {
            for (j = 1; j <= (size+1); j++)
            {
                System.out.printf(" Elemen baris ke-" + i + "kolom ke-" + j + ": ");
                mat[i][j] = in.nextFloat();
                // st = new StringTokenizer(line);
                // mat[i][j] = Float.parseFloat(st.nextToken());
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

    public static void main(String[] args) throws IOException
    {
        SPLSolver spl = new SPLSolver();

        try
        {
            if (args.length != 2)
            {
                spl.Read();
                spl.Solve();
                spl.Print();
            }
            else
            {
                spl.ReadInputFile(args[0]);
                spl.Solve();
                spl.WriteOutputFile(args[1]);
                System.out.printf("\nSelesai");
            }
        }
        catch (IOException e)
        {
            // do something useful here
        }
    }
}
