import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Сиракузская последовательность:\n");
        while (true) {
            try {
                System.out.print("Введите число для сиракузской последовательности: ");
                int number = scanner.nextInt();
                syracuse(number);
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }

        System.out.print("Сумма ряда:\n");
        int amountOfNumber = 0;
        while (true) {
            try {
                System.out.print("Введите количество чисел в ряде: ");
                amountOfNumber = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }
        int[] numberSeriesMembers = new int[amountOfNumber];
        for (int i = 0; i < amountOfNumber; i++) {
            int numberSeriesMember;
            while (true) {
                try {
                    System.out.print("Введите " + (i + 1) + " число ряда: ");
                    numberSeriesMember = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.print("Вы ввели не число!\n");
                }
            }
            numberSeriesMembers[i] = numberSeriesMember;
        }
        sumOfSeries(numberSeriesMembers);

        System.out.print("Ищем клад:\n");
        treasureHunt();

        System.out.print("Логистический максимин:\n");
        logistics();

        System.out.print("\nДважды четное число:\n");

        int inputNumber;
        while (true) {
            try {
                System.out.print("Введите число, которое хотите проверить на дважды четность: ");
                inputNumber = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }

        Number input = new Number(inputNumber);
        input.twiceEven();
    }

    public static void syracuse(int number) {
        int counter = 0;
        while(number != 1) {
            if (number % 2 == 0)
                number /= 2;
            else
                number = 3 * number + 1;
            counter++;
        }
        System.out.print("До 1 нужно " + counter + " шагов\n");
    }

    public static void sumOfSeries(int[] numberSeriesMembers) {
        int sum = 0;
        for (int i = 0; i < numberSeriesMembers.length; i++) {
            if (i % 2 == 0)
                sum += numberSeriesMembers[i];
            else
                sum -= numberSeriesMembers[i];
        }
        System.out.print("Сумма ряда с чередованием знаков: " + sum + "\n");
    }

    public static void treasureHunt() {
        Scanner treasureScan = new Scanner(System.in);
        int x;
        int y;
        while (true) {
            try {
                System.out.print("Введите координату клада по x: ");
                x = treasureScan.nextInt();
                break;
            } catch (Exception e) {
                treasureScan.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }
        while (true) {
            try {
                System.out.print("Введите координату клада по y: ");
                y = treasureScan.nextInt();
                break;
            } catch (Exception e) {
                treasureScan.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }
        int tempCounter = 0;
        int counter = 0;
        int tempX = 0;
        int tempY = 0;
        while(true) {
            treasureScan.nextLine(); // для того, чтобы nextLine не считывал Enter
            String string;
            while (true) {
                try {
                    System.out.print("Введите направление движения или 'стоп': ");
                    string = treasureScan.nextLine(); // Направление движения или слово "Стоп"
                    break;
                } catch (Exception e) {
                    treasureScan.nextLine();
                    System.out.print("Вы ввели не строку!\n");
                }
            }
            if(string.equals("стоп"))
                break;
            int step;
            while (true) {
                try {
                    System.out.print("Введите длину пути в этом направлении: ");
                    step = treasureScan.nextInt();
                    break;
                } catch (Exception e) {
                    treasureScan.nextLine();
                    System.out.print("Вы ввели не число!\n");
                }
            }

            switch (string) {
                case "север" -> tempY += step;
                case "юг" -> tempY -= step;
                case "запад" -> tempX -= step;
                case "восток" -> tempX += step;
                default -> {
                    System.out.print("Неверное направление\n");
                    continue;
                }
            }
            tempCounter++;
            if((tempX == x) && (tempY == y) && (counter == 0))
                counter = tempCounter;
        }
        System.out.print("Чтобы дойти до клада, необходимо минимум " + counter + " указаний\n");
    }

    public static void logistics() {
        Scanner logisticScan = new Scanner(System.in);
        int roadCount;
        while (true) {
            try {
                System.out.print("Введите количество дорог: ");
                roadCount = logisticScan.nextInt();
                break;
            } catch (Exception e) {
                logisticScan.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
        }

        int maxMin = 0; // Максимальная высота среди минимума туннелей
        int maxRoad = 0; // Дорога, по которой может проехать машина максимальной высоты
        for(int i = 0; i < roadCount; i++) {
            int tempMin = 1000000;
            int tunnelCount;
            while (true) {
                try {
                    System.out.print("Введите количество туннелей: ");
                    tunnelCount = logisticScan.nextInt();
                    break;
                } catch (Exception e) {
                    logisticScan.nextLine();
                    System.out.print("Вы ввели не число!\n");
                }
            }
            for (int j = 0; j < tunnelCount; j++) {
                int tunnelHeight;
                while (true) {
                    try {
                        System.out.print("Введите высоту туннеля: ");
                        tunnelHeight = logisticScan.nextInt();
                        break;
                    } catch (Exception e) {
                        logisticScan.nextLine();
                        System.out.print("Вы ввели не число!\n");
                    }
                }

                if(tempMin > tunnelHeight)
                    tempMin = tunnelHeight;
            }
            if(maxMin < tempMin) {
                maxMin = tempMin;
                maxRoad = i + 1;
            }
        }

        System.out.print("Дорога " + maxRoad + "\nМинимальная высота машины: " + maxMin);
    }
}

class Number {
    private int value;
    public Number(int Value) {
        value = Value;
    }
    public void twiceEven() {
        if(Integer.toString(value).length() != 3) {
            System.out.print("Дано не трехзначное число");
            return;
        }

        int digit = 1;
        int sum = 0;
        int product = 1;
        for (int i = 0; i < 3; i++) {
            sum += value / digit % 10;
            product *= value / digit % 10;
            digit *= 10;
        }
        if((sum % 2 == 0) && (product % 2 == 0))
            System.out.print("Число дважды четное");
        else
            System.out.print("Число не является дважды четным");
    }
}