package ru.itmo.server.Managers;

import ru.itmo.common.Collection.Product;
import ru.itmo.server.MainServer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CollectionManager {
    private final ArrayDeque<Product> stack = new ArrayDeque<>();
    private LocalDateTime initializationTime;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CollectionManager() {
        loadCollection();
    }

    private void loadCollection() {
        lock.writeLock().lock();
        try {
            initializationTime = LocalDateTime.now();
            List<Product> products = ProductDbManager.loadAll();
            stack.clear();
            stack.addAll(products);
            MainServer.logger.info("загружено из БД: {} элементов", products.size());
        } catch (SQLException e) {
            MainServer.logger.error("ошибка загрузки из БД", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public StringBuilder addStack(Product product) {
        StringBuilder sb = new StringBuilder();
        lock.writeLock().lock();
        try {
            stack.push(product);
            sb.append(stack.getFirst());
            MainServer.logger.info("элемент добавлен");
            return sb;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void popStack() {
        lock.writeLock().lock();
        try {
            stack.pop();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public StringBuilder showCollection() {
        lock.readLock().lock();
        try {
            return stack.stream()
                    .collect(StringBuilder::new, (b, p) -> b.append(p).append("\n"), StringBuilder::append);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clearStack() {
        lock.writeLock().lock();
        try {
            stack.clear();
            MainServer.logger.info("коллекция пуста");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public LocalDateTime getInitialisationTime() {
        lock.readLock().lock();
        try {
            return initializationTime;
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getTypeOfCollection() {
        lock.readLock().lock();
        try {
            return stack.getClass().getName();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Integer getSizeOfCollection() {
        lock.readLock().lock();
        try {
            return stack.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public StringBuilder filterContainsName(String filterName) {
        lock.readLock().lock();
        try {
            return stack.stream()
                    .filter(product -> product.getName().contains(filterName))
                    .collect(StringBuilder::new, (b, p) -> b.append(p).append("\n"), StringBuilder::append);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void removeById(String id) {
        lock.writeLock().lock();
        try {
            long parsedId = Long.parseLong(id);
            stack.removeIf(product -> product.getId() == parsedId);
        } catch (NumberFormatException e) {
            MainServer.logger.error("ошибка: '{}' не является числом", id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Product> getCopyStackSortedByPrice() {
        lock.readLock().lock();
        try {
            List<Product> sortedList = new ArrayList<>(stack);
            sortedList.sort(Comparator.comparingDouble(Product::getPrice));
            return sortedList;
        } finally {
            lock.readLock().unlock();
        }
    }

    public ArrayDeque<Product> getStack() {
        lock.readLock().lock();
        try {
            return new ArrayDeque<>(stack);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void removeGreaterByUser(float price, int userId) {
        lock.writeLock().lock();
        try {
            stack.removeIf(product -> product.getPrice() > price && product.getUserId() == userId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeLowerByUser(float price, int userId) {
        lock.writeLock().lock();
        try {
            stack.removeIf(product -> product.getPrice() < price && product.getUserId() == userId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Product getById(Long currentId) {
        lock.readLock().lock();
        try {
            return stack.stream()
                    .filter(product -> product.getId() == currentId)
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void updateProduct(Product updatedProduct) {
        lock.writeLock().lock();
        try {
            stack.removeIf(p -> p.getId() == updatedProduct.getId());
            stack.push(updatedProduct);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void reloadFromDatabase() {
        lock.writeLock().lock();
        try {
            List<Product> products = ProductDbManager.loadAll();
            stack.clear();
            stack.addAll(products);
            MainServer.logger.info("Коллекция перезагружена из БД, элементов: {}", products.size());
        } catch (SQLException e) {
            MainServer.logger.error("Ошибка перезагрузки коллекции", e);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public ReadWriteLock getLock() {
        return lock;
    }

    public void clearByUser(int userId) {
        lock.writeLock().lock();
        try {
            stack.removeIf(p -> p.getUserId() == userId);
            MainServer.logger.info("Очищены объекты пользователя {}", userId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeProductById(long id, int userId) {
        lock.writeLock().lock();
        try {
            boolean removed = stack.removeIf(p -> p.getId() == id && p.getUserId() == userId);
            if (removed) {
                MainServer.logger.info("Удалён продукт id={} пользователя {}", id, userId);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}