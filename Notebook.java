/* Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы. Реализовать в java.
Создать множество ноутбуков.
Написать метод, который будет запрашивать у пользователя критерий (или критерии) фильтрации и выведет ноутбуки, отвечающие фильтру. Критерии фильтрации можно хранить в Map.
Например:
“Введите цифру, соответствующую необходимому критерию:
1 - ОЗУ
2 - Объем ЖД
3 - Операционная система
4 - Цвет …
5 - Поиск
Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры фильтрации можно также в Map.
Отфильтровать ноутбуки из первоначального множества и вывести проходящие по условиям.
Работу сдать как обычно ссылкой на гит репозиторий
Частые ошибки:
1. Заставляете пользователя вводить все существующие критерии фильтрации
2. Невозможно использовать более одного критерия фильтрации одновременно
3. При выборке выводятся ноутбуки, которые удовлетворяют только одному фильтру, а не всем введенным пользователем
4. Работа выполнена только для каких то конкретных ноутбуков, и если поменять характеристики ноутбуков или добавить еще ноутбук, то программа начинает работать некорректно
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Notebook {
    private String model;
    private int ram;
    private int storage;
    private String os;
    private String color;

    public Notebook(String model, int ram, int storage, String os, String color) {
        this.model = model;
        this.ram = ram;
        this.storage = storage;
        this.os = os;
        this.color = color;
    }

    public String getModel() {
        return model;
    }
    public int getRam() {
        return ram;
    }
    public int getStorage() {
        return storage;
    }
    public String getOs() {
        return os;
    }
    public String getColor() {
        return color;
    }
}
class Main {
    public static void main(String[] args) {
        // Создание множества ноутбуков
        List<Notebook> notebooks = new ArrayList<>();
        notebooks.add(new Notebook("Dell Inspiron", 8, 256, "Windows 10", "Silver"));
        notebooks.add(new Notebook("HP Pavilion", 16, 512, "Windows 11", "Black"));
        notebooks.add(new Notebook("Figovo ThinkPad", 16, 512, "Windows 10", "Black"));
        notebooks.add(new Notebook("MacOgrizok Pro", 16, 512, "macOS", "Silver"));
        notebooks.add(new Notebook("Asus ROG", 32, 1000, "Windows 10", "Black"));

        filterNotebooks(notebooks);
    }
    public static void filterNotebooks(List<Notebook> notebooks) {
        Map<String, Object> filters = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        Map<Integer, String> criteria = new HashMap<>();
        criteria.put(1, "ОЗУ (ГБ)");
        criteria.put(2, "Объем ЖД (ГБ)");
        criteria.put(3, "Операционная система");
        criteria.put(4, "Цвет");
        criteria.put(5, "Поиск");

        System.out.println("Выберите критерии фильтрации:");
        for (Map.Entry<Integer, String> entry : criteria.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= 4) {
            System.out.println("Введите минимальное значение для критерия " + criteria.get(choice) + ":");
            Object filterValue = null;
            switch (choice) {
                case 1:
                    int minRam = scanner.nextInt();
                    filterValue = minRam;
                    break;
                case 2:
                    int minStorage = scanner.nextInt();
                    filterValue = minStorage;
                    break;
                case 3:
                    scanner.nextLine();
                    String os = scanner.nextLine();
                    filterValue = os;
                    break;
                case 4:
                    scanner.nextLine();
                    String color = scanner.nextLine();
                    filterValue = color;
                    break;
            }
            filters.put(criteria.get(choice), filterValue);
        } else if (choice == 5) {
            System.out.print("Поиск: ");
            scanner.nextLine();
            String search = scanner.nextLine();
            filters.put(criteria.get(choice), search);
        } else {
            System.out.println("Некорректный ввод. Пожалуйста перезапустите программу и введите значение с 1 до 5!");
            return;
        }

        List<Notebook> filteredNotebooks = filterNotebooksByCriteria(notebooks, filters);
        printFilteredNotebooks(filteredNotebooks);
    }
    public static List<Notebook> filterNotebooksByCriteria(List<Notebook> notebooks, Map<String, Object> filters) {
        List<Notebook> filteredNotebooks = new ArrayList<>();
        for (Notebook notebook : notebooks) {
            boolean matchesAllCriteria = true;
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String criteria = entry.getKey();
                Object value = entry.getValue();
                switch (criteria) {
                    case "ОЗУ (ГБ)":
                        if (notebook.getRam() < (int) value) {
                            matchesAllCriteria = false;
                        }
                        break;
                    case "Объем ЖД (ГБ)":
                        if (notebook.getStorage() < (int) value) {
                            matchesAllCriteria = false;
                        }
                        break;
                    case "Операционная система":
                        if (!notebook.getOs().toLowerCase().contains(((String) value).toLowerCase())) {
                            matchesAllCriteria = false;
                        }
                        break;
                    case "Цвет":
                        if (!notebook.getColor().toLowerCase().equals(((String) value).toLowerCase())) {
                            matchesAllCriteria = false;
                        }
                        break;
                    case "Поиск":
                        String search = (String) value;
                        if (!notebook.getModel().toLowerCase().contains(search.toLowerCase()) &&
                                !notebook.getOs().toLowerCase().contains(search.toLowerCase())) {
                            matchesAllCriteria = false;
                        }
                        break;
                }
                if (!matchesAllCriteria) {
                    break;
                }
            }
            if (matchesAllCriteria) {
                filteredNotebooks.add(notebook);
            }
        }
        return filteredNotebooks;
    }
    public static void printFilteredNotebooks(List<Notebook> filteredNotebooks) {
        if (filteredNotebooks.isEmpty()) {
            System.out.println("Ни один ноутбук не соответствует заданным критериям фильтрации.");
        } else {
            System.out.println("Результаты фильтрации:");
            for (Notebook notebook : filteredNotebooks) {
                System.out.println("Модель: " + notebook.getModel());
                System.out.println("ОЗУ: " + notebook.getRam() + " ГБ");
                System.out.println("Объем ЖД: " + notebook.getStorage() + " ГБ");
                System.out.println("Операционная система: " + notebook.getOs());
                System.out.println("Цвет: " + notebook.getColor());
                System.out.println("----------------------------------");
            }
        }
    }
}
