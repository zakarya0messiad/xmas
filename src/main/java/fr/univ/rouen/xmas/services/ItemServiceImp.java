package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.entities.Item;
import fr.univ.rouen.xmas.entities.Skill;
import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.exceptions.NotFoundException;
import fr.univ.rouen.xmas.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ItemServiceImp implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private ToyService toyService;

    @Override
    public Item createItem(Item item) {
       Toy toy = toyService.getToyById(item.getToy().getId());
       Skill toySkill = toy.getSkill();
       User user = userService.getUserById(item.getUser().getId());
        Skill userSkill= user.getSkill();
        int newProcessTime = 0;
        int toyProcessTime = toy.getProcessTime();
        if (toySkill.getId() == userSkill.getId()) {
            newProcessTime = toyProcessTime;
        } else {
            newProcessTime = (int) (toyProcessTime + (toyProcessTime*0.1));
        }
        item.setProcessTime(newProcessTime);
        return this.itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return  this.itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByUser(UUID userId) {
        User user = this.userService.getUserById(userId);
        return  this.itemRepository.findByUser(user);
    }

    @Override
    public Item getItemById(UUID itemId) {
        Optional<Item> itemDb = this.itemRepository.findById(itemId);

        if(itemDb.isPresent()) {
            return itemDb.get();
        }else {
            throw new NotFoundException("Record not found with id : " + itemId);
        }
    }

    @Override
    public Item finishItem(UUID itemId) {
        Optional<Item> itemDb = this.itemRepository.findById(itemId);

        if(itemDb.isPresent()) {
            Item itemUpdate = itemDb.get();
            itemUpdate.setFinished(true);
            itemRepository.save(itemUpdate);
            return itemUpdate;
        }else {
            throw new NotFoundException("Record not found with id : " + itemId);
        }
    }

    @Override
    public void deleteItem(UUID itemId) {
        Optional<Item> itemDb = this.itemRepository.findById(itemId);

        if(itemDb.isPresent()) {
            this.itemRepository.delete(itemDb.get());
        }else {
            throw new NotFoundException("Record not found with id : " + itemRepository);
        }
    }

    @Override
    public Item updateItem(Item item) {
        Optional<Item> itemDb = this.itemRepository.findById(item.getId());

        if(itemDb.isPresent()) {
            Item itemUpdate = itemDb.get();
            itemUpdate.setProcessTime(item.getProcessTime());
            itemUpdate.setFinished(item.getFinished());
            itemRepository.save(itemUpdate);
            return itemUpdate;
        }else {
            throw new NotFoundException("Record not found with id : " + item.getId());
        }
    }

}
