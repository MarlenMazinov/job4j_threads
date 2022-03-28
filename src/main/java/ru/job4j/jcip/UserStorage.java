package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> usersStore = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (user != null) {
            rsl = usersStore.putIfAbsent(user.getId(), user) == null;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (user != null) {
            rsl = usersStore.replace(user.getId(), user) != null;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (user != null) {
            rsl = usersStore.remove(user.getId(), user);
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User sender = usersStore.getOrDefault(fromId, null);
        User receiver = usersStore.getOrDefault(toId, null);
        if (sender != null && receiver != null && sender.getAmount() >= amount) {
            sender.setAmount(sender.getAmount() - amount);
            receiver.setAmount((receiver.getAmount() + amount));
            rsl = update(sender) && update(receiver);
        }
        return rsl;
    }
}
