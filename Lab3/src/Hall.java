import java.util.ArrayList;
import java.util.List;

//Класс зала
public class Hall {
    private final List<Session> mSessions = new ArrayList<>();
    private final int mSeatCount;
    private int[][] mSeats;
    public List<Session> getSessions() { return mSessions; }
    public int[][] getSeats() { return mSeats; }
    public int getSeatCount() { return mSeatCount; }

    // Конструктор, в котором инициализируем матрицу нулями, если кресла нет, и единицами, если кресло есть
    public Hall(int seatCount, int rowCount, int columnCount) {
        mSeatCount = seatCount;
        mSeats = new int[rowCount][columnCount];
        int iterator = 0;
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++) {
                mSeats[i][j] = iterator < seatCount ? 1 : 0;
                iterator++;
            }
    }

    // Метод конфигурации кресел, в котором меняем матрицу кресел в зале
    // и в каждом сеансе, привязанном к этому залу
    public void seatConfig(int[][] seatsConfig) {
        mSeats = seatsConfig;
        for(Session session : mSessions)
            session.setSeats(seatsConfig);
    }

    // Метод добавления нового сеанса к списку сеансов
    public void sessionAdd(String film, int duration, int startOfSession) {
        Session session = new Session(film, duration, startOfSession, mSeats);
        mSessions.add(session);
    }

    // Отрисовка текущей конфигурации кресел
    public void configPrint() {
        System.out.print(" ");
        border();
        for (int i = 0; i < mSeats.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < mSeats[0].length; j++) {
                System.out.print("|  " + (mSeats[i][j] == 1 ? "o" : "X") + "  ");
            }
            System.out.print("|\n ");
            border();
        }
        for (int col = 0; col < mSeats[0].length; col++) {
            System.out.print("    " + (col + 1) + " ");
        }
        System.out.println();
    }

    public void border() { // Функция границы для вывода сеанса
        for (int i = 0; i < mSeats[0].length; i++)
            System.out.print("+-----");
        System.out.print("+\n");
    }
}
