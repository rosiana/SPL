#include <stdio.h>
int main()
{
    int i, j, k;
    int size;
    float mat[100][100], arr[50];
    float f;
    printf("\nMasukkan ukuran matriks: ");
    scanf("%d",&size);
    printf("\nMasukkan elemen matriks \n");
    for (i = 1; i <= size; i++)
    {
        for (j = 1; j <= (size+1); j++)
        {
            printf(" Elemen baris ke-%d kolom ke-%d: ", i,j);
            scanf("%f",&mat[i][j]);
        }
    }
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
    printf("\nHasil variabel matriks\n");
    for (i = 1; i <= size; i++)
    {
        arr[i] = mat[i][size+1] / mat[i][i];
        printf(" x%d = %f\n",i,arr[i]);
    }
    return(0);
}
