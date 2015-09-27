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
	
	void menuSPL() {
		System.out.println();
		System.out.println("Metode input");
        	System.out.println("1. Keyboard");
		System.out.println("2. File");
        	System.out.printf("   Pilihan: ");
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
	
	// mencari x //
	void tampilxfx(double[] datax, double[] datafx, int dat) {
		int i;
		System.out.println("| x ");
		for (i=0; i<dat; i++) {
			System.out.printf(" +datax[i]+ ");
			if (i<=dat) {
				System.out.println("|");
			}
		} // menuliskan data x;
		for (i=0; i<dat; i++) {
			System.out.printf(" +datafx[i]+ ");
			if (i<=dat) {
				System.out.println("|");
			}
		} // menuliskan data F(x);
		
		System.out.println("Cari nilai x ?");
		System.out.println("Input : ");
		
	}
	
	/* void inisiasitabelx() {
		//inisasi dilakukan bila user menggunakan data yang sudah ada/ tidak ada data input user
		double[] datax;
	} */
	
	void xfxsolver() {
		int i;
		int NDatax;
		double[] dataxawal= {0.1, 0.3, 0.5, 0.7, 0.9, 1.1, 1.3};
		double[] dataFXawal = {0.003, 0.067, 0.148, 0.248, 0.370, 0.518, 0.697};
		double[] dataxbaru, dataFXbaru;
	
			System.out.println("-----Mencari Fx-----");
			System.out.println("1. Tampilkan data x dan Fx");
			System.out.println("2. Input data x dan Fx baru");
			System.out.printf("  Masukkan Pilihan : ");
			
			Scanner pilih = new Scanner(System.in);
			int pil = Integer.parseInt(pilih.nextLine());
			
			if (pil==1) {
				NDatax = 7; //nilai data x dan fx sama
				tampilxfx(dataxawal, dataFXawal, NDatax);
				Scanner input = new Scanner(System.in);
				int inp = Integer.parseInt(input.nextLine());
			} /*else
			{
				System.out.println("Berapa data yang ingin diinput?");
				System.out.printf("Masukkan : ");
			}*/ //masih opsional dan terllau ribet
			
				
	}
	
	// akhir mencari x
	

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
					spl.menuSPL();
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
					break;
				}
				case 2: {
					//menu implementasi SPL
					//algo implementasi SPL
					break;
			    }
				default: {
					/* Algo untuk interpolasi */
					interpolation.menuinterpolasi(); 
					
					Scanner interp = new Scanner(System.in);
					int x = Integer.parseInt(interp.nextLine());

					//memilih pakah mencari X, mencari konsentrasi oksigen, atau harga rumah
					
					switch(x) {
						case 1: {
							// algo mencari x
							stop = false;
							while(!stop) {
								interpolation.xfxsolver(); //memanggil prosedur xfxsolver
								
								/*System.out.println("Continue(Y/N)");
								
        						Scanner s= new Scanner(System.in);
								char x = s.next().charAt(0);
								
								if ((x.equals("n")) || (x.equals("N"))) {
									stop = true;
								}
        						
							} //akan mengulang sampai user input bukan Y */
							
							break;
							}
						}
						case 2: {
							//algo mencari konsentrasi oksigen
							break;
						}	
						default: {
							// algo mencari harga rumah
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
