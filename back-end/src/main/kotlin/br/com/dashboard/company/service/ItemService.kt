package br.com.dashboard.company.service

import br.com.dashboard.company.entities.item.Item
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ItemRepository
import br.com.dashboard.company.utils.common.PriceRequestVO
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.item.ItemResponseVO
import br.com.dashboard.company.vo.item.ItemRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Transactional(readOnly = true)
    fun findAllItems(): List<ItemResponseVO> {
        val items = itemRepository.findAll()
        return items.map { item -> parseObject(item, ItemResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findItemById(
        id: Long
    ): ItemResponseVO {
        val item = getItem(id = id)
        return parseObject(item, ItemResponseVO::class.java)
    }

    private fun getItem(id: Long): Item {
        return itemRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(ITEM_NOT_FOUND) }
    }

    @Transactional
    fun createNewItem(
        item: ItemRequestVO
    ): ItemResponseVO {
        if (!checkNameItemAlreadyExists(name = item.name)) {
            val itemResult: Item = parseObject(item, Item::class.java)
            return parseObject(itemRepository.save(itemResult), ItemResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_ITEM)
        }
    }

    private fun checkNameItemAlreadyExists(
        name: String
    ): Boolean {
        val itemResult = itemRepository.checkNameItemAlreadyExists(name = name)
        return itemResult != null
    }

    @Transactional
    fun updateItem(
        item: ItemResponseVO
    ): ItemResponseVO {
        if (!checkNameItemAlreadyExists(name = item.name)) {
            val itemSaved: Item = getItem(id = item.id)
            itemSaved.name = item.name
            itemSaved.price = item.price
            return parseObject(itemRepository.save(itemSaved), ItemResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_ITEM)
        }
    }

    @Transactional
    fun updatePriceItem(
        idItem: Long,
        price: PriceRequestVO
    ) {
        getItem(id = idItem)
        itemRepository.updatePriceItem(idItem = idItem, price = price.price)
    }

    fun deleteItem(id: Long) {
        itemRepository.delete(getItem(id = id))
    }

    companion object {
        const val ITEM_NOT_FOUND = "Item not found!"
        const val DUPLICATE_NAME_ITEM = "The Item already exists"
    }
}
