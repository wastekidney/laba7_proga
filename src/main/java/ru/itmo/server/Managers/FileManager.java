package ru.itmo.server.Managers;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.itmo.common.Collection.Product;
import ru.itmo.server.MainServer;
import ru.itmo.server.ZoneDateTimeAdapter.ZonedDateTimeAdapter;

public class FileManager {
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();
    File file = new File("collection.json");

    public FileManager() throws IOException {
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
            MainServer.logger.error("файл не найден или ошибка чтения: " + e.getMessage());
        }
        return new ArrayDeque<>();
    }

    public void gsonBufferedWrite(ArrayDeque<Product> product){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("collection.json"))) {
            gson.toJson(product, writer);
            MainServer.logger.info("json записан в collection.json");
        } catch (IOException e) {
            MainServer.logger.error("ошибка, json не записан в collection.json: " + e.getMessage());
        }
    }

}
