package dev.highright96.jpashop.service;

import dev.highright96.jpashop.domain.item.Item;
import dev.highright96.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    
    @Transactional //가까운 transactional 이 우선권을 갖는다.
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        /*
        * 1. setter 은 사용하지 않는 것이 좋다.
        * 2. 이와 같은 변경은 setter 대신 Item 클래스 안에 메서드를 만들어주는 편이 유지보수에 좋다.
        */
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findItem(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
