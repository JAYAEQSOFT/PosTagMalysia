package com.example.Model;



import java.util.ArrayList;
import java.util.List;

public class Sale_Static {

    private static Sale single_instance = null;
    static Double Total = 0.00;

    public  void Calculate(){ }

    public static Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public List<items> getItems() {
List<items> Items=single_instance.getItems();
        return Items;
    }

    public void setItems(List<items> items) {

        for(items dtls:Items)
        {
            Total+=dtls.getQuantity();
        }
        Items = items;

    }

    public List<items> Items;

    public static Sale getInstance() {
        if (single_instance==null) {
            single_instance = new Sale();
            single_instance.items = new ArrayList<>();
            Total=0.00;
        }

        return single_instance;
    }
    public static void setInstance() {

        single_instance = new Sale();
        single_instance.items = new ArrayList<>();
        Total=0.00;



    }
    public static void setInstance(Sale sale) {

        single_instance =sale;

    }
}
