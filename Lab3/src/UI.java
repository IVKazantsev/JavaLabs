import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private final CinemaChain mCinemaChain;
    public UI(CinemaChain cinemaChain) {
        mCinemaChain = cinemaChain;
    }
    public void launch() {
        System.out.println("Билетная система:");
        Scanner scanner = new Scanner(System.in);
        while(true) { // Выбор роли (user или admin, пароль: admin) или выход из программы
            System.out.println("Введите свою роль (admin/user) или exit, если хотите выйти:");
            String role = scanner.nextLine();

            if (role.equals("exit"))
                break;
            else if (role.equals("admin")) {
                System.out.println("Введите пароль:");
                String pass = scanner.nextLine();
                if(pass.equals("admin")) {
                    adminInterface();
                }
                else {
                    System.out.println("Неверный пароль");
                    continue;
                }
                break;
            }
            else if (role.equals("user")) {
                userInterface();
                break;
            }
            else
                System.out.println("Вы неверно ввели роль!");
        }
    }

    private void adminInterface() { // интерфейс админа с соответсвующими возможностями
        Scanner scanner = new Scanner(System.in);
        while(true) { // Предотвращение ошибок через while(true) { try catch }
            int action = -1;
            boolean isLoggedOut = false;
            try { // Выбор нужной операции
                System.out.println("Введите нужную вам операцию:");
                System.out.println("1 - Добавить кинотеатр");
                System.out.println("2 - Добавить зал");
                System.out.println("3 - Задать конфигурацию кресел");
                System.out.println("4 - Создать сеанс фильма");
                System.out.println();
                System.out.println("0 - Выйти из аккаунта");
                action = scanner.nextInt();
            } catch (Exception e) { // Обработка неверного ввода
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
            int cinema, hall, seatCount, hallsCount, rowCount, columnCount; // Вспомогательные переменные
            String cinemaName;
            int cinemasCount = mCinemaChain.getCinemas().size();
            switch (action) {
                case 1 -> { // Добавление кинотеатра
                    System.out.println("Выберите название нового кинотеатра:");
                    boolean isCreated = false;
                    scanner.nextLine();
                    cinemaName = scanner.nextLine();
                    // Проверяем, нет ли такого кинотеатра
                    for (Cinema cinema1 : mCinemaChain.getCinemas()) {
                        if (cinema1.getName().equals(cinemaName)) {
                            isCreated = true;
                            break;
                        }
                    }
                    if (isCreated) { // Обработка повторяющихся кинотеатров
                        System.out.println("Кинотеатр с таким названием уже существует!");
                        break;
                    }
                    // Сюда дойдём только если ошибок нет
                    mCinemaChain.cinemaAdd(cinemaName);
                    System.out.println("Вы успешно добавили кинотеатр!");
                }
                case 2 -> { // Добавление зала
                    if (cinemasCount == 0) { // Обработка случая, когда в сети еще нет кинотеатров
                        System.out.println("У вас нет кинотеатров!");
                        break;
                    }
                    cinema = cinemaChoose(scanner, cinemasCount); // Функция выбора нужного кинотеатра
                    while (true) { // Предотвращение ошибок через while(true) { try catch }
                        System.out.println("Введите кол-во кресел в зале:");
                        try {
                            seatCount = scanner.nextInt();
                        } catch (Exception e) { // Обработка неверного ввода
                            scanner.nextLine();
                            System.out.print("Вы ввели не число!\n");
                            continue;
                        }
                        if (seatCount <= 0) // Обработка ввода отрицательного значения
                            System.out.println("Кол-во кресел должно быть положительным!");
                        else { // Если
                            while (true) { // Предотвращение ошибок через while(true) { try catch }
                                System.out.println("Введите кол-во рядов и кол-во мест в каждом ряду");
                                try {
                                    rowCount = scanner.nextInt();
                                    columnCount = scanner.nextInt();
                                } catch (Exception e) { // Обработка неверного ввода
                                    scanner.nextLine();
                                    System.out.println("Вы ввели не число!\n");
                                    continue;
                                } // Обработке несоответствия кол-ва мест и кол-ва рядов и мест
                                if (rowCount * columnCount < seatCount || rowCount <= 0 || columnCount <= 0)
                                    System.out.println("Кол-во рядов или мест введено неверно!");
                                else break;
                            }
                            // Сюда дойдем только если ошибок нет
                            mCinemaChain.getCinemas().get(cinema).hallAdd(seatCount, rowCount, columnCount);
                            System.out.println("Вы успешно добавили кинозал!");
                            break;
                        }
                    }
                }
                case 3 -> { // Задание конфигурации кресел
                    if (cinemasCount == 0) { // Обработка случая, когда в сети еще нет кинотеатров
                        System.out.println("У вас нет кинотеатров!");
                        break;
                    }
                    cinema = cinemaChoose(scanner, cinemasCount); // Функция выбора нужного кинотеатра
                    hallsCount = mCinemaChain.getCinemas().get(cinema).getHalls().size();
                    if (hallsCount == 0) { // Обработка случая, когда в кинотеатре еще нет залов
                        System.out.println("У вас нет кинозалов в этом кинотеатре!");
                        break;
                    }
                    hall = hallChoose(scanner, hallsCount); // Функция выбора нужного зала
                    Hall hall1 = mCinemaChain.getCinemas().get(cinema).getHalls().get(hall);
                    rowCount = hall1.getSeats().length;
                    columnCount = hall1.getSeats()[0].length;
                    System.out.println("Выбранный зал:");
                    hall1.configPrint(); // Печатаем зал, чтобы видеть текущую конфигурацию
                    // Создаем вспомогательную матрицу для нового конфига
                    int[][] seatsConfig = new int[rowCount][columnCount];
                    int seatsCount = 0;
                    while (true) { // Предотвращение ошибок через while(true) { try catch }
                        System.out.println("Выставите ваши " + hall1.getSeatCount()
                                + " кресел в формате матрицы");
                        System.out.println("где 1 - есть кресто, 0 - кресла нет:");
                        try {
                            for (int i = 0; i < rowCount; i++)
                                for (int j = 0; j < columnCount; j++)
                                    seatsConfig[i][j] = scanner.nextInt();
                            scanner.nextLine();
                        } catch (Exception e) { // Обработка неверного ввода
                            scanner.nextLine();
                            System.out.print("Вы ввели не число!\n");
                            continue;
                        }
                        for (int i = 0; i < rowCount; i++)
                            for (int j = 0; j < columnCount; j++)
                                seatsCount = seatsConfig[i][j] == 1 ? seatsCount + 1 : seatsCount;
                        // Кол-во кресел в старой и новой конфигурации совпало
                        if (seatsCount == hall1.getSeatCount())  break;
                        // Обработка отрицательных случаев
                        else if (seatsCount > hall1.getSeatCount())
                            System.out.println("В этом зале нет столько кресел!");
                        else
                            System.out.println("Вы расставили не все кресла!");
                    }
                    // Сюда дойдем только если ошибок нет
                    hall1.seatConfig(seatsConfig);
                    System.out.println("Вы успешно задали конфигурацию!");
                }
                case 4 -> { // Создание сеанса фильма
                    if (cinemasCount == 0) { // Обработка случая, когда в сети еще нет кинотеатров
                        System.out.println("У вас нет кинотеатров!");
                        break;
                    }
                    cinema = cinemaChoose(scanner, cinemasCount); // Функция выбора нужного кинотеатра
                    hallsCount = mCinemaChain.getCinemas().get(cinema).getHalls().size();
                    if (hallsCount == 0) {
                        System.out.println("У вас нет кинозалов в этом кинотеатре!");
                        break;
                    }
                    hall = hallChoose(scanner, hallsCount); // Функция выбора нужного зала
                    while (true) { // Предотвращение ошибок через while(true) { try catch }
                        System.out.println("Введите название фильма:");
                        String film = scanner.nextLine();
                        // Вспомогательные переменные
                        int hourDuration, minuteDuration, hourStartOfSession, minuteStartOfSession;
                        try { // Работа со временем сеанса
                            System.out.println("Введите время начала фильма в формате (час мин):");
                            hourStartOfSession = scanner.nextInt();
                            minuteStartOfSession = scanner.nextInt();
                        } catch (Exception e) { // Обработка неверного ввода
                            scanner.nextLine();
                            System.out.print("Вы ввели неверно!\n");
                            continue;
                        }
                        int startOfSession = hourStartOfSession * 60 + minuteStartOfSession;
                        if (startOfSession < 0 || startOfSession >= 1440) {
                            System.out.println("Ваш фильм выходит за рамки суток!");
                            scanner.nextLine();
                            continue;
                        }
                        try {
                            System.out.println("Введите продолжительность фильма в формате (час мин):");
                            hourDuration = scanner.nextInt();
                            minuteDuration = scanner.nextInt();
                        } catch (Exception e) { // Обработка неверного ввода
                            scanner.nextLine();
                            System.out.print("Вы ввели неверно!\n");
                            continue;
                        }
                        int duration = hourDuration * 60 + minuteDuration;
                        if (duration < 0 || duration > 1440) {
                            System.out.println("Фильм не может длиться меньше минуты или больше суток!");
                            scanner.nextLine();
                            continue;
                        }
                        if (startOfSession + duration >= 1440) {
                            System.out.println("Вы выходите за рамки суток!");
                            scanner.nextLine();
                            continue;
                        }
                        boolean isCollision = false; // Переменная для проверки, нет ли пересекающихся сеансов
                        for (Session session :
                                mCinemaChain.getCinemas().get(cinema).getHalls().get(hall).getSessions()) {
                            if ((startOfSession > session.getStartOfSession() &&
                                    startOfSession < session.getStartOfSession() + session.getDuration()) ||
                                    (startOfSession < session.getStartOfSession() &&
                                            startOfSession + duration >= session.getStartOfSession())) {
                                isCollision = true;
                                System.out.println("Это время занято!");
                                break;
                            }
                        }
                        if (isCollision) // Обработка случая, когдавведенное время занято
                            break;
                        // Сюда дойдем тольько если нет ошибок
                        mCinemaChain.getCinemas().get(cinema).getHalls().get(hall).
                                sessionAdd(film, duration, startOfSession);
                        System.out.println("Вы успешно добавили сеанс!");
                        break;
                    }
                }
                case 0 -> { // Выход из аккаунта
                    isLoggedOut = true;
                    launch();
                }
                default -> System.out.println("Неверная операция!"); // Обработка неверного ввода
            }
            if(isLoggedOut) break; // Окончательный выход из интерфейса при разлогине
        }
    }
    private void userInterface() { // интерфейс пользователя с соответсвующими возможностями
        Scanner scanner = new Scanner(System.in);
        while (true) { // Предотвращение ошибок через while(true) { try catch }
            int action = -1;
            boolean isLoggedOut = false;
            try { // Выбор нужной операции
                System.out.println("Введите нужную вам операцию:");
                System.out.println("1 - Купить билет");
                System.out.println("2 - Ближайший сеанс фильма");
                System.out.println("3 - Печать зала");
                System.out.println();
                System.out.println("0 - Выйти из аккаунта");
                action = scanner.nextInt();
            } catch (Exception e) { // Обработка неверного ввода
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
            }
            int cinema, hall, session, hallsCount, seat, row; // Вспомогательные переменные
            int cinemasCount = mCinemaChain.getCinemas().size();
            Session session1;
            switch (action) {
                case 1 -> { // Покупка билета
                    cinema = cinemaChoose(scanner, cinemasCount); // Функция выбора нужного кинотеатра
                    hallsCount = mCinemaChain.getCinemas().get(cinema).getHalls().size();
                    hall = hallChoose(scanner, hallsCount); // Функция выбора нужного зала
                    session = sessionChoose(scanner, cinema, hall); // Функция выбора нужного сеанса
                    if(session == -1) break; // Неверный ввод сеанса
                    while (true) { // Предотвращение ошибок через while(true) { try catch }
                        session1 = mCinemaChain.getCinemas().get(cinema).getHalls().
                                get(hall).getSessions().get(session);
                        session1.sessionPrint(); // Вывод текущего сеанса для выбора места
                        System.out.println("Выберите место в формате ряд место:");
                        try {
                            row = scanner.nextInt() - 1;
                            seat = scanner.nextInt() - 1;
                            scanner.nextLine();
                        } catch (Exception e) { // Обработка неверного ввода
                            scanner.nextLine();
                            System.out.print("Вы ввели не число!\n");
                            continue;
                        }
                        // Обработка неверного ввода места
                        if (row < 0 || seat < 0 || row > session1.getSeats().length ||
                                seat > session1.getSeats().length)
                            System.out.println("Неверное место!");
                        if (session1.getSeats()[row][seat] == 1) {
                            session1.getSeats()[row][seat] = 2;
                            break;
                        } else if (session1.getSeats()[row][seat] == 2)
                            System.out.println("Это место занято!");
                        else
                            System.out.println("Данного места нет!");
                    }
                    // Дойдем сюда только если ошибок нет
                    System.out.println("Вы успешно купили билет!");
                }
                case 2 -> { // Печать всех фильмов в формате ближайших сеансов
                    // Вводим списки названия фильма, начала сеанса, продолжительности фильма,
                    // кинотеатров с этими сеансами и соответствующих залов
                    List<String> filmList = new ArrayList<>();
                    List<Integer> startsOfSessions = new ArrayList<>();
                    List<Integer> durations = new ArrayList<>();
                    List<String> cinemaList = new ArrayList<>();
                    List<Integer> hallsList = new ArrayList<>();
                    for (Cinema cinma : mCinemaChain.getCinemas())
                        for (Hall hll : cinma.getHalls())
                            for (Session ssion : hll.getSessions()) {
                                // Проходимся по всем сеансам и записываем ближайший сеанс для фильма
                                if (!filmList.contains(ssion.getFilm())) {
                                    filmList.add(ssion.getFilm());
                                    startsOfSessions.add(ssion.getStartOfSession());
                                    durations.add(ssion.getDuration());
                                    cinemaList.add(cinma.getName());
                                    hallsList.add(cinma.getHalls().indexOf(hll));
                                // В случае смены сеанса, название фильма и его продолжительность менять не нужно
                                } else if (ssion.getStartOfSession() < startsOfSessions.get(filmList.indexOf(ssion.getFilm()))) {
                                    startsOfSessions.set(filmList.indexOf(ssion.getFilm()), ssion.getStartOfSession());
                                    cinemaList.set(filmList.indexOf(ssion.getFilm()), cinma.getName());
                                    hallsList.set(filmList.indexOf(ssion.getFilm()), cinma.getHalls().indexOf(hll));
                                }
                            }
                    // Вывод всех ближайших сеансов
                    for (int i = 0; i < filmList.size(); i++) {
                        String text = String.format("кинотеатр %s, зал %d - %d:%02d-%d:%02d - %s",
                                cinemaList.get(i), (hallsList.get(i) + 1),
                                (startsOfSessions.get(i) / 60), (startsOfSessions.get(i) % 60),
                                ((startsOfSessions.get(i) + durations.get(i)) / 60),
                                ((startsOfSessions.get(i) + durations.get(i)) % 60),
                                filmList.get(i));
                        System.out.println(text);
                    }
                }
                case 3 -> { // Печать нужного сеанса
                    cinema = cinemaChoose(scanner, cinemasCount); // Функция выбора нужного кинотеатра
                    hallsCount = mCinemaChain.getCinemas().get(cinema).getHalls().size();
                    hall = hallChoose(scanner, hallsCount); // Функция выбора нужного зала
                    session = sessionChoose(scanner, cinema, hall); // Функция выбора нужного сеанса
                    if(session == -1) break; // Неверный ввод сеанса
                    session1 = mCinemaChain.getCinemas().get(cinema).getHalls().
                            get(hall).getSessions().get(session);
                    session1.sessionPrint();
                }
                case 0 -> { // Выход из аккаунта
                    isLoggedOut = true;
                    launch();
                }
                default -> System.out.println("Неверная операция!");
            }
            if(isLoggedOut) break; // Окончательный выход из интерфейса при разлогине
        }
    }

    public int cinemaChoose(Scanner scanner, int cinemasCount) { // Функция выбора нужного кинотеатра
        int cinema;
        while (true) { // Предотвращение ошибок через while(true) { try catch }
            System.out.println("Выберите кинотеатр:");
            for (int i = 0; i < cinemasCount; i++)
                System.out.println(i + 1 + " " + mCinemaChain.getCinemas().get(i).getName());
            try {
                cinema = scanner.nextInt() - 1;
            } catch (Exception e) { // Обработка неверного ввода
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
                continue;
            }
            if (cinema < 0 || cinema >= cinemasCount)
                System.out.println("Неверный кинотеатр!");
            else break;
        }
        return cinema;
    }
    public int hallChoose(Scanner scanner, int hallsCount) { // Функция выбора нужного зала
        int hall;
        while (true) { // Предотвращение ошибок через while(true) { try catch }
            System.out.println("Выберите зал:");
            for (int i = 0; i < hallsCount; i++)
                System.out.println(i + 1);
            try {
                hall = scanner.nextInt() - 1;
                scanner.nextLine();
            } catch (Exception e) { // Обработка неверного ввода
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
                continue;
            }
            if (hall < 0 || hall >= hallsCount)
                System.out.println("Неверный зал!");
            else break;
        }
        return hall;
    }

    public int sessionChoose(Scanner scanner, int cinema, int hall) { // Функция выбора нужного сеанса
        int hallsCount = mCinemaChain.getCinemas().get(cinema).getHalls().size();
        if (hallsCount == 0) { // Обработка случая, когда в кинотеатре нет залов
            System.out.println("К сожалению, кинозалов в этом кинотеатре нет");
            return -1;
        }
        int sessionCount = mCinemaChain.getCinemas().get(cinema).getHalls().
                get(hall).getSessions().size();
        if (sessionCount == 0) { // Обработка случая, когда в зале нет доступных сеансов
            System.out.println("К сожалению, в этом зале нет сеансов");
            return -1;
        }
        int session;
        Session session1;
        while (true) { // Предотвращение ошибок через while(true) { try catch }
            System.out.println("Выберите сеанс:");
            for (int i = 0; i < sessionCount; i++) {
                session1 = mCinemaChain.getCinemas().get(cinema).getHalls().get(hall).getSessions().get(i);
                String text = String.format("%d %d:%02d-%d:%02d - %s", (i + 1),
                        (session1.getStartOfSession() / 60), (session1.getStartOfSession() % 60),
                        ((session1.getStartOfSession() + session1.getDuration()) / 60),
                        ((session1.getStartOfSession() + session1.getDuration()) % 60),
                        session1.getFilm());
                System.out.println(text);
            }
            try {
                session = scanner.nextInt() - 1;
                scanner.nextLine();
            } catch (Exception e) { // Обработка неверного ввода
                scanner.nextLine();
                System.out.print("Вы ввели не число!\n");
                continue;
            }
            if (session < 0 || session >= sessionCount)
                System.out.println("Неверный сеанс!");
            else break;
        }
        return session;
    }
}