package org.example.home6.phoneBook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {
    private HashMap<String, List<Integer>> phoneBook;

    public PhoneBook(HashMap<String, List<Integer>> phoneBook) {
        this.phoneBook = phoneBook;
    }

    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    /**
     * @return
     * @apiNote Сортированная телефонная книга по мере убывания телефонов
     */
    public HashMap<String, List<Integer>> getPhoneBook() {
        TreeMap<String, Integer> sort = new TreeMap<>();
        for (String s : phoneBook.keySet()) {
            sort.put(s, phoneBook.get(s).size());
        }
        Map<String, Integer> sorted = sort.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        HashMap<String, List<Integer>> sortedPhoneBook = new LinkedHashMap<>();
        for (String s : sorted.keySet()) {
            sortedPhoneBook.put(s, phoneBook.get(s));
        }
        return sortedPhoneBook;
    }

    public void setPhoneBook(HashMap<String, List<Integer>> phoneBook) {
        this.phoneBook = phoneBook;
    }

    /**
     * @param name     Имя контакта
     * @param phoneNum телефон
     * @apiNote Занесение телефона в телефонную книгу
     */
    public void addPhone(String name, Integer phoneNum) {
        if (!phoneBook.containsKey(name)) {
            ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(phoneNum));
            phoneBook.put(name, arr);
        } else {
            List<Integer> arr = phoneBook.get(name);
            if (!arr.contains(phoneNum)) {
                arr.add(phoneNum);
                phoneBook.put(name, arr);
            }
        }
    }

    /**
     * @param name Имя
     * @return
     * @apiNote Поиск телефонов по имени
     */
    public List<Integer> find(String name) {
        if (phoneBook.containsKey(name)) {
            return phoneBook.get(name);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param path путь к файлу
     * @apiNote Загрузка данных в телефонную книгу из файла
     */
    public void loadFile(String path) throws IOException {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                List<String> arr = new ArrayList<>(Arrays.asList(line.replace('[', ' ').replace(']', ' ').split("=")));
                String name = arr.get(0);
                List<String> arr1 = new ArrayList<>(Arrays.asList(arr.get(1).trim().split(",")));
                for (String item : arr1) {
                    addPhone(name, Integer.parseInt(item.trim()));
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path Путь к файлу
     * @throws IOException
     * @apiNote Сохраняет телефонную книгу в файл
     */
    public void saveFile(String path) throws IOException {
        if (!phoneBook.isEmpty()) {
            try (FileWriter writer = new FileWriter(path, false)) {
                for (String s : phoneBook.keySet()) {
                    writer.write(s);
                    writer.append('=');
                    writer.append(phoneBook.get(s).toString());
                    writer.append('\n');
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * @param name     Имя
     * @param phoneNum номер телефона
     * @apiNote Удаление телефона из списка
     */
    public void delPhone(String name, Integer phoneNum) {
        if (phoneBook.containsKey(name)) {
            List<Integer> arr = phoneBook.get(name);
            if (arr.size() > 1) {
                arr.remove(phoneNum);
            } else {
                phoneBook.remove(name);
            }
        }
    }

    /**
     * @param name Имя
     * @apiNote Удаление контакта
     */
    public void delContact(String name) {
        phoneBook.remove(name);
    }

}