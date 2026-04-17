package ru.itmo.Managers;

import ru.itmo.Collection.Product;

import java.time.LocalDateTime;
import java.util.*;

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
            for (Product product : fileManager.gsonBufferedRead()){
                stack.push(product);
            }
            System.out.println("прочитано из файла, загружено в коллекцию");
        }
    }
    public void saveCollection(){
        fileManager.gsonBufferedWrite(stack);
    }

    public void addStack(Product product){
        stack.push(product);
        System.out.println(stack);
    }
    public void popStack(){
        stack.pop();
    }
    public void showCollection(){
        for (Product product : stack) {
            System.out.println(product.toString());
//            fileManager.gsonBufferedWriteExample(product);
        }
    }
    public void clearStack(){
        stack.clear();
    }
//    public LocalDateTime infoCollection(){
//        return getInitialisationTime();
//    }
    public LocalDateTime getInitialisationTime(){
        return initializationTime;
    }

    public String getTypeOfCollection(){
        return stack.getClass().getName();
    }
    public Integer getSizeOfCollection(){
        return stack.size();
    }

    public void filterContainsName(String filterName){
        for (Product product : stack) {
            if (product.getName().contains(filterName) ){
                System.out.println(product.toString());
            }
            else  {
                System.out.println(filterName + " not found");
            }
            }
        }

    public void removeById(String id) {
        long parsedId = Long.parseLong(id);
        stack.removeIf(product -> product.getId() == parsedId);
    }
    public void getId(){
    }

    public List<Product> getCopyStackSortedByPrice() {
        List<Product> sortedList = new ArrayList<>(stack);
        sortedList.sort(Comparator.comparingDouble(Product::getPrice));
        return sortedList;
    }

    public List<Product> getCopyStackSortedByName() {
        List<Product> sortedList = new ArrayList<>(stack);
        sortedList.sort(Comparator.comparing(Product::getName));
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
            System.out.println("ошибка: '" + element + "' не является числом");
        }
    }

    public void removeLower(String element) {
        try {
            float floatElement = Float.parseFloat(element);
            stack.removeIf(product -> product.getPrice() < floatElement);
        } catch (NumberFormatException e) {
            System.out.println("ошибка: '" + element + "' не является числом");
        }
    }

    public Product getById(Long currentId) {
        for (Product product : stack) {
            if (product.getId() == currentId) return product;
        }
        return null;
    }
}

