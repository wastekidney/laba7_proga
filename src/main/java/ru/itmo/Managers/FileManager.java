package ru.itmo.Managers;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.itmo.Collection.Product;
import ru.itmo.Console.Console;
import ru.itmo.utils.ZonedDateTimeAdapter;

public class FileManager {
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();
    File file = new File("collection.json");
    private final Console console;

    public FileManager(Console console) throws IOException {
        this.console = console;
    }

    public ArrayDeque<Product> gsonBufferedRead(){
        if (!file.exists()) {
            return new ArrayDeque<>();
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ArrayDeque<Product> product = gson.fromJson(new InputStreamReader(bis),
                    new TypeToken<ArrayDeque<Product>>(){}.getType()
            );
            return product != null ? product : new ArrayDeque<>();
        } catch (IOException e) {
            console.print("файл не найден или ошибка чтения: " + e.getMessage());
        }
        return new ArrayDeque<>();
    }

    public void gsonBufferedWrite(ArrayDeque<Product> product){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("collection.json"))) {
            gson.toJson(product, writer);
            console.print("json записан в collection.json");
        } catch (IOException e) {
            console.print("ошибка, json не записан в collection.json: " + e.getMessage());
        }
    }

}
