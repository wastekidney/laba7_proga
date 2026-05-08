package ru.itmo.server.Managers;

import ru.itmo.common.Collection.Organization;
import ru.itmo.common.Collection.Product;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import ru.itmo.server.MainServer;

public class CollectionManager {
    private ArrayDeque<Product> stack = new ArrayDeque<>();
    private LocalDateTime initializationTime;
    private final FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void loadCollection(){
        initializationTime = LocalDateTime.now();
        if (!fileManager.gsonBufferedRead().isEmpty()){
            fileManager.gsonBufferedRead().stream().forEach(stack::push);
            MainServer.logger.info("прочитано из файла, загружено в коллекцию");
//            sortCollection();
        }
    }
//    public void sortCollection(){
//        List<Product> sortedList = new ArrayList<>(stack);
//        sortedList.sort(Comparator.comparing(Product::getAddress));
//        stack.clear();
//        sortedList.stream().forEach(stack::push);
//        MainServer.logger.info("коллекция отсортирована по местоположению");
//    }
    public StringBuilder addStack(Product product){
        StringBuilder sb = new StringBuilder();
        stack.push(product);
        sb.append(stack.getFirst());
        MainServer.logger.info("элемент добавлен");
        return sb;

    }
    public void saveCollection(){
        fileManager.gsonBufferedWrite(stack);
    }
    public void popStack(){
        stack.pop();
    }
    public StringBuilder showCollection(){
        StringBuilder sb = stack.stream()
                .collect(StringBuilder::new, (b, p) -> b.append(p).append("\n"), StringBuilder::append);
        return sb;
    }
    public void clearStack(){
        stack.clear();
        MainServer.logger.info("коллекция пуста");
    }
    public LocalDateTime getInitialisationTime(){
        return initializationTime;
    }

    public String getTypeOfCollection(){
        return stack.getClass().getName();
    }
    public Integer getSizeOfCollection(){
        return stack.size();
    }

    public StringBuilder filterContainsName(String filterName){
        StringBuilder sb = stack.stream().
                filter(product -> product.getName().contains(filterName))
                .collect(StringBuilder::new, (b, p) -> b.append(p).append("\n"), StringBuilder::append);
        return sb;
        }

    public void removeById(String id) {
        try {
            long parsedId = Long.parseLong(id);
            stack.removeIf(product -> product.getId() == parsedId);
        }  catch (NumberFormatException e) {
            MainServer.logger.error("ошибка: '{}' не является числом", id);
        }
    }

    public List<Product> getCopyStackSortedByPrice() {
        List<Product> sortedList = new ArrayList<>(stack);
        sortedList.sort(Comparator.comparingDouble(Product::getPrice));
        return sortedList;
    }

    public ArrayDeque<Product> getStack() {
        return stack;
    }

    public void removeGreater(String element) {
        try {
            float floatElement = Float.parseFloat(element);
            stack.removeIf(product -> product.getPrice() > floatElement);
        } catch (NumberFormatException e) {
            MainServer.logger.error("ошибка: '{}' не является числом", element);
        }
    }

    public void removeLower(String element) {
        try {
            float floatElement = Float.parseFloat(element);
            stack.removeIf(product -> product.getPrice() < floatElement);
        } catch (NumberFormatException e) {
            MainServer.logger.error("ошибка: '" + element + "' не является числом");
        }
    }

    public Product getById(Long currentId) {
        return stack.stream().filter(product -> product.getId() == currentId).findFirst().orElse(null);
    }
}

