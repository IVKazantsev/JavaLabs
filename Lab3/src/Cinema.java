import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private final List<Hall> mHalls = new ArrayList<>();
    private final String mName;
    public String getName() { return mName; }
    public Cinema(String name) { mName = name; }
    public List<Hall> getHalls(){
        return mHalls;
    }
    public void hallAdd(int seatCount, int rowCount, int columnCount) { // Метод добавления зала к списку
        Hall hall = new Hall(seatCount, rowCount, columnCount);
        mHalls.add(hall);
    }

}
