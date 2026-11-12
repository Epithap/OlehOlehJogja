package com.example.oleholehjogja

object CartManager {
    private val cart = mutableMapOf<String, Int>()

    fun addItem(id: String) {
        cart[id] = (cart[id] ?: 0) + 1
    }

    fun removeItem(id: String) {
        val current = cart[id] ?: return
        if (current <= 1) cart.remove(id) else cart[id] = current - 1
    }

    fun setItemCount(id: String, count: Int) {
        if (count <= 0) {
            cart.remove(id)
        } else {
            cart[id] = count
        }
    }

    fun getItemCount(id: String): Int = cart[id] ?: 0

    fun getItems(): Map<String, Int> = cart.toMap()

    fun getTotalItemCount(): Int = cart.values.sum()

    fun clear() {
        cart.clear()
    }
}
