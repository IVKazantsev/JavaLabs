public class Main {
    public static void main(String[] args) {
        CinemaChain chain = new CinemaChain();
        // Пример
        chain.cinemaAdd("Люмен");
        chain.getCinemas().get(0).hallAdd(20, 5, 4);
        chain.getCinemas().get(0).hallAdd(30, 6, 5);
        chain.getCinemas().get(0).hallAdd(40, 7, 6);
        chain.getCinemas().get(0).getHalls().get(0).sessionAdd("Исчезнувшая в звездах", 95, 1285);
        chain.getCinemas().get(0).getHalls().get(0).sessionAdd("Повелитель ветра", 95, 1335);
        chain.getCinemas().get(0).getHalls().get(1).sessionAdd("Повелитель ветра", 95, 790);
        chain.getCinemas().get(0).getHalls().get(2).sessionAdd("Фантом вселенной", 95, 900);
        chain.cinemaAdd("Эпицентр");
        chain.getCinemas().get(1).hallAdd(20, 4, 5);
        chain.getCinemas().get(1).hallAdd(30, 5, 6);
        chain.getCinemas().get(1).hallAdd(40, 7, 6);
        chain.getCinemas().get(1).getHalls().get(0).sessionAdd("Повелитель ветра", 95, 1285);
        chain.getCinemas().get(1).getHalls().get(1).sessionAdd("Фантом вселенной", 95, 1335);
        chain.getCinemas().get(1).getHalls().get(0).sessionAdd("Исчезнувшая в звездах", 95, 790);
        chain.cinemaAdd("Европа");
        chain.getCinemas().get(2).hallAdd(20, 5, 4);
        chain.getCinemas().get(2).hallAdd(40, 6, 7);
        chain.getCinemas().get(2).getHalls().get(1).sessionAdd("Повелитель ветра", 95, 1285);
        chain.getCinemas().get(2).getHalls().get(0).sessionAdd("Исчезнувшая в звездах", 95, 1335);
        chain.getCinemas().get(2).getHalls().get(0).sessionAdd("Фантом вселенной", 95, 790);
        chain.getCinemas().get(2).getHalls().get(1).sessionAdd("Повелитель ветра", 95, 900);
        UI ui = new UI(chain);
        ui.launch();
    }
}