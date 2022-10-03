package com.aptech.cart;

import java.util.List;

public class Cart {
    private int total;
    private List<CartItem> items;

    public int getTotal() {
        int total = 0;
        for (CartItem cartItem : items) {
            total += cartItem.getTotal();
        }
        this.total = total;
        return total;
    }


    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public CartItem getCartItem(long productId) {
        return items.stream().filter(item -> item.getProductId() == productId).findAny().orElseThrow(null);
    }

    public void add(CartItem cartItem) {
        CartItem exist = this.getCartItem(cartItem.getProductId());
        if (exist != null) {
            int quantity = cartItem.getQuantity() + 1;
            for (CartItem item : this.items) {
                if (item.getProductId() == cartItem.getProductId()) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        } else {
            cartItem.setQuantity(1);
            this.items.add(cartItem);
        }
    }

    public void remove(long productId) {
        this.items.removeIf(item -> item.getProductId() == productId);
    }
}
