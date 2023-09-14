import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Сиракузская последовательность:\n");
        System.out.print("Введите число для сиракузской последовательности: ");
        int number = scanner.nextInt();
        syracuse(number);

        System.out.print("Сумма ряда:\n");
        System.out.print("Введите количество чисел в ряде: ");
        int amountOfNumber = scanner.nextInt();
        int[] numberSeriesMembers = new int[amountOfNumber];
        for (int i = 0; i < amountOfNumber; i++) {
            System.out.print("Введите " + (i + 1) + " число ряда: ");
            int numberSeriesMember = scanner.nextInt();
            numberSeriesMembers[i] = numberSeriesMember;
        }
        sumOfSeries(numberSeriesMembers);

        System.out.print("Ищем клад:\n");
        treasureHunt();

        System.out.print("Логистический максимин:\n");
        logistics();

        System.out.print("\nДважды четное число:\n");
        System.out.print("Введите число, которое хотите проверить на дважды четность: ");
        Number input = new Number(scanner.nextInt());
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
        for (int numberSeriesMember : numberSeriesMembers) {
            if (numberSeriesMember % 2 == 1)
                sum += numberSeriesMember;
            else
                sum -= numberSeriesMember;
        }
        System.out.print("Сумма ряда с чередованием знаков: " + sum + "\n");
    }

    public static void treasureHunt() {
        Scanner treasureScan = new Scanner(System.in);
        System.out.print("Введите координату клада по x: ");
        int x = treasureScan.nextInt();
        System.out.print("Введите координату клада по y: ");
        int y = treasureScan.nextInt();
        int tempCounter = 0;
        int counter = 0;
        int tempX = 0;
        int tempY = 0;
        boolean stopFlag = false;
        while(!stopFlag) {
            treasureScan.nextLine(); // для того, чтобы nextLine не считывал Enter
            System.out.print("Введите направление движения или 'стоп': ");
            String string = treasureScan.nextLine(); // Направление движения или слово "Стоп"
            if(string.equals("стоп"))
                break;
            System.out.print("Введите длину пути в этом направлении: ");
            int step = treasureScan.nextInt();
            switch (string) {
                case "север" -> tempY += step;
                case "юг" -> tempY -= step;
                case "запад" -> tempX -= step;
                case "восток" -> tempX += step;
                default -> {
                    System.out.print("Неверное направление");
                    stopFlag = true;
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
        System.out.print("Введите количество дорог: ");
        int roadCount = logisticScan.nextInt();
        int maxMin = 0; // Максимальная высота среди минимума туннелей
        int maxRoad = 0; // Дорога, по которой может проехать машина максимальной высоты
        for(int i = 0; i < roadCount; i++) {
            int tempMin = 1000000;
            System.out.print("Введите количество туннелей: ");
            int tunnelCount = logisticScan.nextInt();
            for (int j = 0; j < tunnelCount; j++) {
                System.out.print("Введите высоту туннеля: ");
                int tunnelHeight = logisticScan.nextInt();
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