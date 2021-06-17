package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.entities.Item;
import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item createItem(Item item);
    List<Item> getAllItems();
    Item getItemById(UUID itemId);
    Item finishItem(UUID itemId);
    void deleteItem(UUID itemId);
    Item updateItem(Item item);
    List<Item> getItemsByUser(UUID userId);
}
