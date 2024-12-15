package br.com.dashboard.company.service

import br.com.dashboard.company.entities.item.Item
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ItemRepository
import br.com.dashboard.company.utils.common.PriceRequestVO
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.item.ItemRequestVO
import br.com.dashboard.company.vo.item.ItemResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllItems(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<ItemResponseVO> {
        val items: Page<Item>? = itemRepository.findAllItems(userId = user.id, name = name, pageable = pageable)
        return items?.map { reservation -> parseObject(reservation, ItemResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = ITEM_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findItemByName(
        user: User,
        name: String
    ): List<ItemResponseVO> {
        val items: List<Item> = itemRepository.findItemByName(userId = user.id, name = name)
        return items.map { item -> parseObject(item, ItemResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findItemById(
        user: User,
        idItem: Long
    ): ItemResponseVO {
        val itemSaved: Item? = itemRepository.findItemById(userId = user.id, itemId = idItem)
        if (itemSaved != null) {
            return parseObject(itemSaved, ItemResponseVO::class.java)
        } else {
            throw ResourceNotFoundException(message = ITEM_NOT_FOUND)
        }
    }

   fun getItem(
        userId: Long,
        itemId: Long
    ): Item {
        val itemSaved: Item? = itemRepository.findItemById(userId = userId, itemId = itemId)
        if (itemSaved != null) {
            return itemSaved
        } else {
            throw ResourceNotFoundException(ITEM_NOT_FOUND)
        }
    }

    @Transactional
    fun createNewItem(
        user: User,
        item: ItemRequestVO
    ): ItemResponseVO {
        if (!checkNameItemAlreadyExists(userId = user.id, name = item.name)) {
            val userAuthenticated = userService.findUserById(id = user.id)
            val itemResult: Item = parseObject(item, Item::class.java)
            itemResult.user = userAuthenticated
            return parseObject(itemRepository.save(itemResult), ItemResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_ITEM)
        }
    }

    private fun checkNameItemAlreadyExists(
        userId: Long,
        name: String
    ): Boolean {
        val itemResult = itemRepository.checkNameItemAlreadyExists(userId = userId, name = name)
        return itemResult != null
    }

    @Transactional
    fun updateItem(
        user: User,
        item: ItemResponseVO
    ): ItemResponseVO {
        if (!checkNameItemAlreadyExists(userId = user.id, name = item.name)) {
            val itemSaved: Item = getItem(userId = user.id, itemId = item.id)
            itemSaved.name = item.name
            itemSaved.price = item.price
            return parseObject(itemRepository.save(itemSaved), ItemResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_ITEM)
        }
    }

    @Transactional
    fun updatePriceItem(
        user: User,
        itemId: Long,
        price: PriceRequestVO
    ) {
        getItem(userId = user.id, itemId = itemId)
        itemRepository.updateItemPrice(userId = user.id, idItem = itemId, price = price.price)
    }

    @Transactional
    fun deleteItem(
        userId: Long,
        itemId: Long
    ) {
        val itemSaved: Item = getItem(userId = userId, itemId = itemId)
        itemRepository.deleteItemById(itemId = itemSaved.id, userId = userId)
    }

    companion object {
        const val ITEM_NOT_FOUND = "Item not found!"
        const val DUPLICATE_NAME_ITEM = "The Item already exists"
    }
}
