
import java.util.ArrayList;
import java.util.List;
public class CinemaChain {
    private final List<Cinema> mCinemas = new ArrayList<>();
    public List<Cinema> getCinemas() {
        return mCinemas;
    }
    public void cinemaAdd(String name) {
        Cinema cinema = new Cinema(name);
        mCinemas.add(cinema);
    }
}
