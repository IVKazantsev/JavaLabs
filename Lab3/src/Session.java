// Класс сеанса
public class Session {
    private final String mFilm;
    private final int mDuration;
    private final int mStartOfSession;
    private int[][] mSeats;
    public String getFilm() { return mFilm; }
    public int getDuration() {
        return mDuration;
    }
    public int getStartOfSession() {
        return mStartOfSession;
    }
    public int[][] getSeats() {
        return mSeats;
    }
    public void setSeats(int[][] seats) {
        mSeats = seats;
    }
    public Session(String film, int duration, int startOfSession, int[][] seats) {
        mFilm = film;
        mDuration = duration;
        mStartOfSession = startOfSession;
        mSeats = seats;
    }

    // Вывод сеанса
    public void sessionPrint() {
        System.out.print(" ");
        border();
        for (int i = 0; i < mSeats.length; i++) {
            System.out.print(i + 1); // Ряд
            for (int j = 0; j < mSeats[0].length; j++)
                System.out.print("|  " + (mSeats[i][j] == 1 ? "o" : "X") + "  ");
            System.out.print("|\n ");
            border();
        }
        for (int col = 0; col < mSeats[0].length; col++)
            System.out.print("    " + (col + 1) + " "); // Место
        System.out.println();
    }

    public void border() { // Функция границы для вывода сеанса
        for (int i = 0; i < mSeats[0].length; i++)
            System.out.print("+-----");
        System.out.print("+\n");
    }
}
