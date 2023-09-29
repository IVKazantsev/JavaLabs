import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число, которое хотите проверить: ");

        while (true) {
            try {
                int number = scanner.nextInt();
                Armstrong(number);
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }
        System.out.println("Введите кол-во строк и столбцов в массиве:");
        int rowCount = 0;
        int columnCount = 0;
        while (true) {
            try {
                rowCount = scanner.nextInt();
                columnCount = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не то что нужно!\n");
            }
        }

        int[][] twoDimArray = new int[rowCount][columnCount];
        System.out.println("Введите элементы массива построчно:");
        while (true) {
            try {
                for (int i = 0; i < rowCount; i++)
                    for (int j = 0; j < columnCount; j++) {
                        int tempValue = scanner.nextInt();
                        twoDimArray[i][j] = tempValue;
                    }
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не то что нужно!\n");
            }
        }
        sumOfColumn(twoDimArray);
    }
    static void Armstrong(int number) {
        int sum = 0;
        char[] charArray = Integer.toString(number).toCharArray();
        int digit = charArray.length;

        for (char figure : charArray)
            sum += (int) Math.pow(Character.getNumericValue(figure), digit);
        if(number == sum)
            System.out.println("Число является числом Армстронга");
        else
            System.out.println("Число не является числом Армстронга");
    }
    static void sumOfColumn(int[][] twoDimArray) {
        int rowCount = twoDimArray.length;
        int columnCount = twoDimArray[0].length;
        int sum = 0;
        System.out.println("Сумма для каждого столбца: ");
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++)
                sum += twoDimArray[j][i];
            System.out.print(sum + " ");
            sum = 0;
        }
    }
}