import java.util.Arrays;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int[] secondArray = new int[] {1, 4, 8};
        int[] firstArray = new int[] {2, 3, 4, 5, 10, 12};
        int[] arrayForSum = new int[] {-2, -3, -4, -5, -2351, -12};
        int[][] twoDimArray = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        nonRepeatingSubstring("aagbacrtartyu");
        combiningArrays(firstArray, secondArray);
        maxSum(arrayForSum);
        arrayRotationСlockwise(twoDimArray);
        pairSearch(firstArray, 9);
        arraySum(twoDimArray);
        rowMax(twoDimArray);
        arrayRotationCounterClockwwise(twoDimArray);
    }
    // 1 задача
    static void nonRepeatingSubstring(String string) {
        char[] stringArray = string.toCharArray();
        boolean[] dictionaryOfCaught = new boolean[128];
        int tempStartInd = 0;
        int tempFinishInd = 0;
        int startInd = 0;
        int finishInd = 0;
        // Проходим по всей строке
        for (int i = 0; i < stringArray.length; i++) {
            // Если встречаем символ, который не встречали в этой подстроке,
            // увеличиваем текущую подстроку и отмечаем пойманный символ
            if(!dictionaryOfCaught[(int) stringArray[i]]) {
                dictionaryOfCaught[(int) stringArray[i]] = true;
                tempFinishInd = i;
            }
            // Если встретили повторившийся символ, то обнуляем счетчик в словаре,
            // сравниваем, не является ли новая подстрока наибольшей,
            // возвращаемся в начало нашей подстроки
            // и начинаем со следующего символа, чтобы не потерять подстроку
            else {
                for (int j = tempStartInd; j < i; j++)
                    dictionaryOfCaught[(int) stringArray[j]] = false;
                if((tempFinishInd - tempStartInd) > (finishInd - startInd)) {
                    startInd = tempStartInd;
                    finishInd = tempFinishInd;
                }
                tempStartInd++;
                tempFinishInd = tempStartInd;
                i = tempStartInd;
                dictionaryOfCaught[(int) stringArray[i]] = true;
            }
        }
        // Доп проверка, если наибольшая подстрока была в конце
        if((tempFinishInd - tempStartInd) > (finishInd - startInd)) {
            startInd = tempStartInd;
            finishInd = tempFinishInd;
        }
        System.out.println("Наибольшая подстрока в строке " + string + ":" + string.substring(startInd, finishInd + 1));
    }
    // 2 задача
    static void combiningArrays(int[] firstArray, int[]secondArray) {
        int length = firstArray.length + secondArray.length;
        int[] sortedArray = new int[length];
        int iterator = 0;
        // Нам необходимо разделение на бОльший и меньший массив, чтобы после сортировки
        // дополнить оставшееся пространство бОльшим массивом
        int[] lessArray = (firstArray.length < secondArray.length) ? firstArray : secondArray;
        int[] largerArray = (firstArray.length > secondArray.length) ? firstArray : secondArray;

        for(int i = 0; i < lessArray.length; i++)
            for (int j = 0; j < largerArray.length; j++) {
                if (i >= lessArray.length)
                    break;
                if (lessArray[i] > largerArray[j])
                    sortedArray[iterator++] = largerArray[j];
                else {
                    sortedArray[iterator++] = lessArray[i++];
                    j--;
                }
            }

        for (int i = largerArray.length - sortedArray.length + iterator; i < largerArray.length; i++)
            sortedArray[iterator++] = largerArray[i];
        System.out.println(Arrays.toString(sortedArray));
    }
    // 3 задача
    static void maxSum(int[] array) {
        int sum = 0;
        int tempSum = 0;
        int minNegativeElement = -1000000;
        boolean allObjectsNegative = true;

        for (int i = 0; i < array.length; i++) {
            // Если встретили неотрицательый элемент, то добавляем его к сумме
            // и отмечаем, что в массиве есть неотрицательные числа
            if(array[i] >= 0) {
                tempSum += array[i];
                allObjectsNegative = false;
            }
            // Если встретили отрицательное число, то фиксируем
            // текущую сумму, если она больше нашей, и если мы не уходим в отрицательные
            // значения, то прибавляем число к сумме, иначе "создаем" новую сумму
            // и фиксируем минимальное по модулю отрицательное число, если вдруг все элементы
            // окажутся отрицательными
            else {
                if(allObjectsNegative) {
                    if(minNegativeElement < array[i])
                        minNegativeElement = array[i];
                    // Если все встретившиеся элементы были отрицательные, то нет смысла
                    // проверять следующие условия
                    continue;
                }
                if(sum < tempSum)
                    sum = tempSum;
                if(tempSum + array[i] >= 0)
                    tempSum += array[i];
                else
                    tempSum = 0;
            }
        }

        sum = Math.max(sum, tempSum);
        if(allObjectsNegative)
            sum = minNegativeElement;
        System.out.println(sum);
    }
    // 4 задача
    static void arrayRotationСlockwise(int[][] array) {
        int rowCount = array.length;
        int columnCount = array[0].length;
        // Меняем строки и столбцы местами и устанавливаем элементы
        // в обратном порядке, как бы имитируя поворот на 90 градусов
        int[][] rotatedArray = new int[columnCount][rowCount];
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                rotatedArray[j][rowCount - 1 -i] = array[i][j];

        System.out.println(Arrays.deepToString(rotatedArray));
    }
    // 5 задача
    static void pairSearch(int[] array, int target) {
        boolean searched = false;
        // Проходим по массиву двойным циклом
        // вложенный цикл должен начинаться от следующего по номеру
        for (int i = 0; i < array.length; i++)
            for (int j = i + 1; j < array.length; j++)
                if(array[i] + array[j] == target) {
                    System.out.println(array[i] + " " + array[j]);
                    searched = true;
                }
        if(!searched)
            System.out.println("null");
    }
    // 6 задача
    static void arraySum(int[][] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                sum += array[i][j];
        System.out.println(sum);
    }
    // 7 задача
    static void rowMax(int[][] array) {
        int rowCount = array.length;
        int columnCount = array[0].length;
        int[] maxArray = new int[rowCount];
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                if(maxArray[i] < array[i][j])
                    maxArray[i] = array[i][j];

        System.out.println(Arrays.toString(maxArray));
    }
    // 8 задача
    static void arrayRotationCounterClockwwise(int[][] array) {
        int rowCount = array.length;
        int columnCount = array[0].length;
        int[][] rotatedArray = new int[columnCount][rowCount];
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                rotatedArray[columnCount - 1 -j][i] = array[i][j];

        System.out.println(Arrays.deepToString(rotatedArray));
    }
}